package br.com.home.api.dto;

import br.com.home.api.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserUpdateRoleDto {

    @NotNull(message = "Role required")
    private Role role;

}
