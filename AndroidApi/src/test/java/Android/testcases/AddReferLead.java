package Android.testcases;

import org.testng.annotations.Test;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddReferLead {
	public  String getleadForm(String id,String token){
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
	
		String product=new String();
		//product[0]=response.jsonPath().getString("data.display_products_info[10].display_product_id");
		//System.out.println("product_id is: "+product[0]);
		//product[1]=response.jsonPath().getString("data.display_products_info[10].display_product_name");
		//System.out.println("product_name is: "+product[1]);
		
		getUserDetails ud=new getUserDetails();
	String user_product=ud.getUserDetails();
		
	for(int i=0;i<24;i++){
		
	    String vertical_product_name=response.jsonPath().getString("data.display_products_info[i].vertical_product_name");
	    if (user_product!=vertical_product_name){
	      product=response.jsonPath().getString("data.display_products_info[i].display_product_id"); 
	     
	   break;
	    }
	    System.out.println(product);
	    
	}    
return product;
	}
	@Test
	public void AddreferLead() {
		 BaseTest bt=new BaseTest();
			String requestBody[]=bt.setUp();
			String token =requestBody[0];
			String id=requestBody[1];
	String product=getleadForm(id,token);
	System.out.println(product);
	}

}
