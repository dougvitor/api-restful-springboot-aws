package br.com.home.api.security;

import br.com.home.api.exception.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.home.api.constants.SecurityConstants.*;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwt == null || !jwt.startsWith(JWT_PROVIDER)){
            sendError(httpServletResponse, JWT_INVALID_MSG);
            return;
        }

        jwt = jwt.replace(JWT_PROVIDER, "");

        try{
            final Claims claims = JwtManager.parseToken(jwt);

            String email = claims.getSubject();
            List<String> roles = (List<String>) claims.get(JWT_ROLE_KEY);

            List<GrantedAuthority> grantedAuthorities = roles.stream().map( role -> {return new SimpleGrantedAuthority(role); })
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch(Exception e){
            sendError(httpServletResponse, e.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void sendError(HttpServletResponse httpServletResponse, String msg) throws IOException {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), msg, new Date());
        PrintWriter writer = httpServletResponse.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        final String apiErrorString = mapper.writeValueAsString(apiError);

        writer.write(apiErrorString);

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
