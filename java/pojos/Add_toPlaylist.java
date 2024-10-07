package pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder("{uris,position}")
public class Add_toPlaylist {

	List<String> uris;
	int position;

	public Add_toPlaylist() {
		super();
	}

	public Add_toPlaylist(List<String> uris, int position) {
		super();
		this.uris = uris;
		this.position = position;
	}

	public List<String> getUris() {
		return uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
