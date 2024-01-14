package fullstack.website.blog.model.request;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPWRequest {
    private String token;
    private String password;
}
