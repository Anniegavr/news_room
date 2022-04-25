package backend.registration.payload.request;

import backend.models.ERole;
import backend.models.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/** Constructs the sign up request that is sent to the server **/
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 6, max = 40)
    private Set<Role> roles;

//    public void setUsername(@NotBlank @Size(min = 3, max = 20) String username) {
//        this.username = username;
//    }

//    public void setPassword(@NotBlank @Size(min = 6, max = 40) String password) {
//        this.password = password;
//    }
}
