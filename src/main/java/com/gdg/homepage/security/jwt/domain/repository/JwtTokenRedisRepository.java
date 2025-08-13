package com.gdg.homepage.security.jwt.domain.repository;

import com.gdg.homepage.security.jwt.domain.entity.JwtToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtTokenRedisRepository extends CrudRepository<JwtToken, String> {

    Optional<JwtToken> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

    Optional<JwtToken> findByUserId(Long userId);


}
