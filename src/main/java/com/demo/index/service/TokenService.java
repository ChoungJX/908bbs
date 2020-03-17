package com.demo.index.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.index.domain.po.UserDo;

import org.springframework.stereotype.Service;


@Service("TokenService")
public class TokenService {
    public String getToken(UserDo userDo) {
        String token="";
        token= JWT.create().withAudience(userDo.getUsername())//将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(userDo.getPassword()));// 以 password 作为 token 的密钥
        return token;
    }
}
