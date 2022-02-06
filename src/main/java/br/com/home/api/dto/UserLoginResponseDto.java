package br.com.home.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -2233673204577309876L;

    private String token;

    private Long expireIn;

    private String tokenProvider;
}
