package fullstack.website.blog.service.impl;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.model.mappper.AccountMapper;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.service.IAccountService;
import fullstack.website.blog.service.IEncodePassword;
import fullstack.website.blog.service.IStorageService;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static fullstack.website.blog.utils.common.Search.getPageable;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final IStorageService storageService;
    private final IEncodePassword encode;

    @Override
    public AccountDto findById(Long id) {

        Optional<Account> account = accountRepository.findById(id);

        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public AccountDto findByRoleAndUsernameOrEmail(Role role, String username, String email) {
        Optional<Account> account = accountRepository.findByRoleAndEmailOrUsername(role, username, email);
        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public Page<AccountDto> findAllByRole(SearchCriteria searchCriteria, Role role) {

        Page<Account> accounts = accountRepository.findAllByRole(getPageable(searchCriteria), role);

        return accounts.map(accountMapper::apply);
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        if (accountDto.getImageFile() != null) {
           accountDto.setImage(storageService.store(accountDto.getImageFile()));
        }
        accountDto.setPassword(encode.encodePassword(accountDto.getPassword()));
        accountDto.setRole(Role.ADMIN);
        return accountMapper.apply(accountRepository.save(accountMapper.applyToAccount(accountDto)));
    }

    @Override
    public AccountDto findByUsernameOrEmail(String username, String email) {
        Optional<Account> account = accountRepository.findByUsernameOrEmail(username, email);
        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto findByUsernameOrEmailOrPhoneNumber(AccountDto accountDto) {
        Optional<Account> account = accountRepository.findByUsernameOrEmailOrPhoneNumber(accountDto.getUsername(), accountDto.getEmail(), accountDto.getPhoneNumber());
        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public AccountDto findByPhoneNumber(String phoneNumber) {
        Optional<Account> account = accountRepository.findByPhoneNumber(phoneNumber);
        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto, Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        if (accountDto.getImageFile() != null) {
            accountDto.setImage(storageService.store(accountDto.getImageFile()));
        } else {
            accountDto.setImage(account.getImage());
        }
        if(account.getRole() == Role.ADMIN) {
            accountDto.setPassword(encode.encodePassword(
                    accountDto.getPassword()));
        }
        accountDto.setId(account.getId());
        accountDto.setRole(account.getRole());
        accountDto.setType(account.getType());
        accountDto.setAccessToken(account.getAccessToken());
        accountDto.setLastAccessDate(account.getLastAccessDate());
        accountDto.setPassword(account.getPassword());
        accountDto.setUsername(account.getUsername());
        return accountMapper.apply(accountRepository.save(accountMapper.applyToAccount(accountDto)));
    }

}
