package school.faang.user_service.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class CommonConfig {

    /**
     * <ul>
     *      <li>Для определения локали запроса был сделан фильтр UserLocaleFilter</li>
     *      <li>Пример вывода сообщений из messages.properties для аннотаций валидации (@NotNull, @Null и т.д.)
     *      * <a href="https://struchkov.dev/blog/ru/spring-multilingual-application/">здесь</a></li>
     * </ul>
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
