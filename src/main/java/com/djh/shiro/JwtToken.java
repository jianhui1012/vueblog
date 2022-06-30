package com.djh.shiro;

import org.apache.shiro.authc.AuthenticationToken;


public class JwtToken implements AuthenticationToken {


    private String token;

    public JwtToken(String jwtToken){
        token = jwtToken;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
