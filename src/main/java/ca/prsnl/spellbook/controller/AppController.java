package ca.prsnl.spellbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/health")
public class AppController {

    private Logger log = LoggerFactory.getLogger(AppController.class);

    private static final String HEALTH_STRING = "I am here!";

    public AppController() {
    }

    /**
     * A method which simply checks if the servlet is running correctly
     * The user will know if the servlet is running if a response is received
     *
     * @return A string response
     */
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String healthCheck() {
        log.info("Operating on path /health");
        return HEALTH_STRING;
    }
}
