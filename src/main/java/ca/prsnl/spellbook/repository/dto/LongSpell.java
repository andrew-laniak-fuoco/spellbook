package ca.prsnl.spellbook.repository.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "index",
        "name",
        "desc",
        "higher_level",
        "page",
        "range",
        "components",
        "material",
        "ritual",
        "duration",
        "concentration",
        "casting_time",
        "level",
        "school",
        "classes",
        "subclasses",
        "url"
})
public class LongSpell {

    @JsonProperty("index")
    public Long index;
    @JsonProperty("name")
    public String name;
    @JsonProperty("desc")
    public List<String> desc = null;
    @JsonProperty("higher_level")
    public List<String> higherLevel = null;
    @JsonProperty("page")
    public String page;
    @JsonProperty("range")
    public String range;
    @JsonProperty("components")
    public List<String> components = null;
    @JsonProperty("material")
    public String material;
    @JsonProperty("ritual")
    public Boolean ritual;
    @JsonProperty("duration")
    public String duration;
    @JsonProperty("concentration")
    public Boolean concentration;
    @JsonProperty("casting_time")
    public String castingTime;
    @JsonProperty("level")
    public Long level;
    @JsonProperty("school")
    public School school;
    @JsonProperty("classes")
    public List<CharClass> classes = null;
    @JsonProperty("subclasses")
    public List<Subclass> subclasses = null;
    @JsonProperty("url")
    public String url;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public List<String> getHigherLevel() {
        return higherLevel;
    }

    public void setHigherLevel(List<String> higherLevel) {
        this.higherLevel = higherLevel;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Boolean getRitual() {
        return ritual;
    }

    public void setRitual(Boolean ritual) {
        this.ritual = ritual;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getConcentration() {
        return concentration;
    }

    public void setConcentration(Boolean concentration) {
        this.concentration = concentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<CharClass> getClasses() {
        return classes;
    }

    public void setClasses(List<CharClass> classes) {
        this.classes = classes;
    }

    public List<Subclass> getSubclasses() {
        return subclasses;
    }

    public void setSubclasses(List<Subclass> subclasses) {
        this.subclasses = subclasses;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
