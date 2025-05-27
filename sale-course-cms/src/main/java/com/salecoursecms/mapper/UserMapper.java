package com.salecoursecms.mapper;

import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.dto.reponse.UserReponse;
import com.salecoursecms.dto.request.CreateUserRequest;
import com.salecoursecms.dto.request.UpdateUserRequest;
import com.salecoursecms.entity.first.UserEntity;
import com.salecoursecms.exception.SystemErrorException;
import com.salecoursecms.repository.first.RoleRepository;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleRepository roleRepository;
    public UserEntity toUserEntity(CreateUserRequest req) {
        UserEntity user = new UserEntity();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setFullName(req.getFullName());
        user.setGender(req.getGender());
        user.setPhone(req.getPhone());
        user.setDateOfBirth(req.getDateOfBirth());
        user.setEmail(req.getEmail());
        user.setImg(req.getImg());
        user.setCreateDate(new Date());
        user.setUpdateDate(req.getUpdateDate());
        user.setStatus(req.getStatus());
        boolean existsRole = roleRepository.existsById(req.getRoldId());
        if(existsRole) {
            user.setRoldId(req.getRoldId());
        }else {
            throw new SystemErrorException(MessageConst.ROLE_NOT_FOUND);
        }
        return user;
    }
    public UserReponse toUserReponse(UserEntity user) {
        UserReponse userReponse = new UserReponse();
        userReponse.setId(user.getId());
        userReponse.setUsername(user.getUsername());
        userReponse.setFullName(user.getFullName());
        userReponse.setGender(user.getGender());
        userReponse.setPhone(user.getPhone());
        userReponse.setDateOfBirth(user.getDateOfBirth());
        userReponse.setEmail(user.getEmail());
        userReponse.setImg(user.getImg());
        userReponse.setCreateDate(user.getCreateDate());
        userReponse.setUpdateDate(user.getUpdateDate());
        userReponse.setStatus(user.getStatus());
        userReponse.setRoldId(user.getRoldId());
        return userReponse;
    }
    public UserEntity toUpdateUser(UpdateUserRequest req){
        UserEntity user = new UserEntity();
        user.setId(req.getId());
        user.setPassword(req.getPassword());
        user.setFullName(req.getFullName());
        user.setGender(req.getGender());
        user.setPhone(req.getPhone());
        user.setDateOfBirth(req.getDateOfBirth());
        user.setEmail(req.getEmail());
        user.setImg(req.getImg());
        user.setUpdateDate(new Date());
        user.setImg(req.getImg());
        boolean existsRole = roleRepository.existsById(req.getRoldId());
        if(existsRole) {
            user.setRoldId(req.getRoldId());
        }
        return user;
    }
}
