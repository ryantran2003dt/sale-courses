package com.salecoursecms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
//dùng để tạo blook list kết hợp với redis
@Service
@RequiredArgsConstructor
public class TokenBlocklistService {
    private final RedisTemplate<String, String> redisTemplate;

    public void blockToken(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(token, "BLOCKED", expirationMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlocked(String token) {
        return redisTemplate.hasKey(token);
    }
}
