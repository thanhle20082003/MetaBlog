package fullstack.website.blog.service;

public interface IEncodePassword {
    String encodePassword(String password);

    boolean matches(String password, String encodePassword);
}
