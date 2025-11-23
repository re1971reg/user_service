package school.faang.user_service.config.context;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UserLocaleFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.debug("request locale is: {}", httpRequest.getLocale().toString());
        // Получаем локаль из заголовка Accept-Language
        LocaleContextHolder.setLocale(httpRequest.getLocale());
        // Продолжаем обработку запроса дальше по цепочке фильтров/контроллеров
        chain.doFilter(request, response);
    }
}
