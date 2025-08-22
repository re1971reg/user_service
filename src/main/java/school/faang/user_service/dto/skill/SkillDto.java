package school.faang.user_service.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.validator.group.CreateDto;
import school.faang.user_service.validator.group.UpdateDto;

import java.util.List;

@Data
public class SkillDto {
    /* todo: настроить, чтобы сообщение в аннотации @NotNull доставалось из messages.properties
     */
    @NotNull(groups = UpdateDto.class)
    @Positive(groups = UpdateDto.class)
    private Long id;
    @NotBlank(
        message = "The 'title' can't be empty!",
        groups = {CreateDto.class, UpdateDto.class}
    )
    private String title;
    private List<UserDto> guarantors;
}
