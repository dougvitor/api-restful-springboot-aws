package br.com.home.api.security;

import br.com.home.api.service.util.HashUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return getHash(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hash = getHash(rawPassword);
        return hash.equals(encodedPassword);
    }

    private String getHash(CharSequence rawPassword) {
        return HashUtil.getSecureHash(rawPassword.toString());
    }
}
