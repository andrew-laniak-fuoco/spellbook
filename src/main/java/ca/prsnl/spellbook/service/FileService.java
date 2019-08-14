package ca.prsnl.spellbook.service;

import ca.prsnl.spellbook.repository.dao.SpellDao;
import ca.prsnl.spellbook.repository.dto.LongSpell;
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
public class FileService {

    private SpellDao spellDao;

    @Autowired
    public FileService(SpellDao dao) {
        this.spellDao = dao;
    }

    /**
     * Scans the resources package for all files in /static/loader and returns
     * a list of strings representing each file name
     *
     * @return - List of Strings which are the file names in /resources/static/loader
     * @throws IOException
     */
    public List<String> getLoaders() throws IOException {
        String dir = "src/main/resources/static/loader";
        return Files.walk(Paths.get(dir))
                .map(p -> p.toFile())
                .filter(f -> f.isFile())
                .map(file -> file.getName())
                .collect(Collectors.toList());
    }

    /**
     * Inserts a set of new records defined in the file /resources/spellList.json
     * The file is assumed to be a JSON text file which can be used to build a list
     * of spell DTOs.
     * Each DTO in the list will then be inserted into the database.
     */
    public void loadDatabaseFromFile(String file) {
        try {
            InputStream input = getClass().getResourceAsStream(file);
            String json = FileToString.readFile(input);
            LongSpell[] spells = JSONFilter.toObjectFromJson(json, LongSpell[].class);
            for (LongSpell s : spells) {
                Spell newSpell = longSpelltoSpell(s);
                if (!spellDao.existsById(newSpell.getName())) {
                    spellDao.create(newSpell);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem initalizing database");
        }
    }

    private Spell longSpelltoSpell(LongSpell longSpell) {
        Spell spell = new Spell();

        spell.setSlevel(longSpell.getLevel().intValue());
        spell.setSchool(longSpell.getSchool().getName());
        spell.setName(longSpell.getName().toUpperCase());
        if (longSpell.getRitual()) {
            spell.setCastTime(longSpell.getCastingTime() + "/Ritual");
        } else {
            spell.setCastTime(longSpell.getCastingTime());
        }
        spell.setRange(longSpell.getRange());
        spell.setComponent(longSpell.getComponents() + " " + longSpell.getMaterial());
        if (longSpell.getConcentration()) {
            spell.setDuration("Concentration, " + longSpell.getDuration());
        } else {
            spell.setDuration(longSpell.getDuration());
        }
        String description = "";
        for (String s : longSpell.getDesc()) {
            description += s;
            description += "\n";
        }
        spell.setSdesc(description);

        String higher = null;
        if (longSpell.getHigherLevel() != null) {
            higher = "";
            for (String s : longSpell.getHigherLevel()) {
                higher += s;
                higher += "\n";
            }
        }
        spell.setHigher(higher);

        return spell;
    }
}
