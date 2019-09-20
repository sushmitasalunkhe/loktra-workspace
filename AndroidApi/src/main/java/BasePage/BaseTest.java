package BasePage;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest extends BasePage {

	public String[] setUp() {
	RequestSpecification httprequest=RestAssured.
			given().contentType("multipart/form-data").multiPart("login_id","system@loktra.com").
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
	String ar[]=new String[2];
	ar[0]=response.jsonPath().get("data.token");
	System.out.println(ar[0]+" is token");
	//get id
	ar[1]=response.jsonPath().get("data.id");
	System.out.println(ar[1]+" is id");
	return ar;
	}
	
	}


