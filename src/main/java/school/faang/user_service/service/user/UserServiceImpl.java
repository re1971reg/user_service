package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.CreateUserDto;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.Country;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.exception.EntityNotFoundException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.user.CountryRepository;
import school.faang.user_service.repository.user.UserRepository;
import school.faang.user_service.util.Utils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND = "user.notFound";
    public static final String PSW_LENGTH_ERROR = "password.length.error";
    public static final String USER_DONT_MATCH_PROFILE = "user.dont.match.profile";

    @Value("${user.password.min-length:6}")
    private int minPasswordLength;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final UserMapper userMapper;
    private final UserContext userContext;
    private final Utils utils;

    @Override
    public UserDto create(CreateUserDto userDto) {
        if (userDto.password().length() < minPasswordLength) {
            throw new DataValidationException(utils.getMessage(PSW_LENGTH_ERROR, minPasswordLength));
        }
        User user = userMapper.toUser(userDto);
        Country country = countryRepository.getByIdOrThrow(userDto.countryId());
        user.setCountry(country);
        user = userRepository.save(user);
        log.info("User {} created", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto update(Long userId, UpdateUserDto userDto) {
        verifyUserIsProfileOwner(userId);
        User user = getByIdOrThrow(userId);
        userMapper.update(userDto, user);
        Country country = countryRepository.getByIdOrThrow(userDto.countryId());
        user.setCountry(country);
        user = userRepository.save(user);
        log.info("User {} updated", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    public void verifyUserIsProfileOwner(Long userId) {
        long requesterId = userContext.getUserId();
        if (userId != requesterId) {
            throw new ForbiddenException(utils.getMessage(USER_DONT_MATCH_PROFILE, requesterId));
        }
    }

    @Override
    public UserDto getById(Long userId) {
        User user = getByIdOrThrow(userId);
        return userMapper.toUserDto(user);
    }

    @Override
    public User getByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(
                utils.getMessage(USER_NOT_FOUND, userId)));
    }
}
