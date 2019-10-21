package Android.testcases.Lead;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import BasePage.BaseTest;
import PageObjects.Lead.LeadPage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddSelfLead extends BaseTest{
	
	
	
	@Test			
	public  void addSelfLead() throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println("testcase running is : "+AddSelfLead.class.getSimpleName());
		Object obj=   new JSONParser().parse(new FileReader("products.json")); 
		List<Object> list_of_self=(List<Object>) obj;
		ArrayList list =  (ArrayList) list_of_self.get(0);
		for (int j=0;j<list.size();j++) {
			HashMap<String,Object>m=new HashMap<String,Object>();
			m.putAll((Map<? extends String, ? extends Object>) list.get(j));
			String product_id=(String) m.get("product_id");
			System.out.println();
			System.out.println();
			System.out.println("-----------------------------------------------------------------");
			System.out.println(m.get("product_name"));
			LeadPage sl=new LeadPage();
			RequestSpecification httprequest= sl.getHttprequest();
			String name=name(5)+" "+name(5);
			String number="8"+number(9);
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
				System.out.println("responseBody of add self lead is: "+responseBody);
				//get statuscode
				int statusCode=response.getStatusCode();
				System.out.println("Status code is: "+statusCode);
				AssertJUnit.assertEquals(statusCode,200);
				AddSelfLead s2=new AddSelfLead();
				String act[]=s2.getnewlead_s();
				String exp_name=name;
				String exp_number=number;
				String exp_status="Exec Approved";
				String act_name=act[0];
				String act_number=act[1];
				String act_status=act[2];
				System.out.println("lead id is: "+act[3]);
				AssertJUnit.assertEquals(act_name,exp_name);
				AssertJUnit.assertEquals(act_number,exp_number);
				AssertJUnit.assertEquals(act_status, exp_status);
				}
				
	} 
	



	public String[] getnewlead_s() {
		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		httprequest.queryParam("type", "assign").
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
		System.out.println("act_status : "+act[2]);
		return act;
	}
	
	}
	
	 

	


