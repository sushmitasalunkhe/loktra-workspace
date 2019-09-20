package BasePage;

import io.restassured.RestAssured;

public class BasePage {
	 BasePage() {
			RestAssured.baseURI="https://loktra.loktra.com/api/v1";
			}

}
