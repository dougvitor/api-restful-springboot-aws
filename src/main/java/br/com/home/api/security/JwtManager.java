package br.com.home.api.security;

import br.com.home.api.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import static br.com.home.api.constants.SecurityConstants.*;
import java.util.Calendar;
import java.util.List;

@Component
public class JwtManager {

    public String createToken(String email, List<String> roles){
        Calendar dateExpiration = Calendar.getInstance();
        dateExpiration.add(Calendar.DAY_OF_MONTH, JWT_EXP_DAYS);
        String jwt = Jwts
                .builder()
                .setSubject(email)
                .setExpiration(dateExpiration.getTime())
                .claim(JWT_ROLE_KEY, roles)
                .signWith(SignatureAlgorithm.HS512, API_KEY.getBytes())
                .compact();
        return jwt;
    }

}
