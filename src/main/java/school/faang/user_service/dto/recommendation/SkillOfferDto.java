package school.faang.user_service.dto.recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Сущность предложенного навыка в рекомендации")
public class SkillOfferDto {
    @Schema(description = "Идентификатор предложения навыка")
    private Long id;

    @NotNull(
        message = "{validation.skillOfferDto.skillId.notNull}"
    )
    @Schema(description = "Идентификатор навыка")
    private Long skillId;
}
