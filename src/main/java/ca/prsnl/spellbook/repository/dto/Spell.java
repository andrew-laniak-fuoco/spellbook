package ca.prsnl.spellbook.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "level",
        "school",
        "name",
        "time",
        "range",
        "comp",
        "duration",
        "desc",
        "higher"
})
public class Spell {

    public enum School {ABJURATION, CONJURATION, DIVINATION, ENCHANTMENT, EVOCATION, ILLUSION, NECROMANCY, TRANSMUTATION}

    @JsonProperty("name")
    public String name;
    @JsonProperty("school")
    public School school;
    @JsonProperty("level")
    public Integer slevel;
    @JsonProperty("range")
    public String range;
    @JsonProperty("comp")
    public String component;
    @JsonProperty("duration")
    public String duration;
    @JsonProperty("time")
    public String castTime;
    @JsonProperty("desc")
    public String sdesc;
    @JsonProperty("higher")
    public String higher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setSchool(String str) {
        str = str.toUpperCase();
        switch (str) {
            case "ABJURATION":
                this.school = School.ABJURATION;
                break;
            case "CONJURATION":
                this.school = School.CONJURATION;
                break;
            case "DIVINATION":
                this.school = School.DIVINATION;
                break;
            case "ENCHANTMENT":
                this.school = School.ENCHANTMENT;
                break;
            case "EVOCATION":
                this.school = School.EVOCATION;
                break;
            case "ILLUSION":
                this.school = School.ILLUSION;
                break;
            case "NECROMANCY":
                this.school = School.NECROMANCY;
                break;
            case "TRANSMUTATION":
                this.school = School.TRANSMUTATION;
                break;
        }
    }

    public int getSlevel() {
        return slevel;
    }

    public void setSlevel(int slevel) {
        this.slevel = slevel;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String comp) {
        this.component = comp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCastTime() {
        return castTime;
    }

    public void setCastTime(String castTime) {
        this.castTime = castTime;
    }

    public String getSdesc() {
        return sdesc;
    }

    public void setSdesc(String sdesc) {
        this.sdesc = sdesc;
    }

    public String getHigher() {
        return higher;
    }

    public void setHigher(String higher) {
        this.higher = higher;
    }

    @Override
    public String toString() {
        return "Spell{" + "name='" + name + '\'' + '}';
    }
}
