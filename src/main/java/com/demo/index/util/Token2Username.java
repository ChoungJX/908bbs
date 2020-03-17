package com.demo.index.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.demo.index.dao.UserDao;
import com.demo.index.domain.po.UserDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
 * Token2Username
 */

@Service
@Configurable
public class Token2Username {
    
    @Autowired
    private UserDao userDao;

    private UserDo userDo;

    public boolean ifUsername(String token) {
        if (token == null) {
            return false;
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return false;
        }
        UserDo userDo = userDao.findByUsername(userId);
        if (userDo == null) {
            return false;
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userDo.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        this.userDo = userDo;
        return true;
    }

    public UserDo getUserDo() {
        return userDo;
    }

    public void setUserDo(UserDo userDo) {
        this.userDo = userDo;
    }
}