package br.com.home.api.dto;

import br.com.home.api.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDto {

    private Role role;

}
