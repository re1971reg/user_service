package school.faang.user_service.service.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.mapper.RecommendationMapper;
import school.faang.user_service.repository.recommendation.RecommendationRepository;
import school.faang.user_service.service.user.UserService;
import school.faang.user_service.validator.Validator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;
    private final UserService userService;
    private final List<Validator<RecommendationDto>> validators;

    @Override
    public RecommendationDto create(RecommendationDto recommendationDto) {
        validators.forEach(validator -> validator.validate(recommendationDto));
        User author = userService.getByIdOrThrow(recommendationDto.getAuthorId());
        User receiver = userService.getByIdOrThrow(recommendationDto.getReceiverId());

        return new RecommendationDto();
    }
}
