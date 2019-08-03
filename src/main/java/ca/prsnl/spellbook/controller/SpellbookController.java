package ca.prsnl.spellbook.controller;

import ca.prsnl.spellbook.repository.dto.Spell;
import ca.prsnl.spellbook.service.SpellbookService;
import ca.prsnl.spellbook.util.ResponseExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class SpellbookController {

    private Logger log = LoggerFactory.getLogger(SpellbookController.class);

    private SpellbookService service;

    @Autowired
    public SpellbookController(SpellbookService sbs) {
        this.service = sbs;
    }

    @GetMapping(path = "/read/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spell readSpell(@PathVariable String name) {
        log.info("Operating on path /read/" + name);
        try {
            return service.getSpell(name);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/read/list")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> readNames() {
        log.info("Operating on path /read/list");
        try {
            return service.getSpellList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/read/loaders")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> fileNames() {
        log.info("Operating on path /read/loaders");
        try {
            return service.getLoaders();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/insert")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spell insertSpell(@RequestBody Spell spell) {
        try {
            return service.insertSpell(spell);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/init/database")
    @ResponseStatus(HttpStatus.OK)
    public void initalizeDatabase() {
        log.info("Operating on path /init/database");
        try {
            service.initdb();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
