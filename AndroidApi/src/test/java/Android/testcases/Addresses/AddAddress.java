package Android.testcases.Addresses;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.json.simple.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import BasePage.PageObject;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddAddress extends PageObject {
@Test
public void addAddress() {
	BaseTest bt=new BaseTest();
	String requestBody[]=bt.setUp();
	String token =requestBody[0];
	String id=requestBody[1];
	RequestSpecification httprequest=RestAssured.given().
			header("id",id).
			header("token",token).
			header("source","android_app").
			header("Host","loktra.loktra.com").
			header("Referer","https://loktra.loktra.com/source-manager-leads");
	JSONObject location=new JSONObject();
	JSONObject address = new JSONObject();
		address.put("city", "bangalore");
		address.put("location", location);
		address.put("country", "India");
		address.put("full_address", "32/1, HAL 2nd Stage, Kodihalli, Bengaluru, Karnataka 560008, India");
		address.put("house_details", "hdkdndsnn");
		address.put("member_id", id);
		address.put("pincode", "560008");
		address.put("state","Karnataka");
		address.put("street_name","vdjdjdnbdbd");
		location.put("latitude",12.965974845495557); 
		location.put("longitude",77.6421346887946);
		httprequest.body(address.toJSONString()).contentType("application/json");
	//httprequest.body(requestparams.toJSONString()).contentType("application/json");
	Response response=httprequest.request(Method.POST,"/app/home-address");
	String responseBody=response.getBody().asString();
	System.out.println("responseBody of add address: "+responseBody);
	//get statuscode
	int statusCode=response.getStatusCode();
	System.out.println("Status code is: "+statusCode);
	AssertJUnit.assertEquals(statusCode,201);
	String Address_id=response.getBody().jsonPath().getString("id");
	PageObject po=new PageObject();
	po.setAddressid(Address_id);
	String ad_id=po.getAddressid();
	System.out.println(ad_id);

	
	
}
}
