package ca.prsnl.spellbook.repository.dao;

import ca.prsnl.spellbook.AppConfig;
import ca.prsnl.spellbook.repository.dto.Spell;
import ca.prsnl.spellbook.util.JSONFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpellDao {

    private Logger log = LoggerFactory.getLogger(SpellDao.class);

    private static final String UPDATE = "UPDATE spell SET school=?,slevel=?,range=?," +
            "cast_time=?,comp=?,duration=?,sdesc=?,higher=? WHERE name=?";
    private static final String DELETE = "DELETE FROM spell WHERE name=?";


    DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public SpellDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(getTableName());
    }

    /**
     * Searches the Position table for all positions with an account_id matching the parameter id
     *
     * @param id - id of the account
     * @return - A list of Position DTOs
     */
    public Spell findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        Spell spell = null;
        try {
            String statement = "select * from " + getTableName() + " where " + getPrimaryKey() + " = ?";
            log.info("SQL select: " + statement);
            spell = (Spell) jdbcTemplate.queryForObject(statement, BeanPropertyRowMapper.newInstance(getDtoClass()), id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return spell;
    }

    /**
     * Checks if a given id value is present in the database
     *
     * @param id - quote ticker value
     * @return - true if the value is found in the database, false otherwise
     */
    public boolean existsById(String id) {
        return (findById(id) != null);
    }

    /**
     * Adds a new row to the table
     *
     * @param entity - a DTO
     * @return - a DTO which represents the inserted row
     */
    public Spell create(Spell entity) {
        if (entity == null) {
            throw new IllegalArgumentException("DTO can not be empty");
        }
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        simpleInsert.execute(parameterSource);
        return entity;
    }

    /**
     * Updates a row in the table
     *
     * @param entity - a DTO
     * @return - a DTO which represents the updated row after changes
     */
    public Spell update(Spell entity) {
        try {
            jdbcTemplate.update(UPDATE, dtoToArray(entity));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * Removes a row in the table that has the given id
     *
     * @param id - quote ticker value
     * @return - a DTO which represents the row which was just removed
     */
    public Spell deleteById(String id) {
        Spell spell = findById(id);
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return spell;
    }

    public List<String> getAllNames() {
        List<String> nameList = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT name FROM spell")) {
            ResultSet rset = statement.executeQuery();
            while (rset.next()) {
                nameList.add(rset.getString("name"));
            }
            return nameList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameList;
    }

    private String getTableName() {
        return "spell";
    }

    private String getPrimaryKey() {
        return "name";
    }

    private Class getDtoClass() {
        return Spell.class;
    }

    private Object[] dtoToArray(Spell entity) {
        Object[] array = new Object[9];
        array[0] = entity.getSchool();
        array[1] = entity.getSlevel();
        array[2] = entity.getRange();
        array[3] = entity.getCastTime();
        array[4] = entity.getComponent();
        array[5] = entity.getDuration();
        array[6] = entity.getSdesc();
        array[7] = entity.getHigher();
        array[8] = entity.getName();
        return array;
    }

    public static void loadDatabaseFromJson() throws SQLException, IOException {
        DataSource ds = AppConfig.createDataSource();
        SpellDao dao = new SpellDao(ds);
        String json = readFile("src/main/resources/spellList.json");
        Spell[] spells = JSONFilter.toObjectFromJson(json, Spell[].class);
        for (Spell s : spells) {
            s.setName(s.getName().toUpperCase());
            dao.create(s);
        }
    }

    private static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
