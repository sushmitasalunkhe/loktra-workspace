package Android.testcases;

import java.util.HashMap;
import java.util.Map;

import org.apache.groovy.json.internal.JsonStringDecoder;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;

public class AddSelfLead extends BaseTest{
	
	
	public  String[] getleadForm(String id,String token){
	    	RequestSpecification httprequest=RestAssured.given().
					header("id",id).
					header("token",token).
					header("source","android_app").
					header("Host","loktra.loktra.com").
					header("Referer","https://loktra.loktra.com/source-manager-leads");
			Response response=httprequest.request(Method.GET,"/source-manager/lead-form");
			String responseBody=response.getBody().asString();
			System.out.println("responseBody is: "+responseBody);
			//get statuscode
			int statusCode=response.getStatusCode();
			System.out.println("Status code is: "+statusCode);
		
			String product[]=new String[2];
			product[0]=response.jsonPath().getString("data.display_products_info[10].display_product_id");
			System.out.println("product_id is: "+product[0]);
			product[1]=response.jsonPath().getString("data.display_products_info[10].display_product_name");
			System.out.println("product_name is: "+product[1]);
			return product;
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
	@Test			
	public  void addSelfLead() {
		 BaseTest bt=new BaseTest();
			String requestBody[]=bt.setUp();
			String token =requestBody[0];
			String id=requestBody[1];
    	
		AddSelfLead sl=new AddSelfLead();
		RequestSpecification httprequest=RestAssured.given().contentType("application/json").
				header("id",id).
				header("token",token).
				header("source","android_app");
	    		String product[]=sl.getleadForm(id,token);
	    		String product_id=product[0];
	    		String product_name=product[1];
	    		
	    		String name=name(5)+" "+name(5);
	    		String number="9"+number(9);
	    		
		
		      	//Map<String, Object> requestparams = new HashMap<>();
				 JSONObject requestparams=new JSONObject();
				JSONObject product_additional_info_v2 = new JSONObject();
				 product_additional_info_v2.put("lead_priority", "Hot");
				 product_additional_info_v2.put("lead_source", "anglelist");
				 product_additional_info_v2.put("shop_ownership", "Parental");
				 product_additional_info_v2.put("residence_ownership", "Owned");
				 product_additional_info_v2.put("lead_type", "Normal");
				requestparams.put("product_additional_info_v2", product_additional_info_v2);
				requestparams.put("contact",number);
				requestparams.put("client_photo_tags","");
				requestparams.put("is_self_lead",true); 
				requestparams.put("meeting_datetime",0);
				requestparams.put("lead_priority_id","02169b99-025a-46c4-be4e-0d64bb9d55a7");
				requestparams.put("lead_source_id","44d20efb-9826-4566-b32b-b1a4a9b50c67");
				requestparams.put("name",name);
				requestparams.put("product_id",product_id);
				//requestparams.put("product_id","Support Queries");
				requestparams.put("follow_up",0);
	    		
	    		
				httprequest.body(requestparams.toJSONString()).contentType("application/json");
				Response response=httprequest.request(Method.POST,"/app/self-lead-v2");
				String responseBody=response.getBody().asString();
				System.out.println("responseBody is: "+responseBody);
				//get statuscode
				int statusCode=response.getStatusCode();
				System.out.println("Status code is: "+statusCode);
				Assert.assertEquals(statusCode,200);
	}
	 

	

}
