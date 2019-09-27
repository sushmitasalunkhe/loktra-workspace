package Android.testcases.Login;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import BasePage.BasePage;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class LoginTest extends BasePage{
	
	LoginTest() {
		super();
	}
	HSSFWorkbook workbook;
    HSSFSheet sheet;
	HSSFCell cell;
	@Test
	public void login() throws IOException, ParseException {
		 /*File src=new File("/home/ankur-loktra/Documents/testdata/TestData.xls");
		 FileInputStream finput = new FileInputStream(src);
		 workbook = new HSSFWorkbook(finput);
		 sheet= workbook.getSheetAt(0);
		 System.out.println(sheet.getLastRowNum());
		 for(int i=1; i<=sheet.getLastRowNum(); i++) {
			          // Import data for Email.
			 cell = sheet.getRow(i).getCell(1);
			 cell.setCellType(CellType.STRING);
			 String login_id=cell.getStringCellValue();
			 cell=sheet.getRow(i).getCell(2);
			 int exp_statusCode=(int) cell.getNumericCellValue();
			 System.out.println(login_id);*/
			 //requestobject
	
			ObjectMapper mapper = new ObjectMapper();
			 HashMap<String, Object> result = mapper.readValue(new File(
	                    "login.json"), new TypeReference<Map<String, Object>>() {
	            });
		//	 System.out.println(result);
			//JSONObject jsonobject=(JSONObject)obj;
			Map<String,Object> login=(HashMap<String, Object>) result.get("login");
			//System.out.println(login);
			Set setofkeys=login.keySet();
			//System.out.println(setofkeys);
						Iterator itr=setofkeys.iterator();
					while(itr.hasNext()) {
						System.out.println();
						System.out.println();
						System.out.println("-----------------------------------------------------------------");
							String key=(String) itr.next();	
							String login_id= (String) login.get(key);
							 key=(String) itr.next();	
							String password=(String) login.get(key);
							key=(String) itr.next();
							int status_code= Integer.parseInt( (String) login.get(key));
							System.out.println(login_id);
			RequestSpecification httprequest=RestAssured.given().
					contentType("multipart/form-data").
					multiPart("login_id",login_id).
					multiPart("password",password).
					multiPart("source","android_app").
					header("Host","loktra.loktra.com");
			
			//responseobject
			Response response=httprequest.request(Method.POST,"/auth/login");
			String responseBody=response.getBody().asString();
			System.out.println("responseBody is: "+responseBody);
			//get statuscode
			int act_statusCode=response.getStatusCode();
			System.out.println("Status code is: "+act_statusCode);
			AssertJUnit.assertEquals(act_statusCode, status_code);}
		 
	
		 
		 }
	
}
