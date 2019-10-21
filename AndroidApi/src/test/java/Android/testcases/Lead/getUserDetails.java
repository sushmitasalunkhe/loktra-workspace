package Android.testcases.Lead;

import org.testng.Assert;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import PageObjects.Lead.LeadPage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getUserDetails {
	@Test
	public String getUserDetails() {

		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		Response response=httprequest.request(Method.GET,"/app/profile");
		/*String responseBody=response.getBody().asString();
		
		System.out.println("responseBody is: "+responseBody);
		//get statuscode
		
		int statusCode=response.getStatusCode();
		System.out.println("Status code is: "+statusCode);
		Assert.assertEquals(statusCode,200);*/
		String Myproduct=response.jsonPath().getString("data.product_info.display_product_name");
		return Myproduct;
		}

}
