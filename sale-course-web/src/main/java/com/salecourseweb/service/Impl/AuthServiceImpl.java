package com.salecourseweb.service.Impl;

import com.salecourseweb.dto.request.IdTokenRequest;
import com.salecourseweb.entity.first.StudentEntity;
import com.salecourseweb.repository.first.StudentRepository;
import com.salecourseweb.service.AuthService;
import com.salecourseweb.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JWTUtils jwtUtils;
    private final StudentRepository studentRepository;
    private final GoogleIdTokenVerifier verifier;


    @Override
    public String loginOAuthGoogle(IdTokenRequest requestBody) {
        StudentEntity account = verifyIDToken(requestBody.getIdToken());
        if (account == null) {
            throw new IllegalArgumentException();
        }
        account = createOrUpdateUser(account);
        return jwtUtils.createToken(account, false);
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
            studentRepository.save(account);
            return account;
        }
        existingAccount.setFullName(account.getFullName());
        existingAccount.setAvatarUrl(account.getAvatarUrl());
        existingAccount.setCreateDate(new Date());
        studentRepository.save(existingAccount);
        return existingAccount;
    }
}
