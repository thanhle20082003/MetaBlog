package fullstack.website.blog.service;

import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.Role;
import org.springframework.data.domain.Page;

public interface IAccountService {
    AccountDto findById(Long id);

    AccountDto findByRoleAndUsernameOrEmail(Role role, String username, String email);

    Page<AccountDto> findAllByRole(SearchCriteria searchCriteria, Role role);

    AccountDto createAccount(AccountDto accountDto);

    AccountDto findByUsernameOrEmail(String username, String email);

    void deleteAccount(Long id);

    AccountDto findByUsernameOrEmailOrPhoneNumber(AccountDto accountDto);

    AccountDto findByPhoneNumber(String phoneNumber);

    AccountDto updateAccount(AccountDto accountDto, Long accountId);
}
