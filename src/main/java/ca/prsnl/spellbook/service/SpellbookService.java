package ca.prsnl.spellbook.service;

import ca.prsnl.spellbook.repository.dao.SpellDao;
import ca.prsnl.spellbook.repository.dto.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpellbookService {

    private SpellDao spellDao;

    @Autowired
    public SpellbookService(SpellDao dao) {
        this.spellDao = dao;
    }

    public Spell getSpell(String name) {
        name = name.toUpperCase();
        Spell spell = spellDao.findById(name);
        if (spell == null) {
            throw new ResourceNotFoundException(name + " does not exist");
        }
        return spell;
    }

    public Spell insertSpell(Spell spell) {
        String quality = qualityCheck(spell);
        if (quality != null) {
            throw new IllegalArgumentException(quality);
        }
        spell.setName(spell.getName().toUpperCase());
        // if spell exists already, update with new information
        if (spellDao.existsById(spell.getName())) {
            return updateSpell(spell);
        } else {
            return newSpell(spell);
        }
    }

    public List<String> getLoaders() throws IOException {
        String dir = "src/main/resources/static/loader";
        return Files.walk(Paths.get(dir))
                .map(p -> p.toFile())
                .filter(f -> f.isFile())
                .map(file -> file.getName())
                .collect(Collectors.toList());
    }

    public List<String> getSpellList() {
        return spellDao.getAllNames();
    }

    public void initdb() {
        try {
            spellDao.loadDatabaseFromJson();
        } catch (Exception e) {
            throw new RuntimeException("Problem initalizing database");
        }
    }

    private Spell newSpell(Spell spell) {
        return spellDao.create(spell);
    }

    private Spell updateSpell(Spell spell) {
        return spellDao.update(spell);
    }

    private String qualityCheck(Spell spell) {
        if (spell.getName().equals("") || spell.getName() == null) {
            return "empty name";
        }
        if (spell.getSlevel() > 9 || spell.getSlevel() < 0) {
            return "spell level out of range.  Must be between 0 and 9 inclusive";
        }
        if (spell.getRange().equals("") || spell.getRange() == null) {
            return "empty range";
        }
        if (spell.getSdesc().equals("") || spell.getSdesc() == null) {
            return "empty description";
        }
        if (spell.getCastTime().equals("") || spell.getCastTime() == null) {
            return "empty casting time";
        }
        if (spell.getComponent().equals("") || spell.getComponent() == null) {
            return "empty component";
        }
        if (spell.getDuration().equals("") || spell.getDuration() == null) {
            return "empty duration";
        }
        if (spell.getSchool() == null) {
            return "no school specified";
        }
        return null;
    }
}
