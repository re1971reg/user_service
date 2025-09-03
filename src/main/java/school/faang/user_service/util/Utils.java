package school.faang.user_service.util;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.FormattedMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utils {

    private final MessageSource messageSource;

    public String format(final String messagePattern, final Object arg) {
        return new FormattedMessage(messagePattern, arg).getFormattedMessage();
    }

    public String format(final String messagePattern, final Object firstArg, final Object secondArg) {
        return new FormattedMessage(messagePattern, firstArg, secondArg).getFormattedMessage();
    }

    public String format(final String messagePattern, final Object... args) {
        return new FormattedMessage(messagePattern, args).getFormattedMessage();
    }

    public String getMessage(final String code, final Object arg) {
        String message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        return format(message, arg);
    }
}
