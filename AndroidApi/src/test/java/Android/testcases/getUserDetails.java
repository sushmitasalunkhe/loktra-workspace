package Android.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getUserDetails {
	@Test
	public String getUserDetails() {
		BaseTest bt=new BaseTest();
		String requestBody[]=bt.setUp();
		String token =requestBody[0];
		String id=requestBody[1];
		RequestSpecification httprequest=RestAssured.given().
				header("id",id).
				header("token",token).
				header("source","android_app");
		Response response=httprequest.request(Method.GET,"/app/profile");
		String responseBody=response.getBody().asString();
		String Myproduct=response.jsonPath().getString("data.product_info.display_product_name");
		System.out.println("responseBody is: "+responseBody);
		//get statuscode
		int statusCode=response.getStatusCode();
		System.out.println("Status code is: "+statusCode);
		Assert.assertEquals(statusCode,200);
		return Myproduct;
		}

}
