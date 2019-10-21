package Android.testcases.Lead;



import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import PageObjects.Lead.LeadPage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateLead {
	
	public String[] getnewlead_s(int i) {
		
		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		httprequest.queryParam("type", "assign").
				queryParam("tab_name", "fresh_leads").
				queryParam("sub_tab", "all").
				queryParam("page_number", "1").queryParam("page_size", "20");
		
		Response response=httprequest.request(Method.GET,"/app/lead/details-v3");
	/*	String responseBody=response.getBody().asString();
		System.out.println("responseBody of new lead is: "+responseBody);
		//get statuscode
		int statusCode=response.getStatusCode();
		System.out.println("Status code is: "+statusCode);*/
		String act[]=new String[4];
		
		act[0]=response.jsonPath().getString("data.lead_details["+i+"].customer_data.name");
		act[1]=response.jsonPath().getString("data.lead_details["+i+"].customer_data.contact");
		act[2]=response.jsonPath().getString("data.lead_details["+i+"].status_name");
		act[3]=response.jsonPath().getString("data.lead_details["+i+"].lead_id");
		System.out.println("act_name: "+act[0]);
		System.out.println("act_number: "+act[1]);
		//System.out.println("act_status : "+act[2]);
		return act;
	}
	
	@Test
	public void updateLead() throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println("testcase running is : "+UpdateLead.class.getSimpleName());
		long now = Instant.now().toEpochMilli();
		long follow_up_time=now+200000000;
	//System.out.println(follow_up_time);
		Object obj=   new JSONParser().parse(new FileReader("updateLead.json")); 
		ArrayList list =  (ArrayList) obj;
	 
		for (int i=0;i<list.size();i++) {
//	 					
			HashMap<String,Object>m=new HashMap<String,Object>();
			m.putAll((Map<? extends String, ? extends Object>) list.get(i));
	//		HashMap<String,String>m2=new HashMap<String,String>();
	//		m2.putAll((Map<? extends String, ? extends String>) list.get(1));
			String status_id=(String) m.get("status_id");
			String reject_id=(String) m.get("reject_id");
			String status=(String) m.get("status");
			System.out.println();
			System.out.println();
			System.out.println("-----------------------------------------------------------------");
			UpdateLead ul=new UpdateLead();
			String act[]=ul.getnewlead_s(i);
			String lead_id=act[3];
			System.out.println("lead id is: "+act[3]);	
			LeadPage sl=new LeadPage();
			RequestSpecification httprequest= sl.getHttprequest();
		
	      	//Map<String, Object> requestparams = new HashMap<>();
			 JSONObject requestparams=new JSONObject();
			JSONObject form_data = new JSONObject();
			form_data.put("schedule_start_date_time",follow_up_time);
			form_data.put("follow_up_type", "meeting");
			form_data.put("fos_reason_id", reject_id);
			requestparams.put("form_data", form_data);
			requestparams.put("action_time",0);
			requestparams.put("comment_time",0);
			requestparams.put("form_schema_id",""); 
			requestparams.put("follow_time",0);
			requestparams.put("lead_id",lead_id);
			requestparams.put("status_id",status_id);
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
			String act_status=u2.getUpdatednewlead(lead_id);
			String exp_status=status;
			//System.out.println("act_status : "+act_status);
			AssertJUnit.assertEquals(act_status, exp_status);
			
			}
			}
	public String getUpdatednewlead(String lead_id) {
		
		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		httprequest.queryParam("type", "assign").
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
