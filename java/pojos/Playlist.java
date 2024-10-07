package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder("{name,description,public}")
public class Playlist {

	String name;
	String description;
	@JsonProperty("public")
	Boolean publi;

	public Playlist() {
		super();
	}

	public Playlist(String name, String description, Boolean publi) {
		super();
		this.name = name;
		this.description = description;
		this.publi = publi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getPubli() {
		return publi;
	}

	public void setPubli(Boolean publi) {
		this.publi = publi;
	}

}
