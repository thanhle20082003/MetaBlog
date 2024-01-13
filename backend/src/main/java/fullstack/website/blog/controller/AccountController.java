package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.AccountFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.AccountType;
import fullstack.website.blog.utils.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fullstack.website.blog.utils.api.ConstantsApi.Account.ACCOUNT_PATH;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping(ACCOUNT_PATH)
public class AccountController {

    private final AccountFacade accountFacade;

    @GetMapping("/{role}")
    @Operation(summary = "Get all account by role", description = "Get all account by role, only admin can access")
    public ResponseEntity<Object> findAllAccount(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "20") Integer size,
                                                 @RequestParam(defaultValue = "id") String columSort,
                                                 @RequestParam(required = false) Role role) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, accountFacade.findAllAccount(new SearchCriteria(page, size, columSort), role), true);
    }

    @PostMapping("/create")
    @Operation(summary = "Add new account", description =  "Add new account, only admin can access")
    public ResponseEntity<Object> createAccount(AccountDto accountDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.CREATED, accountFacade.create(accountDto), true);
    }

    @PutMapping("/update/{accountId}")
    @Operation(summary = "Update account", description = "Update for admin")
    public ResponseEntity<Object> updateAccount(AccountDto accountDto,
                                                @PathVariable Long accountId
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, accountFacade.update(accountDto, accountId), true);
    }

    @PutMapping("/lock/{accountId}")
    @Operation(summary = "Lock account", description = "Lock account for admin")
    public ResponseEntity<Object> lockAccount(@PathVariable Long accountId,
                                              @RequestParam AccountType type
    ) throws ArchitectureException {
        accountFacade.lockAndUnLock(accountId, type);
        return ResponseHandler.response(HttpStatus.LOCKED, "This account was locking", true);
    }

    @DeleteMapping("delete/{accountId}")
    @Operation(summary = "Delete account", description = "Delete account for admin")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long accountId) throws ArchitectureException {
        accountFacade.delete(accountId);
        return ResponseHandler.response(HttpStatus.OK, "Delete account successfully", true);
    }
}
