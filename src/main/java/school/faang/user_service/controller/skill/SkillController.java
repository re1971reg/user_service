package school.faang.user_service.controller.skill;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.service.skill.SkillService;
import school.faang.user_service.validator.group.CreateDto;

import java.util.List;

@Tag(name = "Skill controller API", description = "API for skill controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    @Operation(
        summary = "Get the user's suggested skills",
        description = "The request provides a list of suggested user skills"
    )
    @GetMapping("/offered")
    public List<SkillCandidateDto> getOfferedSkills(@RequestHeader(name = "x-user-id") long userId) {
        return skillService.getOfferedSkills(userId);
    }

    @Operation(summary = "Create new skill", description = "Request to create new skill")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto create(@RequestBody @Validated(CreateDto.class) SkillDto skillDto) {
        return skillService.create(skillDto);
    }
}
