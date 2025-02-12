package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RateLimitedController {

    @GetMapping("/limited-endpoint")
    @ResponseBody
    public String limitedEndpoint() {
        return "Request successful!";
    }
}
