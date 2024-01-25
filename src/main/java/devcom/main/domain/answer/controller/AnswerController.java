package devcom.main.domain.answer.controller;

import devcom.main.domain.answer.Service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
}
