package rest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Context {
	
	public Boolean contentTypeCharset;
	public File fileBody;
	public String path;
	public IAuth auth;
	public String apikey; 
	public String apiSecret;
	public String token;
	public String tokenSecret;	
	public String baseURL;
	public EContentType requestContentType;
	public String URI; 
	public String requestBodyString;
	public String reqHeader;
	public Map<String,Object>queryParams=new HashMap<>();
	public Map<String,Object>pathParams=new HashMap<>();
	public Map<String,Object>requestHeaderParams=new HashMap<>();
	public Map<String,Object>formParams=new HashMap<>();
	public Map<String,Object>headers=new HashMap<>();
	public Map<String,Object>formParam=new HashMap<>();
	public Map<String,Object>multiParts=new HashMap<>();	
	public Map<String,Object>params=new HashMap<>();

}