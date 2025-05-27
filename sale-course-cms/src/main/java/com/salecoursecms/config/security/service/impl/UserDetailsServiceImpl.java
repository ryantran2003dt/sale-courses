package com.salecoursecms.config.security.service.impl;

import com.salecoursecms.config.security.dto.UserDetailsImpl;
import com.salecoursecms.entity.first.RoleEntity;
import com.salecoursecms.entity.first.UserEntity;
import com.salecoursecms.repository.first.RoleRepository;
import com.salecoursecms.repository.first.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Lấy user từ database
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Xây dựng UserDetails từ user
        List<GrantedAuthority> authorities = getPermissionAndSetToListGrantedAuthority(user);

        // Trả về đối tượng UserDetails
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities);
    }


    @NotNull
    private List<GrantedAuthority> getPermissionAndSetToListGrantedAuthority(UserEntity accountExisted) {

        //Đoạn này do gpt viết phải tách ra đùng gộp lại để dễ trace log
        Optional<RoleEntity> roleOptional = roleRepository.findRoleById(accountExisted.getRoldId());
        log.info("roleOptional: {}", roleOptional);
        RoleEntity userRole = roleOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with role: " + accountExisted.getRoldId()));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_".concat(userRole.getRoleName())));

        return authorities;
    }
}
