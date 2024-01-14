package fullstack.website.blog.service.impl;

import fullstack.website.blog.service.IThymeleafService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static fullstack.website.blog.utils.api.fe.FrontEndApi.FRONT_END_BASE_API;
import static fullstack.website.blog.utils.mail.ConstantsMail.Mail.Template.*;
import static fullstack.website.blog.utils.mail.ConstantsMail.Thymeleaf.*;

@Service
@RequiredArgsConstructor
public class ThymeleafService implements IThymeleafService {

    private static final TemplateEngine templateEngine;

    static {
        templateEngine = IThymeleafService.emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Override
    public String getVerifyEmailContent(String token, String api) {
        final Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("url", api);
        return templateEngine.process(TEMPLATE_VERIFY_NAME, context);
    }

    @Override
    public String getWelcomeEmailContent(String username) {
        final Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("url", FRONT_END_BASE_API);
        return templateEngine.process(TEMPLATE_WELCOME_NAME, context);
    }

    @Override
    public String getForgotPWEmailContent(String username) {
        final Context context = new Context();
        context.setVariable("url", FRONT_END_BASE_API);
        context.setVariable("username", username);
        return templateEngine.process(TEMPLATE_FORGOT_PW_NAME, context);
    }
}