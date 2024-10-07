package rest;

public enum EContentType {
	
	URLENC("application/x-www-form-urlencoded"),
	CHARSET_STREAM("application/octet-stream"),
	JSON("application/json"), 
	Text("text/plain"), 
	FORM_DATA("multipart/form-data");

	String contentType;

	EContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {

		return contentType;
	}

}