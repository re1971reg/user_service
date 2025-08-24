package school.faang.user_service.service.skill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.user.Skill;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;
import school.faang.user_service.repository.user.SkillRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    public static final String SKILL_EXISTS = "skill.exists";

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final SkillOfferRepository skillOfferRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTitle(String title) {
        return skillRepository.existsByTitleIgnoreCase(title);
    }

    @Override
    @Transactional
    public SkillDto create(SkillDto skillDto) {
        log.debug("service request to save skill : {}", skillDto);
        if (skillRepository.existsByTitleIgnoreCase(skillDto.getTitle())) {
            String message = messageSource.getMessage(
                SKILL_EXISTS, new Object[]{skillDto.getTitle()}, LocaleContextHolder.getLocale());
            throw new ForbiddenException(message);
        }
        Skill skill = skillMapper.toSkill(skillDto);
        Skill result = skillRepository.save(skill);
        return skillMapper.toSkillDto(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDto> getUserSkills(Long userId) {
        List<Skill> skills = skillRepository.findAllByUserId(userId);
        return skills.stream().map(skillMapper::toSkillDto).toList();
    }

    // @Override
    // @Transactional
    // public SkillDto update(SkillDto skillDto) {
    //     return null;
    // }

    // @Override
    // @Transactional(readOnly = true)
    // public Optional<SkillDto> findByTitle(String title) {
    //     return Optional.empty();
    // }

    @Override
    @Transactional(readOnly = true)
    public List<SkillCandidateDto> getOfferedSkills(long userId) {
        List<Skill> skills = skillRepository.findSkillsOfferedToUser(userId);
        return skills.stream()
            .map(skill -> {
                int offersAmount = skillOfferRepository.countAllOffersOfSkill(skill.getId(), userId);
                return new SkillCandidateDto(skillMapper.toSkillDto(skill), offersAmount);
            })
            .toList();
    }
}
