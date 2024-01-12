package fullstack.website.blog.model.dto;

import fullstack.website.blog.utils.enums.AccountType;
import fullstack.website.blog.utils.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Builder
public class AccountDto {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Boolean gender;

    private Date birthday;

    private String image;

    private MultipartFile imageFile;

    private String address;

    private Role role;

    private AccountType type;

    private Date lastAccessDate;

    private String accessToken;
}
