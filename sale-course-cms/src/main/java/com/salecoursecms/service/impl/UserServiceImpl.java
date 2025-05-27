package com.salecoursecms.service.impl;

import com.salecoursecms.constant.AppConst;
import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.constant.VariableConst;
import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.reponse.PagingResponse;
import com.salecoursecms.dto.reponse.UserReponse;
import com.salecoursecms.dto.request.CreateUserRequest;
import com.salecoursecms.dto.request.PagingRequest;
import com.salecoursecms.dto.request.UpdateUserRequest;
import com.salecoursecms.entity.first.UserEntity;
import com.salecoursecms.exception.ResourceNotFoundException;
import com.salecoursecms.mapper.PagingMapper;
import com.salecoursecms.mapper.UserMapper;
import com.salecoursecms.repository.first.UserRepository;
import com.salecoursecms.service.UserService;
import com.salecoursecms.utils.PageableInit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;
    private final PagingMapper pagingMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public BaseReponse<?> findAllUSer(String search, PagingRequest pagingRequest){
        try{
            Pageable pageable = PageableInit.getPageable(pagingRequest);

            Page<UserEntity> user = userRepository.findAllWithPagin(search,pageable);

            PagingResponse pagingResponse = pagingMapper.createPagingResponse(user.getTotalPages(), user.getNumber(), user.getSize());

            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS, null,new Locale(VariableConst.LAN)),pagingResponse,user.getContent());
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.GET_DATA_FAIL,null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> createUser(CreateUserRequest req) {
        try{
            String password = passwordEncoder.encode(req.getPassword());
            req.setPassword(password);
            UserEntity userEntity = userMapper.toUserEntity(req);
            userRepository.save(userEntity);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.CREATE_SUCCESS, null,new Locale(VariableConst.LAN)),userEntity);
        }
        catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(e.getMessage(),null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> updateStatus(Long id, int status){
        try{
            UserEntity userEntity = userRepository.findUserByStatus(id,status);
            UserReponse userReponse = userMapper.toUserReponse(userEntity);
            if(userEntity == null){
                throw new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND);
            }else {
                userEntity.setStatus(VariableConst.DELETE);
                userRepository.save(userEntity);
                return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.UPDATE_SUCCESS, null,new Locale(VariableConst.LAN)),userReponse);
            }
        }catch (Exception e){
            log.info("[ERROR] " + e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.UPDATE_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> updateUser(UpdateUserRequest req){
        try{
            boolean existUser = userRepository.existsById(req.getId());
            if(existUser){
                UserEntity userEntity = userMapper.toUpdateUser(req);
                userRepository.save(userEntity);
                UserReponse userReponse = userMapper.toUserReponse(userEntity);
                return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.UPDATE_SUCCESS, null,new Locale(VariableConst.LAN)),userReponse);
            }else {
                throw new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND);
            }
        }catch (Exception e){
            log.info("[ERROR] " + e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.UPDATE_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }
    @Override
    public BaseReponse<?> findUserById(Long id) {
        try{
            UserEntity user = userRepository.findUserByStatus(id,1);
            UserReponse reponse = userMapper.toUserReponse(user);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS, null,new Locale(VariableConst.LAN)),reponse);
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.ACCOUNT_NOT_FOUND, null,new Locale(VariableConst.LAN)),null);
        }
    }
}
