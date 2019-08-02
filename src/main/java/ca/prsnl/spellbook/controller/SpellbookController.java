package ca.prsnl.spellbook.controller;

import ca.prsnl.spellbook.repository.dto.Spell;
import ca.prsnl.spellbook.service.SpellbookService;
import ca.prsnl.spellbook.util.ResponseExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class SpellbookController {

    private SpellbookService service;

    @Autowired
    public SpellbookController(SpellbookService sbs) {
        this.service = sbs;
    }

    @GetMapping(path = "/read/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spell readSpell(@PathVariable String name) {
        try {
            return service.getSpell(name);
        } catch (Exception e) {
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
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
