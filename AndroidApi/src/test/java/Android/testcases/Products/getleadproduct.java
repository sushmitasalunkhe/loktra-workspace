package Android.testcases.Products;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
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

import Android.testcases.Lead.AddReferLead;
import Android.testcases.Lead.getUserDetails;
import BasePage.BaseTest;
import PageObjects.Lead.LeadPage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getleadproduct {
	@Test
	public void getLeadproduct() throws JsonGenerationException, JsonMappingException, IOException {
		
		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		Response response=httprequest.request(Method.GET,"/source-manager/lead-form");
		String product_list=response.jsonPath().getString("data.display_products_info");
		getUserDetails ud=new getUserDetails();
		String user_product=ud.getUserDetails();
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
			 LinkedHashMap<String, Object>product1=new LinkedHashMap<String,Object>();
			     product1.put("product_name", product_name);
			     product1.put("product_id", product_id);
			     list_of_refer.add(product1);
}else {
				 product_name=response.jsonPath().getString("data.display_products_info["+i+"].display_product_name");
				 product_id=response.jsonPath().getString("data.display_products_info["+i+"].display_product_id");
				 LinkedHashMap<String, Object>product2=new LinkedHashMap<String,Object>();
			     product2.put("product_name", product_name);
			     product2.put("product_id", product_id);
			     list_of_self.add(product2);
			 
		 }
		 }
	list.add(list_of_self);
	list.add(list_of_refer);
ObjectMapper mapper = new ObjectMapper();
    
    mapper.writeValue(new File("products.json"), list);

	        
}
}
