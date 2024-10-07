package rest;

import java.util.Map;

//import org.json.JSONException;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Methods {

	public static void setBaseUrl(String URL) {

		if (!URL.isEmpty()) {
			RestAssured.baseURI = URL;
		}
	}

	public static RequestSpecification authorization(IAuth Icontext) {

		RequestSpecification reqSpec;
		if (Icontext == null) {
			reqSpec = RestAssured.given();
		} else {
			reqSpec = Icontext.auth();
		}
		return reqSpec;
	}

	public static RequestSpecification init(Context context) {

		setBaseUrl(context.baseURL);
		RequestSpecification reqSpec = authorization(context.auth);
		
	if(context.contentTypeCharset!=null) {
		if(context.contentTypeCharset==false) { 
		EncoderConfig EC= new EncoderConfig();
		reqSpec.config(RestAssured.config().encoderConfig(EC.
		appendDefaultContentCharsetToContentTypeIfUndefined(false)));
		};		
};
				
		
		if (!context.pathParams.isEmpty()) {
			reqSpec.pathParams(context.pathParams);
		}
		if (!context.formParams.isEmpty()) {
			reqSpec.formParams(context.formParams);
		}
		if (!context.queryParams.isEmpty()) {
			reqSpec.queryParams(context.queryParams);
		}
		if (context.requestContentType != null && !context.requestContentType.equals("")) {
			reqSpec.contentType(context.requestContentType.getContentType());
		}
		
		return reqSpec;
	}

	public static Response GET(Context context) {

		Response resp = null;
		RequestSpecification reqSpec = init(context);

		if (!context.requestHeaderParams.isEmpty()) {
			for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
				reqSpec.header(header.getKey(), header.getValue());
			}
		}
		if (!context.headers.isEmpty()) {
			for (Map.Entry<String, Object> header : context.headers.entrySet()) {
				reqSpec.headers(header.getKey(), header.getValue());
			}
		}
		if (!context.params.isEmpty()) {
			for (Map.Entry<String, Object> paramMap : context.params.entrySet()) {
				reqSpec.param(paramMap.getKey(), paramMap.getValue());
			}
		}
		if (context.reqHeader != null && !context.reqHeader.equals("")) {
			reqSpec.header(new Header("Authorization: ",context.reqHeader));
			
		}
		if (context.URI != null && !context.URI.equals("")) {
			resp = reqSpec.get(context.URI);
		}
		
		
		
		return resp;
	}

	public static Response POST(Context context) {

		Response resp = null;
		RequestSpecification reqSpec = init(context);

		if (context.fileBody != null) {
			reqSpec.body(context.fileBody);
		}
		if (context.requestBodyString != null) {
			reqSpec.body(context.requestBodyString);
		}
		if (!context.multiParts.isEmpty()) {
			for (Map.Entry<String, Object> multi : context.multiParts.entrySet()) {
				reqSpec.multiPart(multi.getKey(), multi.getValue());
			}
		}
		if (!context.requestHeaderParams.isEmpty()) {
			for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
				reqSpec.header(header.getKey(), header.getValue());
			}
		}
		if (!context.formParams.isEmpty()) {
			reqSpec.formParams(context.formParams);
		}
		if (!context.params.isEmpty()) {
			for (Map.Entry<String, Object> paramMap : context.params.entrySet()) {
				reqSpec.param(paramMap.getKey(), paramMap.getValue());
			}
		}
		if (!context.headers.isEmpty()) {
			for (Map.Entry<String, Object> header : context.headers.entrySet()) {
				reqSpec.headers(header.getKey(), header.getValue());
			}
		}
		
		if(context.path!=null) {
			reqSpec.body(context.path);
		}

		if (context.URI != null && !context.URI.equals("")) {
			resp = reqSpec.post(context.URI);
		} else {
			resp = reqSpec.post();
		}
		
		return resp;
	}

	public static Response PUT(Context context) {

		Response resp = null;
		RequestSpecification reqSpec = init(context);

//		if (context.requestBody != null) {
//			reqSpec.body(context.requestBody.toString());
//		}
		if (context.requestBodyString != null) {
			reqSpec.body(context.requestBodyString);
		}
		if (!context.multiParts.isEmpty()) {
			for (Map.Entry<String, Object> multi : context.multiParts.entrySet()) {
				reqSpec.multiPart(multi.getKey(), multi.getValue());
			}
		}
		if (!context.requestHeaderParams.isEmpty()) {
			for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
				reqSpec.header(header.getKey(), header.getValue());
			}
		}
		if (!context.formParams.isEmpty()) {
			reqSpec.formParams(context.formParams);
		}
		if (!context.params.isEmpty()) {
			for (Map.Entry<String, Object> paramMap : context.params.entrySet()) {
				reqSpec.param(paramMap.getKey(), paramMap.getValue());
			}
		}

		if (context.URI != null && !context.URI.equals("")) {
			resp = reqSpec.post(context.URI);
		} else {
			resp = reqSpec.put();
		}
		return resp;
	}

	public static Response DELETE(Context context) {

		Response resp = null;
		RequestSpecification reqSpec = init(context);

//		if (context.requestBody != null) {
//			reqSpec.body(context.requestBody.toString());
//		}
		if (context.requestBodyString != null) {
			reqSpec.body(context.requestBodyString);
		}
		if (!context.multiParts.isEmpty()) {
			for (Map.Entry<String, Object> multi : context.multiParts.entrySet()) {
				reqSpec.multiPart(multi.getKey(), multi.getValue());
			}
		}
		if (!context.requestHeaderParams.isEmpty()) {
			for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
				reqSpec.header(header.getKey(), header.getValue());
			}
		}
		if (!context.formParams.isEmpty()) {
			reqSpec.formParams(context.formParams);
		}
		if (!context.params.isEmpty()) {
			for (Map.Entry<String, Object> paramMap : context.params.entrySet()) {
				reqSpec.param(paramMap.getKey(), paramMap.getValue());
			}
		}

		if (context.URI != null && !context.URI.equals("")) {
			resp = reqSpec.post(context.URI);
		} else {
			resp = reqSpec.delete();
		}
		return resp;
	}

}