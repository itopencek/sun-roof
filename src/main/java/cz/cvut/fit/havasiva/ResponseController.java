package cz.cvut.fit.havasiva;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseController {
    public static final String template = "My response to you is: %s";

    @RequestMapping("/response")
    public TextResponse textResponse(@RequestParam(value="response", defaultValue="no response") String name) {
        return new TextResponse(String.format(template, name));
    }

}
