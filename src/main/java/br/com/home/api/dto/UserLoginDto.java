package br.com.home.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @Email(message = "Invalid email adress")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
