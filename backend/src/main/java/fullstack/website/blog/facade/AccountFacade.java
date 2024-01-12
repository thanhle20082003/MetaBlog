package fullstack.website.blog.facade;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.exception.common.*;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.service.IAccountService;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final IAccountService accountService;

    public Page<AccountDto> findAllAccount(SearchCriteria searchCriteria, Role role) throws ArchitectureException {
        Page<AccountDto> result = accountService.findAllByRole(searchCriteria, role);
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    public AccountDto create(AccountDto accountDto) throws ArchitectureException {
        if (accountDto.getId() != null) {
            throw new IdMustBeNullException(Category.class.getSimpleName());
        }
        AccountDto exist = accountService.findByUsernameOrEmailOrPhoneNumber(accountDto);
        if (exist != null) {
            throw new DescriptionException("Email, username or phone number already exists");
        }
        if (accountDto.getPassword().length() < 6) throw new DescriptionException("Password must be at least 6 characters");
        return accountService.createAccount(accountDto);
    }

    public AccountDto findByRoleAndUsernameOrEmail(Role role, String usernameOrEmail) throws ArchitectureException {
        if(role == Role.ADMIN) throw new DescriptionException("Admin can't access");
        if (usernameOrEmail.isEmpty())
            throw new InvalidParamException();
        AccountDto accountDto = accountService.findByRoleAndUsernameOrEmail(role, usernameOrEmail, usernameOrEmail);
        return checkAccountIsNotNull(accountDto);
    }

    public AccountDto findByUsernameOrEmail(String username, String email) throws ArchitectureException {
        if (username.isEmpty() || email.isEmpty())
            throw new InvalidParamException();
        return accountService.findByUsernameOrEmail(username, email);
    }

    public AccountDto update(AccountDto accountDto, Long accountId) throws ArchitectureException{
        AccountDto accountOld = accountService.findById(accountId);
        if(accountOld == null) throw new NotFoundException();
        if(!accountDto.getEmail().isEmpty()) {
            AccountDto account = accountService.findByUsernameOrEmail(accountDto.getEmail(), accountDto.getEmail());
            if(account != null && account.getEmail() != null && accountDto.getEmail().equals(account.getEmail()) && !accountDto.getEmail().equals(accountOld.getEmail())) {
                throw new DescriptionException("Email is exist");
            }
        }
        if(!accountDto.getPhoneNumber().isEmpty()) {
            AccountDto account = accountService.findByPhoneNumber(accountDto.getPhoneNumber());
            if(account != null && account.getPhoneNumber() != null && accountDto.getPhoneNumber().equals(account.getPhoneNumber()) && !accountDto.getPhoneNumber().equals(accountOld.getPhoneNumber())) {
                throw new DescriptionException("Phone number is exist");
            }
        }

        return accountService.updateAccount(accountDto, accountId);
    }

    private AccountDto checkAccountIsNotNull(AccountDto accountDto) throws NotFoundException {
        if (accountDto == null)
            throw new NotFoundException();
        return accountDto;
    }


}
