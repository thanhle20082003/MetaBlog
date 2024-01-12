package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.service.IEncodePassword;
import fullstack.website.blog.utils.enums.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AccountMapper implements Function<Account, AccountDto> {

    private final IEncodePassword encodePassword;
    @Override
    public AccountDto apply(Account account) {
        return AccountDto
                .builder()
                .id(account.getId())
                .image(account.getImage())
                .email(account.getEmail())
                .address(account.getAddress())
                .accessToken(account.getAccessToken())
                .birthday(account.getBirthday())
                .gender(account.getGender())
                .phoneNumber(account.getPhoneNumber())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .type(account.getType())
                .lastAccessDate(account.getLastAccessDate())
                .build();
    }

    public Account applyToAccount(AccountDto accountDto) {
        return Account
                .builder()
                .id(accountDto.getId())
                .image(accountDto.getImage())
                .email(accountDto.getEmail())
                .address(accountDto.getAddress())
                .password(accountDto.getPassword())
                .accessToken(accountDto.getAccessToken())
                .birthday(accountDto.getBirthday())
                .gender(accountDto.getGender())
                .phoneNumber(accountDto.getPhoneNumber())
                .role(accountDto.getRole())
                .username(accountDto.getUsername())
                .fullName(accountDto.getFullName())
                .password(accountDto.getPassword())
                .type(accountDto.getType() == null ?
                        AccountType.ACTIVE :
                        accountDto.getType())
                .lastAccessDate(accountDto.getLastAccessDate())
                .build();
    }



}