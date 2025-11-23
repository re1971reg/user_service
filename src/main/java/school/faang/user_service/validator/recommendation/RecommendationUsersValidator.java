package school.faang.user_service.validator.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.util.Utils;
import school.faang.user_service.validator.Validator;

@Component
@RequiredArgsConstructor
public class RecommendationUsersValidator implements Validator<RecommendationDto> {

    private static final String USERS_CANT_BE_SAME = "validation.recommendationDto.users.cantBeSame";

    private final Utils utils;

    @Override
    public void validate(RecommendationDto recommendationDto) {
        if (recommendationDto.getAuthorId().equals(recommendationDto.getReceiverId())) {
            throw new DataValidationException(utils.getMessage(USERS_CANT_BE_SAME));
        }
    }
}
