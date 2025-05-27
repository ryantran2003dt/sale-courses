package com.salecoursecms.service.impl;

import com.salecoursecms.config.security.dto.UserDetailsImpl;
import com.salecoursecms.config.security.service.JwtService;
import com.salecoursecms.constant.AppConst;
import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.constant.VariableConst;
import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.reponse.LoginReponse;
import com.salecoursecms.dto.reponse.RoleReponse;
import com.salecoursecms.dto.reponse.UserInfoResponse;
import com.salecoursecms.dto.request.LoginRequest;
import com.salecoursecms.entity.first.RoleEntity;
import com.salecoursecms.entity.first.RoleModuleEntity;
import com.salecoursecms.entity.first.UserEntity;
import com.salecoursecms.exception.ResourceNotFoundException;
import com.salecoursecms.exception.UnauthorizedException;
import com.salecoursecms.mapper.AuthMapper;
import com.salecoursecms.mapper.RoleMapper;
import com.salecoursecms.repository.first.RoleModuleReponsitory;
import com.salecoursecms.repository.first.RoleRepository;
import com.salecoursecms.repository.first.UserRepository;
import com.salecoursecms.service.AuthService;
import com.salecoursecms.service.TokenBlocklistService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final RoleRepository roleRepository;
    private final RoleModuleReponsitory roleModuleReponsitory;
    private final JwtService jwtService;
    private final RoleMapper roleMapper;
    private final MessageSource messageSource;
    private final TokenBlocklistService tokenBlocklistService;

    @Override
    public BaseReponse<?> login(LoginRequest loginRequestDTO, HttpServletResponse httpServletResponse, Locale locale) {
        try{
            String username = loginRequestDTO.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginRequestDTO.getPassword())
            );
            UserEntity user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElse(null);
            Optional<RoleEntity> roles = roleRepository.findRoleById(user.getRoldId());
            if (roles == null || !roles.isPresent()) {
                throw new ResourceNotFoundException(MessageConst.ROLE_NOT_FOUND);
            }
            String role = roles.get().getRoleName();
            UserDetailsImpl userDetails = UserDetailsImpl.builder()
                    .username(username)
                    .authorities(List.of(new SimpleGrantedAuthority(role)))
                    .build();
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            setTokenToCookie(httpServletResponse, accessToken, refreshToken);
            LoginReponse loginResponse = generateLoginResponse(username, role);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.LOGIN_SUCCESS,null,new Locale(VariableConst.LAN)),loginResponse);
        }
        catch (InternalAuthenticationServiceException exception) {
            throw new UnauthorizedException(MessageConst.ACCOUNT_NOT_FOUND);
        } catch (BadCredentialsException exception) {
            throw new UnauthorizedException(MessageConst.USERNAME_PASSWORD_WRONG);
        } catch (DisabledException exception) {
            throw new UnauthorizedException(MessageConst.ACCOUNT_IS_DISABLE);
        } catch (LockedException exception) {
            throw new UnauthorizedException(MessageConst.ACCOUNT_IS_LOCKED);
        }
    }
    private void setTokenToCookie(HttpServletResponse httpServletResponse,
                                  String accessToken, String refreshToken) {
        Cookie refreshTokenCookie = authMapper.createCookie(AppConst.REFRESH_TOKEN, refreshToken);
        Cookie accessTokenCookie = authMapper.createCookie(AppConst.ACCESS_TOKEN, accessToken);
        httpServletResponse.addCookie(refreshTokenCookie);
        httpServletResponse.addCookie(accessTokenCookie);
    }
    private LoginReponse generateLoginResponse(String username, String role) {
        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND));

        RoleEntity roleEntity = roleRepository
                .findRoleById(user.getRoldId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ROLE_NOT_FOUND));
        List<Long> moduleId = roleModuleReponsitory.findByRoleId(roleEntity.getId()).stream().map(RoleModuleEntity::getModuleId).collect(Collectors.toList());
        RoleReponse reponseDTO = roleMapper.toRoleReponse(roleEntity, moduleId);

        return new LoginReponse(
                user.getId(),
                user.getUsername(),
                jwtService.generateAccessToken(UserDetailsImpl.builder()
                        .username(username)
                        .authorities(List.of(new SimpleGrantedAuthority(role)))
                        .build()),
                reponseDTO
        );
    }

    @Override
    public BaseReponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Lấy refresh token từ cookie
            String refreshToken = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (AppConst.REFRESH_TOKEN.equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }

            if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
                throw new UnauthorizedException("Refresh token không hợp lệ hoặc đã hết hạn.");
            }

            // Lấy username từ refresh token
            String username = jwtService.extractUsername(refreshToken);

            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND));

            RoleEntity roleEntity = roleRepository.findRoleById(user.getRoldId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ROLE_NOT_FOUND));

            String role = roleEntity.getRoleName();

            // Tạo access token mới
            UserDetailsImpl userDetails = UserDetailsImpl.builder()
                    .username(username)
                    .authorities(List.of(new SimpleGrantedAuthority(role)))
                    .build();
            String newAccessToken = jwtService.generateAccessToken(userDetails);

            // Cập nhật cookie mới
            Cookie newAccessTokenCookie = authMapper.createCookie(AppConst.ACCESS_TOKEN, newAccessToken);
            response.addCookie(newAccessTokenCookie);

            return new BaseReponse<>(AppConst.STATUS_SUCCESS, false,
                    messageSource.getMessage(MessageConst.JWT_REFRESH_SUCCESS_, null, new Locale(VariableConst.LAN)),
                    newAccessToken);
        } catch (Exception e) {
            log.error("[ERROR] " + e.getMessage());
            throw new UnauthorizedException(MessageConst.JWT_REFRESH_FAIL);
        }
    }


    @Override
    public BaseReponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.substring(7);

        if (accessToken != null && jwtService.validateToken(accessToken)) {
            long expiration = jwtService.getExpirationMillis(accessToken);
            tokenBlocklistService.blockToken(accessToken, expiration);
        }

        // Xóa cookie
        deleteCookie(response, AppConst.ACCESS_TOKEN);
        deleteCookie(response, AppConst.REFRESH_TOKEN);

        return new BaseReponse<>(AppConst.STATUS_SUCCESS, false, messageSource.getMessage(MessageConst.LOGOUT_SUCCESS,null,new Locale(VariableConst.LAN)), null);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public BaseReponse<?> getInformation(HttpServletRequest httpServletRequest) {
        try{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String accessToken = authorizationHeader.substring(7);
            String username = jwtService.extractUsername(accessToken);

            UserEntity user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND));

            RoleEntity roleEntity = roleRepository
                    .findRoleById(user.getRoldId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ROLE_NOT_FOUND));
            UserInfoResponse userInfoResponse = new UserInfoResponse(user.getUsername(),user.getFullName(),user.getPhone(),roleEntity.getRoleName());
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS,null,new Locale(VariableConst.LAN)),userInfoResponse);
        }catch (Exception e) {
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.GET_DATA_FAIL,null,new Locale(VariableConst.LAN)),null);
        }
    }
}
