package PageObjects.Lead;

import BasePage.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class LeadPage {
		BaseTest bt=new BaseTest();
		String requestBody[]=bt.setUp();
		String token =requestBody[0];
		String id=requestBody[1];
		private RequestSpecification httprequest=RestAssured.given().contentType("application/json").
			header("id",id).
			header("token",token).
			header("source","android_app").
			header("Referer","https://loktra.loktra.com");
		public RequestSpecification getHttprequest() {
			return httprequest;
		}
		public void setHttprequest(RequestSpecification httprequest) {
			this.httprequest = httprequest;
		}

}
