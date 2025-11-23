package school.faang.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.User;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.WARN
)
public interface UserMapper {

    User toUser(CreateUserDto userDto);

    void updateFromDto(UpdateUserDto userDto, @MappingTarget User entity);

    UserDto toUserDto(User user);
}
