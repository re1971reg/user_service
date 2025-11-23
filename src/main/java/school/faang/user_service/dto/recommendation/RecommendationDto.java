package school.faang.user_service.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import school.faang.user_service.validator.Marker;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@Schema(description = "Сущность для рекомендации")
public class RecommendationDto {
    @NotNull(
        message = "{validation.recommendationDto.id.notNull}",
        groups = Marker.OnUpdate.class
    )
    @Schema(description = "Идентификатор рекомендации")
    private Long id;

    // в controller информация приходит из header="x-user-id"
    @NotNull(
        message = "{validation.recommendationDto.authorId.notNull}",
        groups = {Marker.OnCreate.class, Marker.OnUpdate.class}
    )
    @Schema(description = "Автор рекомендации")
    private Long authorId;

    @NotBlank(
        message = "{validation.recommendationDto.receiverId.notNull}",
        groups = {Marker.OnCreate.class, Marker.OnUpdate.class}
    )
    @Schema(description = "Идентификатор пользователя на кого делается рекомендация")
    private Long receiverId;

    @NotBlank(
        message = "{validation.recommendationDto.content.notBlank}",
        groups = {Marker.OnCreate.class, Marker.OnUpdate.class}
    )
    @Schema(description = "Описание рекомендации")
    private String content;

    private List<SkillOfferDto> skillOffers;
    private LocalDateTime createdAt;
}
