package ca.prsnl.spellbook.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "url"
})
public class School {

    @JsonProperty("name")
    public String name;
    @JsonProperty("url")
    public String url;

    public String getName() {
        return name;
    }
}
