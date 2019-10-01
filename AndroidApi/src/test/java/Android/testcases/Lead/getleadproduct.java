package Android.testcases.Lead;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;
import org.json.simple.JSONArray;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getleadproduct {
	@Test
	public void getLeadproduct() throws JsonGenerationException, JsonMappingException, IOException {

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
		Response response=httprequest.request(Method.GET,"/source-manager/lead-form");
		//String responseBody=response.getBody().asString();
		//System.out.println("responseBody of get leadform is: "+responseBody);
		//get statuscode
		/*int statusCode=response.getStatusCode();
		System.out.println("Status code is: "+statusCode);*/
		String product_list=response.jsonPath().getString("data.display_products_info");
		
		getUserDetails ud=new getUserDetails();
		String user_product=ud.getUserDetails(id,token);
//		String product[]=new String[24];
//		ArrayList<String> product = new ArrayList<>();
		String product_name;
		String product_id;
		List<Object> list=new ArrayList<Object>();
		List<Object> list_of_self=new ArrayList<Object>();
		List<Object> list_of_refer=new ArrayList<Object>();
	for(int i=0;i<24;i++) {
	String vertical_product_name=response.jsonPath().getString("data.display_products_info["+i+"].vertical_product_name");
		 
		 if (!user_product.equals(vertical_product_name)){
			
			 product_name=response.jsonPath().getString("data.display_products_info["+i+"].display_product_name");
			 product_id=response.jsonPath().getString("data.display_products_info["+i+"].display_product_id");
//			 product.add(product_name);
//			 product.add(product_id);
//			 
			 LinkedHashMap<String, Object>product1=new LinkedHashMap<String,Object>();
			     product1.put("product_name", product_name);
			     product1.put("product_id", product_id);
			     list_of_refer.add(product1);
			     
		
		//System.out.println("product_id is: "+product[0]);
//		 //JSONArray list = new JSONArray();
//		 ProductList_forrefer.put("product_id "+i,product[0]);
//		 ProductList_forrefer.put("product_name"+i,product[1]);

		 }else {
				 product_name=response.jsonPath().getString("data.display_products_info["+i+"].display_product_name");
				 product_id=response.jsonPath().getString("data.display_products_info["+i+"].display_product_id");
//				 product.add(product_name);
//				 product.add(product_id);
				  
				 LinkedHashMap<String, Object>product2=new LinkedHashMap<String,Object>();
			     product2.put("product_name", product_name);
			     product2.put("product_id", product_id);
			     list_of_self.add(product2);
				     // ... continue for all values
		 }
		 }
	list.add(list_of_self);
	list.add(list_of_refer);
ObjectMapper mapper = new ObjectMapper();
    
    mapper.writeValue(new File("products.json"), list);

	        
}
}
