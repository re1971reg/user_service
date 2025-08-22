package school.faang.user_service.service.skill;

import school.faang.user_service.dto.skill.SkillDto;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    boolean existsByTitle(String title);

    SkillDto create(SkillDto skillDto);

    SkillDto update(SkillDto skillDto);

    Optional<SkillDto> findByTitle(String title);

    List<SkillDto> getUserSkills(Long userId);
}
