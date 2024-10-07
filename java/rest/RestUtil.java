package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class RestUtil<T> {

	public static <T> T parse(Response response, String property) {

		return response.jsonPath().get(property);
	}

	public static String toJson(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

//
////method to hit url--> and get redirected url content	
//public static String getRedirectedContent(String urlString, String sessionCookie) throws Exception {
//    
//URL url = new URL(urlString);
//HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//connection.setInstanceFollowRedirects(true); // Follow redirects
//connection.setRequestMethod("GET");
//
//  if (sessionCookie != null) {
//     connection.setRequestProperty("Cookie", sessionCookie);
//}
//int responseCode = connection.getResponseCode();
//System.out.println("Content Response Code: " + responseCode);
//
//StringBuilder content = new StringBuilder();
//  try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//     String inputLine;
//     while ((inputLine = in.readLine()) != null) {
//       content.append(inputLine);
//}
//}
//return content.toString();
//}
//
//
//public static String login(String loginUrl, String username, String password) throws Exception {
//    
//URL url = new URL(loginUrl);
//HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//connection.setRequestMethod("POST");
//connection.setDoOutput(true);
//connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//String postData = "username=" + username + "&password=" + password;
//
//try (OutputStream os = connection.getOutputStream()) {
//    os.write(postData.getBytes());
//    os.flush();
//}
//int responseCode = connection.getResponseCode();
//System.out.println("Login Response Code: " + responseCode);
//
//String cookie = connection.getHeaderField("Set-Cookie");
//String content = connection.getContent().toString();
//connection.disconnect();
//return content; 
//}

}
