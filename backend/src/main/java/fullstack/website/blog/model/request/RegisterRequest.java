package fullstack.website.blog.model.request;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class RegisterRequest {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String fullName;

    @NonNull
    private Date birthday;

    @NonNull
    private String email;

    @NonNull
    private Boolean gender;


}
