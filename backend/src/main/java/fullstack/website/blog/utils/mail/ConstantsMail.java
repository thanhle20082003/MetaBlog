package fullstack.website.blog.utils.mail;

public class ConstantsMail {

    public static final class Thymeleaf{
        public static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessages";
        public static final String MAIL_TEMPLATE_PREFIX = "/templates/";
        public static final String MAIL_TEMPLATE_SUFFIX = ".html";
        public static final String UTF_8 = "UTF-8";
    }

    public static final class Mail{
        public static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
        public static final class Subject{
            public static final String VERIFY_EMAIL_SUBJECT = "Verify your email account at MetaBlog";
            public static final String WELCOME_EMAIL_SUBJECT = "Welcome to MetaBlog";
            public static final String CHANGE_PASS_EMAIL_SUBJECT = "Change password success at The Trendy Fashionista";
        }
        public static final class Template{
            public static final String TEMPLATE_VERIFY_NAME = "verifyEmail";
            public static final String TEMPLATE_WELCOME_NAME = "welcomeEmail";
            public static final String TEMPLATE_CHANGE_PASS_NAME = "changePassEmail";
        }
    }
}
