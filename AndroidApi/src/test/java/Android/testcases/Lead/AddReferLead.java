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

import Android.testcases.Products.getleadproduct;
import BasePage.BaseTest;
import PageObjects.Lead.LeadPage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddReferLead extends BaseTest {
	
	@Test
	public void AddreferLead() throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println("testcase running is : "+AddReferLead.class.getSimpleName());
		Object obj=   new JSONParser().parse(new FileReader("products.json")); 
		List<Object> list_of_self=(List<Object>) obj;
		 //System.out.println(list_of_self);
		ArrayList list =  (ArrayList) list_of_self.get(1);
//		System.out.println(list);
//		System.out.println(list.size());
		for (int j=0;j<list.size();j++) {
				 // ArrayList<Object> list=new ArrayList<Object>();
				HashMap<String,Object>m=new HashMap<String,Object>();
				m.putAll((Map<? extends String, ? extends Object>) list.get(j));
				String product_id=(String) m.get("product_id");
				String product_name=(String) m.get("product_name");
				System.out.println();
				System.out.println();
				System.out.println("-----------------------------------------------------------------");
				System.out.println("product name is: "+product_name);
				
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
				
				LeadPage sl=new LeadPage();
				RequestSpecification httprequest= sl.getHttprequest();
				httprequest.body(requestparams.toJSONString()).contentType("application/json");
				Response response=httprequest.request(Method.POST,"/app/lead");
				String responseBody=response.getBody().asString();
				System.out.println("responseBody of refer lead is: "+responseBody);
				//get statuscode
				int statusCode=response.getStatusCode();
				System.out.println("Status code is: "+statusCode);
				AssertJUnit.assertEquals(statusCode,200);
				AddReferLead r2=new AddReferLead();
				String act[]=r2.getnewlead_r();
				
				String exp_name=name;
				String exp_number=number;
				String exp_status="Call Pending";
				String act_name=act[0];
				String act_number=act[1];
				String act_status=act[2];
				System.out.println("lead id is: "+act[3]);
				AssertJUnit.assertEquals(act_name,exp_name);
				AssertJUnit.assertEquals(act_number,exp_number);
				AssertJUnit.assertEquals(act_status, exp_status);
			}
	   	}
	public String[] getnewlead_r() {
		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		httprequest.queryParam("type", "refer").
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
