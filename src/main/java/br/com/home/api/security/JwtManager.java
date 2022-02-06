package br.com.home.api.security;

import br.com.home.api.dto.UserLoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

import static br.com.home.api.constants.SecurityConstants.*;

@Component
public class JwtManager {

    public UserLoginResponseDto createToken(String email, List<String> roles) {
        Calendar dateExpiration = Calendar.getInstance();
        dateExpiration.add(Calendar.DAY_OF_MONTH, JWT_EXP_DAYS);
        String jwt = Jwts
                .builder()
                .setSubject(email)
                .setExpiration(dateExpiration.getTime())
                .claim(JWT_ROLE_KEY, roles)
                .signWith(SignatureAlgorithm.HS512, API_KEY.getBytes())
                .compact();

        Long expireIn = dateExpiration.getTimeInMillis();

        return new UserLoginResponseDto(jwt, expireIn, JWT_PROVIDER);
    }

    public static Claims parseToken(String jwt) throws JwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(API_KEY.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

}
