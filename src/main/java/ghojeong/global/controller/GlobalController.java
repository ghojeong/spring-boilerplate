package ghojeong.global.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
