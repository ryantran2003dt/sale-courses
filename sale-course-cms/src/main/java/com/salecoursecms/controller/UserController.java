package com.salecoursecms.controller;

import com.salecoursecms.constant.UrlConst;
import com.salecoursecms.dto.request.CreateUserRequest;
import com.salecoursecms.dto.request.PagingRequest;
import com.salecoursecms.dto.request.UpdateUserRequest;
import com.salecoursecms.dto.request.UpdateStatusRequest;
import com.salecoursecms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(UrlConst.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(UrlConst.GET_LIST)
    public ResponseEntity<?> getAllUser(String search, PagingRequest pagingRequest) {
        return ResponseEntity.ok(userService.findAllUSer(search, pagingRequest));
    }
    @PostMapping(UrlConst.CREATE)
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequest req){
        return ResponseEntity.ok(userService.createUser(req));
    }
    @PostMapping(UrlConst.GET_BY_ID)
    public ResponseEntity<?> getUserById(@RequestBody Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }
    @PostMapping(UrlConst.UPDATE_STATUS)
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusRequest req){
        return ResponseEntity.ok(userService.updateStatus(req.getId(), req.getStatus()));
    }
    @PostMapping(UrlConst.UPDATE)
    public ResponseEntity<?> updateInfoUser(@RequestBody @Valid UpdateUserRequest req){
        return ResponseEntity.ok(userService.updateUser(req));
    }
}
