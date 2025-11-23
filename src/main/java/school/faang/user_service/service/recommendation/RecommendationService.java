package school.faang.user_service.service.recommendation;

import school.faang.user_service.dto.recommendation.RecommendationDto;

public interface RecommendationService {

    /**
     * Создание рекомендации
     *
     * @param recommendationDto - параметры для создания рекомендации
     * @return RecommendationDto
     */
    RecommendationDto create(RecommendationDto recommendationDto);
}
