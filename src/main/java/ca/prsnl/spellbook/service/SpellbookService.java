package ca.prsnl.spellbook.service;

import ca.prsnl.spellbook.repository.dao.SpellDao;
import ca.prsnl.spellbook.repository.dto.Spell;
import ca.prsnl.spellbook.util.FileToString;
import ca.prsnl.spellbook.util.JSONFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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

    /**
     * Searches the database for a spell with a primary key equal to the parameter name
     *
     * @param name - primary key for the spell
     * @return - a spell DTO
     */
    public Spell getSpell(String name) {
        name = name.toUpperCase();
        Spell spell = spellDao.findById(name);
        if (spell == null) {
            throw new ResourceNotFoundException(name + " does not exist");
        }
        return spell;
    }

    /**
     * Inserts a spell into the database.
     * If the primary key is already present within the database, it's data
     * will be updated with the new fields
     * If the primary key is not present within the database then a new record
     * will be created.
     *
     * @param spell - a spell DTO holding the data to insert into the database
     * @return - a spell DTO representing the new or updated row
     */
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

    public List<String> getSpellList() {
        return spellDao.getAllKeys();
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
