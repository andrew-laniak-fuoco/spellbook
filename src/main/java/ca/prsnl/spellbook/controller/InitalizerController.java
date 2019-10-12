package ca.prsnl.spellbook.controller;

import ca.prsnl.spellbook.repository.dto.Spell;
import ca.prsnl.spellbook.service.FileService;
import ca.prsnl.spellbook.util.ResponseExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/init")
public class InitalizerController {

    private Logger log = LoggerFactory.getLogger(InitalizerController.class);

    FileService fileService;

    @Autowired
    public InitalizerController(FileService fs) {
        fileService = fs;
    }

    @GetMapping(path = "/loaders")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> fileNames() {
        log.info("Operating on path /read/loaders");
        try {
            return fileService.getLoaders();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/database")
    @ResponseStatus(HttpStatus.OK)
    public void initalizeDatabase() {
        log.info("Operating on path /init/database");
        try {
            fileService.loadDatabaseFromFile("/5e-SRD-Spells.json");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/read/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Spell> readAll() {
        log.info("Operating on path /read/all");
        try {
            return fileService.getEverything();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
