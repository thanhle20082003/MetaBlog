package fullstack.website.blog.model.response;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.utils.enums.AccountType;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JWTAuthenticationResponse {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String image;
    private Date birthday;
    private Boolean gender;
    private AccountType accountType;
    private String accessToken;
    private String refreshToken;
    private Integer path;

    public static JWTAuthenticationResponse apply(
            Account account, String accessToken, String refreshToken) {
        return JWTAuthenticationResponse
                .builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .phoneNumber(account.getPhoneNumber())
                .address(account.getAddress())
                .image(account.getImage())
                .birthday(account.getBirthday())
                .gender(account.getGender())
                .accountType(account.getType())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .path(account.getRole().ordinal())
                .build();
    }
}
