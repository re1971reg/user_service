package school.faang.user_service.dto.skill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.validator.Marker;

import java.util.List;

@Data
@Schema(description = "Сущность навыка")
public class SkillDto {
    @NotNull(groups = Marker.OnUpdate.class, message = "{validation.skillDto.id.notNull}")
    @Positive(groups = Marker.OnUpdate.class)
    @Schema(description = "Идентификатор пользователя")
    private Long id;

    @NotBlank(
        message = "{validation.skillDto.title.notEmpty}",
        groups = {Marker.OnCreate.class, Marker.OnUpdate.class}
    )
    @Schema(description = "Наименование навыка")
    private String title;

    @Schema(description = "Рекомендатели навыка")
    private List<UserDto> guarantors;
}
