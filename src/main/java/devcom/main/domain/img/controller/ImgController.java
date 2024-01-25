package devcom.main.domain.img.controller;

import devcom.main.domain.img.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ImgController {
    private final ImgService imgService;
}
