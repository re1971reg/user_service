package school.faang.user_service.dto.recommendation;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class RecommendationDto {
    private Long id;
    private Long authorId;
    private Long receiverId;
    private String content;
    private List<SkillOfferDto> skillOffers;
    private LocalDateTime createdAt;
}
