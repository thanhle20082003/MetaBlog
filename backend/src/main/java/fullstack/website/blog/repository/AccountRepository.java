package fullstack.website.blog.repository;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.utils.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAllByRole(Pageable pageable, Role role);

    Optional<Account> findByUsernameOrEmail(String username, String email);

    Optional<Account> findByPhoneNumber(String phoneNumber);

    Optional<Account> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Optional<Account> findByRoleAndEmailOrUsername(Role role, String username, String email);
}
