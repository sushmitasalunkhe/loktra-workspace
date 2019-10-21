package Android.testcases.Lead;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import BasePage.BaseTest;
import PageObjects.Lead.LeadPage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getSelfLeads {
	@Test
	public void getLeads() {
		System.out.println("testcase running is : "+getSelfLeads.class.getSimpleName());

		LeadPage sl=new LeadPage();
		RequestSpecification httprequest= sl.getHttprequest();
		httprequest.queryParam("type", "assign").
				queryParam("tab_name", "fresh_leads").
				queryParam("sub_tab", "all").
				queryParam("page_number", "1");
		Response response=httprequest.request(Method.GET,"/app/lead/details-v3");
		String responseBody=response.getBody().asString();
		System.out.println("responseBody of new lead is: "+responseBody);
		//get statuscode
		int statusCode=response.getStatusCode();
		System.out.println("Status code is: "+statusCode);
	}

}
