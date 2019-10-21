package Android.testcases.Login;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Android.testcases.Lead.getUserDetails;
import BasePage.BasePage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class LoginTest extends BasePage{
	
	
	@Test
	public void login() throws FileNotFoundException, IOException, ParseException  {
	
		System.out.println("testcase running is : "+LoginTest.class.getSimpleName());

		Object obj=   new JSONParser().parse(new FileReader("login.json")); 
		 ArrayList list =  (ArrayList) obj;
		 for (int i=0;i<list.size();i++) {
//		 ArrayList<Object> list=new ArrayList<Object>();
			HashMap<String,Object>m=new HashMap<String,Object>();
			m.putAll((Map<? extends String, ? extends Object>) list.get(i));
//			HashMap<String,String>m2=new HashMap<String,String>();
//			m2.putAll((Map<? extends String, ? extends String>) list.get(1));
			String login_id=(String) m.get("id");
			String password=(String) m.get("password");
			int exp_status=Integer.parseInt((String) m.get("status"));
			System.out.println();
		 	System.out.println();
		 	System.out.println("-----------------------------------------------------------------");
		 	RequestSpecification httprequest=RestAssured.given().
		 	contentType("multipart/form-data").
		 	multiPart("login_id",login_id).
		 	multiPart("password",password).
		 	multiPart("source","android_app").
		    header("Host","loktra.loktra.com");
						
		 		//responseobjectx
		 	Response response=httprequest.request(Method.POST,"/auth/login");
		 	String responseBody=response.getBody().asString();
		 	System.out.println(login_id);
		 	System.out.println("responseBody for login is: "+responseBody);
		 	int act_statusCode=response.getStatusCode();
		 	System.out.println("Status code for login is: "+act_statusCode);
		 	AssertJUnit.assertEquals(act_statusCode, exp_status);
		 	String token =response.jsonPath().get("data.token");
		 	String id =response.jsonPath().get("data.id");
		 	LoginTest lt=new LoginTest();
		 	if(act_statusCode==200) 
		 	{
		 		String act_email=lt.getUserDetails(id,token);
		 	AssertJUnit.assertEquals(act_email.toLowerCase(), login_id.toLowerCase());
		 	}
		 			}
	}
		public String getUserDetails(String id, String token) {
	
		 RequestSpecification httprequest=RestAssured.given().
		        header("id",id).
		 		header("token",token).
		 		header("source","android_app");
		 Response response=httprequest.request(Method.GET,"/app/profile");
		 String responseBody=response.getBody().asString();
		
		 System.out.println("responseBody for userdetails is: "+responseBody);
		 //get statuscode
		
		 int statusCode=response.getStatusCode();
		 System.out.println("Status code for userdetails is: "+statusCode);
		 AssertJUnit.assertEquals(statusCode,200);
		 String email=response.jsonPath().getString("data.member_info.email");
		 
		return email;
		}
	
}
