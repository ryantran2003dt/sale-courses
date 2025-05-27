package com.salecoursecms.config.security.service;

import com.salecoursecms.config.security.dto.JwtInfoConfig;
import com.salecoursecms.constant.AppConst;
import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.entity.first.UserEntity;
import com.salecoursecms.exception.ResourceNotFoundException;
import com.salecoursecms.repository.first.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtInfoConfig jwtInfoConfig;
    private final UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean extractIsAccessToken(String token) {
        return extractClaim(token, claims -> claims.get(AppConst.IS_ACCESS_TOKEN_CLAIM, Boolean.class));
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof List) {
            return ((List<?>) rolesObj).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof List) {
            return ((List<?>) rolesObj).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        } else if (rolesObj != null) {
            return rolesObj.toString();
        }
        return "";
    }

    public Long extractIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("jti");

        if (rolesObj instanceof String) {
            try {
                return Long.parseLong((String) rolesObj);
            } catch (NumberFormatException e) {
                // Nếu giá trị không phải là chuỗi số hợp lệ
                e.printStackTrace();
                return null;
            }
        } else if (rolesObj instanceof Number) {
            return ((Number) rolesObj).longValue();
        }

        return null; // Trường hợp không thể ép kiểu
    }



    public String generateAccessToken(UserDetails userDetails ) {

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", role);

        UserEntity user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND));

        return Jwts.builder()
                .setClaims(claims)
                .setId(String.valueOf(user.getId()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtInfoConfig.getAccessTokenExpiredTime()))
                .signWith(getPrivateKey())
                .compact();

    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .claim(AppConst.IS_ACCESS_TOKEN_CLAIM, false)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtInfoConfig.getRefreshTokenExpiredTime()))
                .signWith(getPrivateKey())
                .compact();
    }

    public boolean isTokenInvalid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // hàm này và hàm kế tiếp dùng để kết hợp với redis blocklist để logout
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token không hợp lệ (hết hạn, sai chữ ký, v.v.)
            return false;
        }
    }

    public long getExpirationMillis(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().getTime();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getPublicKey() {
        try (InputStream inputStream = getClass().getResourceAsStream("/keys/public_key.pem")) {
            String key = new String(inputStream.readAllBytes());

            key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", ""); // Xóa khoảng trắng hoặc xuống dòng

            byte[] keyBytes = Base64.getDecoder().decode(key);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving public key from file", e);
        }
    }




    private Key getPrivateKey() {
        try (InputStream inputStream = getClass().getResourceAsStream("/keys/private_key.pem")) {

            String key = new String(inputStream.readAllBytes());

            key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", ""); // Xóa khoảng trắng hoặc xuống dòng

            byte[] keyBytes = Base64.getDecoder().decode(key);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving private key from file", e);
        }
    }
}
