package com.salecoursecms.service;

import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.request.CreateUserRequest;
import com.salecoursecms.dto.request.PagingRequest;
import com.salecoursecms.dto.request.UpdateUserRequest;

public interface UserService {
    BaseReponse<?> createUser(CreateUserRequest req);
    BaseReponse<?> findAllUSer(String search, PagingRequest pagingRequest);
    BaseReponse<?> findUserById(Long id);
    BaseReponse<?> updateStatus(Long id, int status);
    BaseReponse<?> updateUser(UpdateUserRequest req);
}
