package devcom.main.domain.skill.controller;


import devcom.main.domain.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;


}
