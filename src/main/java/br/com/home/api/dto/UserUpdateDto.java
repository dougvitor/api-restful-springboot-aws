package br.com.home.api.dto;

import br.com.home.api.domain.Request;
import br.com.home.api.domain.RequestStage;
import br.com.home.api.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserUpdateDto {

    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @Size(min = 7, max = 99, message = "Password must be between 7 and 99")
    private String password;

    private List<Request> requests = new ArrayList<Request>();

    private List<RequestStage> stages = new ArrayList<RequestStage>();

    public User convertToUser() {
        return new User(
                null,
                this.name,
                this.email,
                this.password,
                null,
                this.requests,
                this.stages
        );
    }
}
