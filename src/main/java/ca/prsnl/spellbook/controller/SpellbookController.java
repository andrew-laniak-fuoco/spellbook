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
@RequestMapping("/spell")
public class SpellbookController {

    private Logger log = LoggerFactory.getLogger(SpellbookController.class);

    private SpellbookService spellService;

    @Autowired
    public SpellbookController(SpellbookService sbs) {
        this.spellService = sbs;
    }

    @GetMapping(path = "/read/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spell readSpell(@PathVariable String name) {
        log.info("Operating on path /read/" + name);
        try {
            return spellService.getSpell(name);
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
            return spellService.getSpellList();
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
            return spellService.insertSpell(spell);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
