package Android.testcases.Lead;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import org.testng.annotations.Test;

import java.time.Instant;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateLead {
	public String[] getnewlead_s(String id,String token) {
		RequestSpecification httprequest=RestAssured.given().
				header("id",id).
				header("token",token).
				header("source","android_app").
				header("Host","loktra.loktra.com").
				header("Referer","https://loktra.loktra.com/source-manager-leads").
				queryParam("type", "assign").
				queryParam("tab_name", "fresh_leads").
				queryParam("sub_tab", "all").
				queryParam("page_number", "1");
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
		//System.out.println("act_status : "+act[2]);
		return act;
	}
	
	@Test
	public void updateLead() {
		

	 BaseTest bt=new BaseTest();
		String requestBody[]=bt.setUp();
		String token =requestBody[0];
		String id=requestBody[1];
	UpdateLead ul=new UpdateLead();
	String act[]=ul.getnewlead_s(id, token);
	String lead_id=act[3];
	System.out.println("lead id is: "+act[3]);
	long now = Instant.now().toEpochMilli();
	long follow_up_time=now+200000000;
	System.out.println(follow_up_time);
	RequestSpecification httprequest=RestAssured.given().contentType("application/json").
			header("id",id).
			header("token",token).
			header("source","android_app");
	      	//Map<String, Object> requestparams = new HashMap<>();
			 JSONObject requestparams=new JSONObject();
			JSONObject form_data = new JSONObject();
			form_data.put("schedule_start_date_time",follow_up_time);
			form_data.put("follow_up_type", "meeting");
			form_data.put("fos_reason_id", "");
			requestparams.put("form_data", form_data);
			requestparams.put("action_time",0);
			requestparams.put("comment_time",0);
			requestparams.put("form_schema_id",""); 
			requestparams.put("follow_time",0);
			requestparams.put("lead_id",lead_id);
			requestparams.put("status_id","e33357fd-23e4-4510-aa94-8f49763c29e8");
			requestparams.put("type","status");
			
			httprequest.body(requestparams.toJSONString()).contentType("application/json");
			Response response=httprequest.request(Method.PUT,"/app/lead");
			String responseBody=response.getBody().asString();
			System.out.println("responseBody of update lead is: "+responseBody);
			//get statuscode
			int statusCode=response.getStatusCode();
			System.out.println("Status code is: "+statusCode);
			AssertJUnit.assertEquals(statusCode,200);
			UpdateLead u2=new UpdateLead();
			String act_status=u2.getUpdatednewlead(id, token,lead_id);
			String exp_status="Exec Follow Up";
			//System.out.println("act_status : "+act_status);
			AssertJUnit.assertEquals(act_status, exp_status);
			}
	public String getUpdatednewlead(String id,String token,String lead_id) {
		
			RequestSpecification httprequest=RestAssured.given().
					header("id",id).
					header("token",token).
					header("source","android_app").
					header("Host","loktra.loktra.com").
					header("Referer","https://loktra.loktra.com/source-manager-leads").
					queryParam("type", "assign").
					queryParam("tab_name", "fresh_leads").
					queryParam("sub_tab", "all").
					queryParam("page_number", "1").
					queryParam("lead_id", lead_id);
			Response response=httprequest.request(Method.GET,"/app/lead/details-v3");
			/*String responseBody=response.getBody().asString();
			System.out.println("responseBody of updated new lead is: "+responseBody);
			//get statuscode
			int statusCode=response.getStatusCode();
			System.out.println("Status code is: "+statusCode);*/
			String act=new String();
			act=response.jsonPath().getString("data.status_name");
			System.out.println("act_status : "+act);
			return act;
	}
}
