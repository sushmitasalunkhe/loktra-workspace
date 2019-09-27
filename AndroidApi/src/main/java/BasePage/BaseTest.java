package BasePage;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest extends BasePage {

	public String [] setUp() {
	RequestSpecification httprequest=RestAssured.
			given().contentType("multipart/form-data").multiPart("login_id","sushmita@loktra.com").
			multiPart("password","wVKuJyx97M4pUS1imjcxQQ==").
			multiPart("source","android_app").
	header("Host","loktra.loktra.com");
	
	//responseobject
	Response response=httprequest.request(Method.POST,"/auth/login");
	//String responseBody=response.getBody().asString();
	//System.out.println("responseBody is: "+responseBody);
	//get statuscode
	int statusCode=response.getStatusCode();
	//System.out.println("Status code is: "+statusCode);
	
	//get token
	String auth[]=new String[2];
	auth[0]=response.jsonPath().get("data.token");
	//System.out.println(auth[0]+" is token");
	//get id
	auth[1]=response.jsonPath().get("data.id");
	//System.out.println(auth[1]+" is id");

		
	return auth;
	
	}
	
	private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static String name(int count) {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0) {
	int character = (int)(Math.random()*ALPHA_STRING.length());

	builder.append(ALPHA_STRING.charAt(character));
	}
	return builder.toString();
	}
	private static final String NUM_STRING = "0123456789";
	public static String number(int count) {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0) {
	int character = (int)(Math.random()*NUM_STRING.length());
	builder.append(NUM_STRING.charAt(character));
	}
	return builder.toString();
	}	
	}


