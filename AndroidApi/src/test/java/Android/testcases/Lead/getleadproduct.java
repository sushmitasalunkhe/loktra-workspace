package Android.testcases.Lead;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;
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
	public void getleadForm() throws JsonGenerationException, JsonMappingException, IOException{

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
		LinkedHashMap<String, Object>Product=new LinkedHashMap<String,Object>();
		
		LinkedHashMap<String,Object> ProductList_forself=new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> ProductList_forrefer=new LinkedHashMap<String,Object>();
		getUserDetails ud=new getUserDetails();
		String user_product=ud.getUserDetails(id,token);
		String product[]=new String[24];
	for(int i=0;i<24;i++) {
		String vertical_product_name=response.jsonPath().getString("data.display_products_info["+i+"].vertical_product_name");
		
		 if (!user_product.equals(vertical_product_name)){
		product[0]=response.jsonPath().getString("data.display_products_info["+i+"].display_product_id");
		//System.out.println("product_id is: "+product[0]);
		product[1]=response.jsonPath().getString("data.display_products_info["+i+"].display_product_name");
		//System.out.println("product_name is: "+product[1]);
		 //JSONArray list = new JSONArray();
		 ProductList_forrefer.put("product_id "+i,product[0]);
		 ProductList_forrefer.put("product_name"+i,product[1]);
		 }else {
				product[i]=response.jsonPath().getString("data.display_products_info["+i+"].display_product_id");
				ProductList_forself.put("product_id "+i,product[i]);
			}
		 }
	
	Product.put("Product_s", ProductList_forself);
	Product.put("Product_r", ProductList_forrefer);
	        ObjectMapper mapper = new ObjectMapper();
	       
	        mapper.writeValue(new File("products.json"), Product);
	       
}
}
