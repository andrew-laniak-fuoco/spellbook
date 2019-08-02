package ca.prsnl.spellbook.service;

import ca.prsnl.spellbook.repository.dao.SpellDao;
import ca.prsnl.spellbook.repository.dto.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellbookService {

    private SpellDao spellDao;

    @Autowired
    public SpellbookService(SpellDao dao) {
        this.spellDao = dao;
    }

    public Spell getSpell(String name) {
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
        // if spell exists already, update with new information
        if (spellDao.existsById(spell.getName())) {
            return updateSpell(spell);
        } else {
            return newSpell(spell);
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