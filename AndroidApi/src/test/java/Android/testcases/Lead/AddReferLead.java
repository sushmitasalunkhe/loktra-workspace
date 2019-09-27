package Android.testcases.Lead;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddReferLead extends BaseTest {
	
	@Test
	public void AddreferLead() throws JsonParseException, JsonMappingException, IOException {
		getUserDetails ud=new getUserDetails();
		
		 BaseTest bt=new BaseTest();
			String requestBody[]=bt.setUp();
			String token =requestBody[0];
			String id=requestBody[1];
			ObjectMapper mapper = new ObjectMapper();
			 HashMap<String, Object> result = mapper.readValue(new File(
	                  "products.json"), new TypeReference<Map<String, Object>>() {
	          });
		//	 System.out.println(result);
			//JSONObject jsonobject=(JSONObject)obj;
			Map<String,Object> product=(HashMap<String, Object>) result.get("Product_r");
			//System.out.println(login);
			Set setofkeys=product.keySet();
			//System.out.println(setofkeys);
						Iterator itr=setofkeys.iterator();
					while(itr.hasNext()) {
						System.out.println();
						System.out.println();
						System.out.println("-----------------------------------------------------------------");
							String key=(String) itr.next();	
							String product_id= (String) product.get(key);
							key=(String) itr.next();
							String product_name=(String) product.get(key);
							
							System.out.println("product name is: "+product_name);
			
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
	requestparams.put("product_id",product_id);
	requestparams.put("product_name",product_name);
	requestparams.put("follow_up",0);
	
	
	httprequest.body(requestparams.toJSONString()).contentType("application/json");
	Response response=httprequest.request(Method.POST,"/app/lead");
	String responseBody=response.getBody().asString();
	System.out.println("responseBody of refer lead is: "+responseBody);
	//get statuscode
	int statusCode=response.getStatusCode();
	System.out.println("Status code is: "+statusCode);
	AssertJUnit.assertEquals(statusCode,200);
	AddReferLead r2=new AddReferLead();
	String act[]=r2.getnewlead_r(id,token);
	
	String exp_name=name;
	String exp_number=number;
	String exp_status="Call Pending";
	String act_name=act[0];
	String act_number=act[1];
	String act_status=act[2];
	System.out.println("lead id is: "+act[3]);
	AssertJUnit.assertEquals(act_name,exp_name);
	AssertJUnit.assertEquals(act_number,exp_number);
	AssertJUnit.assertEquals(act_status, exp_status);}
	}
	public String[] getnewlead_r(String id,String token) {
		RequestSpecification httprequest=RestAssured.given().
				header("id",id).
				header("token",token).
				header("source","android_app").
				header("Host","loktra.loktra.com").
				header("Referer","https://loktra.loktra.com/source-manager-leads").
				queryParam("type", "refer").
				queryParam("gmt_difference","GMT+05:30");
		Response response=httprequest.request(Method.GET,"/app/lead/details-v3");
	/*	String responseBody=response.getBody().asString();
		System.out.println("responseBody of new lead is: "+responseBody);
		//get statuscode
		int statusCode=response.getStatusCode();
		System.out.println("Status code is: "+statusCode);*/
		String act[]=new String[4];
		act[0]=response.jsonPath().getString("data.lead_details[0].customer_data.name");
		act[1]=response.jsonPath().getString("data.lead_details[0].customer_data.contact");
		act[2]=response.jsonPath().getString("data.lead_details[0].status_name");
		act[3]=response.jsonPath().getString("data.lead_details[0].lead_id");
		System.out.println("act_name: "+act[0]);
		System.out.println("act_number: "+act[1]);
		System.out.println("act_status : "+act[2]);
		return act;
		
		
	}

}
