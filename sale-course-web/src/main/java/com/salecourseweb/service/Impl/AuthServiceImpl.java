package com.salecourseweb.service.Impl;

import com.salecourseweb.constant.AppConst;
import com.salecourseweb.constant.MessageConst;
import com.salecourseweb.constant.VariableConst;
import com.salecourseweb.dto.reponse.BaseReponse;
import com.salecourseweb.dto.reponse.LoginReponse;
import com.salecourseweb.dto.reponse.RoleReponse;
import com.salecourseweb.dto.request.IdTokenRequest;
import com.salecourseweb.entity.first.StudentEntity;
import com.salecourseweb.entity.second.RoleEntity;
import com.salecourseweb.entity.second.RoleModuleEntity;
import com.salecourseweb.exception.ResourceNotFoundException;
import com.salecourseweb.mapper.RoleMapper;
import com.salecourseweb.repository.first.StudentRepository;
import com.salecourseweb.repository.second.RoleModuleRepository;
import com.salecourseweb.repository.second.RoleRepository;
import com.salecourseweb.service.AuthService;
import com.salecourseweb.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JWTUtils jwtUtils;
    private final StudentRepository studentRepository;
    private final GoogleIdTokenVerifier verifier;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final RoleModuleRepository roleModuleRepository;
    private final MessageSource messageSource;


    @Override
    public BaseReponse<?> loginOAuthGoogle(IdTokenRequest requestBody) {
       try{
           StudentEntity account = verifyIDToken(requestBody.getIdToken());
           if (account == null) {
               throw new IllegalArgumentException();
           }
           account = createOrUpdateUser(account);
           String token = jwtUtils.createToken(account, false);
           LoginReponse reponse =generateLoginResponse(account.getEmail());
           reponse.setToken(token);
           return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.LOGIN_SUCCESS,null,new Locale(VariableConst.LAN)),reponse);
       }catch (Exception e){
           log.error("[ERROR] " + e.getMessage());
           return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(e.getMessage(),null,new Locale(VariableConst.LAN)),null);
       }

    }
    private LoginReponse generateLoginResponse(String email) {
        StudentEntity user = studentRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND));

        RoleEntity roleEntity = roleRepository
                .findRoleById(user.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ROLE_NOT_FOUND));
        List<Long> moduleId = roleModuleRepository.findByRoleId(roleEntity.getId()).stream().map(RoleModuleEntity::getModuleId).collect(Collectors.toList());
        RoleReponse reponseDTO = roleMapper.toRoleReponse(roleEntity, moduleId);

        return new LoginReponse(
                user.getId(),
                user.getEmail(),
                null,
                reponseDTO
        );
    }
    private StudentEntity verifyIDToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String email = payload.getEmail();
            String pictureUrl = (String) payload.get("picture");

            return new StudentEntity(firstName, lastName, email, pictureUrl);
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }
    @Transactional
    public StudentEntity createOrUpdateUser(StudentEntity account) {
        StudentEntity existingAccount = studentRepository.findByEmail(account.getEmail()).orElse(null);
        if (existingAccount == null) {
            account.setCreateDate(new Date());
            account.setStatus(1);
            account.setRoleId(3L);
            studentRepository.save(account);
            return account;
        }
        existingAccount.setFullName(account.getFullName());
        existingAccount.setAvatarUrl(account.getAvatarUrl());
        studentRepository.save(existingAccount);
        return existingAccount;
    }
}
