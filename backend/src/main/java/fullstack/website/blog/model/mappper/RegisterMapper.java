package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.model.request.RegisterRequest;
import fullstack.website.blog.service.IEncodePassword;
import fullstack.website.blog.utils.enums.AccountType;
import fullstack.website.blog.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RegisterMapper implements Function<RegisterRequest, Account> {

    private final IEncodePassword encodePassword;

    @Override
    public Account apply(RegisterRequest request) {
        return Account
                .builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodePassword.encodePassword(request.getPassword()))
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .type(AccountType.UNVERIFIED)
                .role(Role.WRITER)
                .build();
    }

}
