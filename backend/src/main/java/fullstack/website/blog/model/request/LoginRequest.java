package fullstack.website.blog.model.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {

    private String usernameOrEmail;
    private String password;

}
