package school.faang.user_service.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Сущность пользователя")
public record UserDto(
    @Schema(description = "Идентификатор пользователя в системе")
    Long id,
    @Schema(description = "Наименование пользователя")
    String username,
    @Schema(description = "E-mail пользователя")
    String email,
    @Schema(description = "Телефон пользователя")
    String phone,
    @Schema(description = "Краткая информация о пользователе")
    String aboutMe
) {
}
