package school.faang.user_service.controller.skill;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.service.skill.SkillService;
import school.faang.user_service.validator.Marker;

import java.util.List;

@Slf4j
@Tag(name = "Навыки", description = "Операции с навыками")
@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
@Validated
public class SkillController {

    private final SkillService skillService;

    @Operation(
        summary = "Список предложенных навыков пользователя"
    )
    @GetMapping("/offered")
    @ResponseStatus(HttpStatus.OK)
    public List<SkillCandidateDto> getOfferedSkills(
        @Parameter(name = "Идентификатор пользователя")
        @RequestHeader(name = "x-user-id")
        Long userId
    ) {
        return skillService.getOfferedSkills(userId);
    }

    @Operation(summary = "Создание нового навыка")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(Marker.OnCreate.class)
    public SkillDto create(@RequestBody @Valid SkillDto skillDto) {
        log.debug("REST request to save skill : {}", skillDto);
        return skillService.create(skillDto);
    }

    @Operation(summary = "Приобретение предложенного навыка")
    @GetMapping("/{skillId}/acquire")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto acquireSkillFromOffers(
        @Parameter(description = "Идентификатор навыка")
        @PathVariable("skillId") Long skillId,
        @Parameter(description = "Идентификатор пользователя")
        @RequestHeader(name = "x-user-id") Long userId
    ) {
        log.debug("REST acquire skill from offers. skillId: {}, userId: {}", skillId, userId);
        return skillService.acquireSkillFromOffers(skillId, userId);
    }
}
