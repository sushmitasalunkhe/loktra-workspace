package Android.testcases;

import org.json.simple.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddReferLead extends BaseTest {
	
	public  String[] getleadForm_r(String id,String token,String user_product){
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
	
		String []product=new String[2];
	for(int i=0;i<24;i++){
		
	    String vertical_product_name=response.jsonPath().getString("data.display_products_info["+i+"].vertical_product_name");
	    if (user_product!=vertical_product_name){
	      product[0]=response.jsonPath().getString("data.display_products_info["+i+"].display_product_id"); 
	      product[1]=response.jsonPath().getString("data.display_products_info["+i+"].display_product_name");
	   break;
	    }
	}
	    System.out.println(product[1]);
	    
	    
return product;
	}
	@Test
	public void AddreferLead() {
		getUserDetails ud=new getUserDetails();
		
		 BaseTest bt=new BaseTest();
			String requestBody[]=bt.setUp();
			String token =requestBody[0];
			String id=requestBody[1];
			String user_product=ud.getUserDetails(id,token);
			//System.out.println(id+token);
			AddReferLead r1=new AddReferLead();
			String product[]=r1.getleadForm_r(id, token, user_product);
			RequestSpecification httprequest=RestAssured.given().contentType("application/json").
					header("id",id).
					header("token",token).
					header("source","android_app").
					header("Referer","https://loktra.loktra.com");
	//System.out.println(product);
	String name=name(5)+" "+name(5);
	String number="9"+number(9);
	

  	//Map<String, Object> requestparams = new HashMap<>();
	 JSONObject requestparams=new JSONObject();
	requestparams.put("contact",number);
	requestparams.put("action_time",0);
	requestparams.put("is_self_lead",false); 
	requestparams.put("geocoded_address","");
	requestparams.put("location","");
	requestparams.put("name",name);
	requestparams.put("product_id",product[0]);
	requestparams.put("product_name",product[1]);
		requestparams.put("follow_up",0);
	
	
	httprequest.body(requestparams.toJSONString()).contentType("application/json");
	Response response=httprequest.request(Method.POST,"/app/lead");
	String responseBody=response.getBody().asString();
	System.out.println("responseBody is: "+responseBody);
	//get statuscode
	int statusCode=response.getStatusCode();
	System.out.println("Status code is: "+statusCode);
	AssertJUnit.assertEquals(statusCode,200);
	
	}

}
