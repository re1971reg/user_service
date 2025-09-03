package school.faang.user_service.service.skill;

import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;

import java.util.List;

public interface SkillService {

    boolean existsByTitle(String title);

    /**
     * Создание нового навыка
     *
     * @param skillDto - свойства навыка
     * @return - Информация по навыку с указанием его id
     */
    SkillDto create(SkillDto skillDto);

    // SkillDto update(SkillDto skillDto);

    // Optional<SkillDto> findByTitle(String title);

    /**
     * Возвращает список навыков пользователя по его id
     *
     * @param userId - идентификатор пользователя
     * @return {@code List<SkillDto>} - массив навыков
     */
    List<SkillDto> getUserSkills(Long userId);

    /**
     * Возвращает список предложенных навыков пользователя
     *
     * @param userId - идентификатор пользователя
     * @return - массив предложенных навыков
     */
    List<SkillCandidateDto> getOfferedSkills(Long userId);


    /**
     * Метод для приобретения предложенных навыков
     *
     * @param skillId - идентификатор навыка
     * @param userId  - идентификатор пользователя
     */
    SkillDto acquireSkillFromOffers(long skillId, long userId);
}
