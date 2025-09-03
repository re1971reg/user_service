package school.faang.user_service.service.skill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.user.Skill;
import school.faang.user_service.exception.EntityExistsException;
import school.faang.user_service.exception.ForbiddenException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.recommendation.SkillOfferRepository;
import school.faang.user_service.repository.user.SkillRepository;
import school.faang.user_service.service.user.UserService;
import school.faang.user_service.util.Utils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    public static final String SKILL_EXISTS = "skill.exists";
    public static final String USER_HAS_SKILL = "user.skill.exists";
    public static final String MIN_SKILL_OFFER = "skill.offer.min";

    private final SkillRepository skillRepository;
    private final SkillOfferRepository skillOfferRepository;
    private final SkillMapper skillMapper;
    private final UserService userService;
    private final Utils utils;

    @Value("${skill.offer.min:3}")
    private int minSkillOffer;

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
            throw new ForbiddenException(utils.getMessage(SKILL_EXISTS, skillDto.getTitle()));
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
    public List<SkillCandidateDto> getOfferedSkills(Long userId) {
        List<Skill> skills = skillRepository.findSkillsOfferedToUser(userId);
        return skills.stream()
            .map(skill -> {
                int offersAmount = skillOfferRepository.countAllOffersOfSkill(skill.getId(), userId);
                return new SkillCandidateDto(skillMapper.toSkillDto(skill), offersAmount);
            })
            .toList();
    }

    @Override
    public SkillDto acquireSkillFromOffers(long skillId, long userId) {
        userService.verifyUserIsProfileOwner(userId);
        /* todo:
         *  - проверить, что текущего навыка у пользователя ещё нет
         *  - пользователь может присвоить навык себе, если другие пользователи порекомендовали ему этот
         *    навык не менее трех раз
         *  - всех пользователей, которые являлись авторами рекомендаций из каждого объекта offer,
         *    назначить гарантами навыка пользователя */
        // проверить наличие пользователя в БД
        UserDto user = userService.getById(userId);
        // проверить наличие навыка в БД
        Skill skillInDb = skillRepository.getByIdOrThrow(skillId);

        // проверить, что текущего навыка у пользователя ещё нет
        skillRepository.findUserSkill(skillId, userId)
            .ifPresent(skill -> {
                throw new EntityExistsException(utils.getMessage(USER_HAS_SKILL, skillId));
            });

        // пользователь может присвоить навык себе, если другие пользователи порекомендовали ему этот
        // навык не менее трех раз
        int offerCount = skillOfferRepository.countAllOffersOfSkill(skillId, userId);
        if (offerCount < minSkillOffer) {
            throw new ForbiddenException(utils.getMessage(MIN_SKILL_OFFER, minSkillOffer));
        }

        skillRepository.assignSkillToUser(skillId, userId);

        // всех пользователей, которые являлись авторами рекомендаций из каждого объекта offer,
        // назначить гарантами навыка пользователя
        return new SkillDto();
    }
}
