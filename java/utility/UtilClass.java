package utility;

import java.io.IOException;
//import java.net.http.HttpResponse;
//import java.text.Normalizer;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

public class UtilClass {

	public static List<String> flatMyList(List<List<String>> list) {

		return list.stream().flatMap(List::stream).collect(Collectors.toList());
	}

	public static String randomTxt() {

		String txt = "qwertyuioplkjhgfdsazxcvbnm";
		List<String> charList = Arrays.asList(txt.split(""));
		Collections.shuffle(charList);
		return charList.stream().limit(4).collect(Collectors.joining(""));
	}

	public static List<String> clearBrackets(List<String> list) {

		return list.stream().map(el -> replace(el)).collect(Collectors.toList());

	}

	public static String replace(String str) {

		return str.replaceAll("[^a-zA-Z0-9 ]", "");
	}

	public static String encode(String txt) {

		String encodedCredentials = Base64.getEncoder().encodeToString((txt).getBytes());
		return encodedCredentials;
	}

}
