package fullstack.website.blog.service.impl;

import fullstack.website.blog.service.IEncodePassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncodePassword implements IEncodePassword {

    private final PasswordEncoder passwordEncoder;


    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }
}
