package org.com.schedulemanagement.mmScheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.com.scheduleproperties.Items;
import org.com.scheduleproperties.Schedule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class PostSchedule {
	
	public static void postRequest() throws InterruptedException, ParseException {
		Scanner myObj = new Scanner(System.in);
		//---->To Clear the Db<--------
		System.out.println("Do you want to clear DB?");
		if(myObj.nextLine().equalsIgnoreCase("Yes")) {
			System.setProperty("webdriver.chrome.driver", "src/test/java/lib/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get("http://ec2-52-50-126-1.eu-west-1.compute.amazonaws.com/mongo-query");
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.navigate().refresh();
			String text = driver.findElement(By.xpath("//pre")).getText();
			System.out.println(text);
			Assert.assertEquals("DB cleared successfully!",text);
			driver.quit();
			
			int count=1;
			DateConfiguration d=new DateConfiguration();
			System.out.println("Enter the materialId");
			String userName = myObj.nextLine();
			System.out.println("Enter the Start number of Schedules");
			int reqId1 = myObj.nextInt();
			System.out.println("Enter the End number of Schedules");
			int reqId2 = myObj.nextInt();
			System.out.println("Enter the number of Items");
			int matID=myObj.nextInt();
			
			
			for (int i = reqId1; i <=reqId2; i++) {
	    		
				RequestSpecification request = RestAssured.with();
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("Authorization", "bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Im1idGVzdC8yMDE3MDcyNiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJicm46c2VydmljZTpzaC1zY2hlZHVsZS1pbXBvcnQtc2VydmljZSIsImV4cCI6MTU5NDkwMDI1NCwiaWF0IjoxNTYzMzY0MjU0LCJpc3MiOiJtb2NrYmlyZCIsInN1YiI6ImJybjpudWM6Y2xpZW50OnN0YXJnYXRlOmZsYWcifQ.n8MXhz_ww2-BMKJZA9CuOS3VBq759Pu4P5dPz_QMa7kgoq-ZTF2htIBeu9KOUZwNgugVwUEK5gCxD5fdJhVU2fZYhyoRQ5CcBd8dRQLUE_OouVI_UGHekMpoPeQIpWMTBOc2Gl1Q6EtMHfAc0h-1Filw_N2nIkO7LIAZVmrHFLOWRa0tZaC0BeKYY0E0hEskZ7Wt1jBT2g4Mb6Icd9R_gW2u3__rFUWCgzt74NUK5WmthsdPmrz34vxAihrAyV2EX-aUbk_cnDLd_S_oZFJW6pSEqiv75xs5e0tMRSCOHCavQDld51xhxaHZ2Ub48J8uR3coWsc2Anvimb4O8bbyIQ");
				String req_Id=""+i;
				Schedule s=new Schedule();
				s.setClient("new");
				s.setChannel("Cmore_Stars");
				s.setDateRequired(d.dateRequired());
				s.setRequestId(req_Id);
				s.setVersion("V001");
				s.setScheduleType("Primary");
				List<Items> l = new ArrayList<Items>();
				
				for (int j = 1; j <=matID; j++) {
					
					Items item1=new Items();
					String mat_Id="MAT-"+userName+"-"+i+"-"+j;
					item1.setMaterialID(mat_Id);
					item1.setContentType("Programme");
					item1.setTitle("test1");
					item1.setTimeRequired(d.timeRequired());
					item1.setDuration(100);
					item1.setMediaType("Video");
					item1.setMediaSource("File");
					
					l.add(item1);
					
					
				}
				s.setItems(l);
				Thread.sleep(500);
				//----->POST Request<-------
				Response response = request.baseUri("https://ulbw5caxdh.execute-api.eu-west-1.amazonaws.com/flag/").headers(headers).body(s).post("/sch-imp/schedule");
				int code=202;

				
				Assert.assertEquals(code, response.getStatusCode());
				System.out.println("Schedule Created successfully "+count);
				count++;
	    	}
		}
		else {
			System.out.println("DB is not Cleared");
			int count=1;
			DateConfiguration d=new DateConfiguration();
			System.out.println("Enter the materialId");
			String userName = myObj.nextLine();
			System.out.println("Enter the Start number of Schedules");
			int reqId1 = myObj.nextInt();
			System.out.println("Enter the End number of Schedules");
			int reqId2 = myObj.nextInt();
			System.out.println("Enter the number of Items");
			int matID=myObj.nextInt();
			
			
			for (int i = reqId1; i <=reqId2; i++) {
	    		
				RequestSpecification request = RestAssured.with();
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("Authorization", "bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Im1idGVzdC8yMDE3MDcyNiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJicm46c2VydmljZTpzaC1zY2hlZHVsZS1pbXBvcnQtc2VydmljZSIsImV4cCI6MTU5NDkwMDI1NCwiaWF0IjoxNTYzMzY0MjU0LCJpc3MiOiJtb2NrYmlyZCIsInN1YiI6ImJybjpudWM6Y2xpZW50OnN0YXJnYXRlOmZsYWcifQ.n8MXhz_ww2-BMKJZA9CuOS3VBq759Pu4P5dPz_QMa7kgoq-ZTF2htIBeu9KOUZwNgugVwUEK5gCxD5fdJhVU2fZYhyoRQ5CcBd8dRQLUE_OouVI_UGHekMpoPeQIpWMTBOc2Gl1Q6EtMHfAc0h-1Filw_N2nIkO7LIAZVmrHFLOWRa0tZaC0BeKYY0E0hEskZ7Wt1jBT2g4Mb6Icd9R_gW2u3__rFUWCgzt74NUK5WmthsdPmrz34vxAihrAyV2EX-aUbk_cnDLd_S_oZFJW6pSEqiv75xs5e0tMRSCOHCavQDld51xhxaHZ2Ub48J8uR3coWsc2Anvimb4O8bbyIQ");
				String req_Id=""+i;
				Schedule s=new Schedule();
				s.setClient("new");
				s.setChannel("Cmore_Stars");
				s.setDateRequired(d.dateRequired());
				s.setRequestId(req_Id);
				s.setVersion("V001");
				s.setScheduleType("Primary");
				List<Items> l = new ArrayList<Items>();
				
				for (int j = 1; j <=matID; j++) {
					
					Items item1=new Items();
					String mat_Id="MAT-"+userName+"-"+i+"-"+j;
					item1.setMaterialID(mat_Id);
					item1.setContentType("Programme");
					item1.setTitle("test1");
					item1.setTimeRequired(d.timeRequired());
					item1.setDuration(100);
					item1.setMediaType("Video");
					item1.setMediaSource("File");
					
					l.add(item1);
					
					
				}
				s.setItems(l);
				Thread.sleep(500);
				//----->POST Request<-------
				Response response = request.baseUri("https://ulbw5caxdh.execute-api.eu-west-1.amazonaws.com/flag/").headers(headers).body(s).post("/sch-imp/schedule");
				int code=202;

				
				Assert.assertEquals(code, response.getStatusCode());
				System.out.println("Schedule Created successfully "+count);
				count++;
	    	}
			
		}
		
	 
		
	   /*  
		int count=1;
		DateConfiguration d=new DateConfiguration();
		System.out.println("Enter the materialId");
		String userName = myObj.nextLine();
		System.out.println("Enter the number of Schedules");
		int reqId = myObj.nextInt();
		System.out.println("Enter the number of Items");
		int matID=myObj.nextInt();
		
		
		for (int i = 1; i <=reqId; i++) {
    		
			RequestSpecification request = RestAssured.with();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Authorization", "bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Im1idGVzdC8yMDE3MDcyNiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJicm46c2VydmljZTpzaC1zY2hlZHVsZS1pbXBvcnQtc2VydmljZSIsImV4cCI6MTU5NDkwMDI1NCwiaWF0IjoxNTYzMzY0MjU0LCJpc3MiOiJtb2NrYmlyZCIsInN1YiI6ImJybjpudWM6Y2xpZW50OnN0YXJnYXRlOmZsYWcifQ.n8MXhz_ww2-BMKJZA9CuOS3VBq759Pu4P5dPz_QMa7kgoq-ZTF2htIBeu9KOUZwNgugVwUEK5gCxD5fdJhVU2fZYhyoRQ5CcBd8dRQLUE_OouVI_UGHekMpoPeQIpWMTBOc2Gl1Q6EtMHfAc0h-1Filw_N2nIkO7LIAZVmrHFLOWRa0tZaC0BeKYY0E0hEskZ7Wt1jBT2g4Mb6Icd9R_gW2u3__rFUWCgzt74NUK5WmthsdPmrz34vxAihrAyV2EX-aUbk_cnDLd_S_oZFJW6pSEqiv75xs5e0tMRSCOHCavQDld51xhxaHZ2Ub48J8uR3coWsc2Anvimb4O8bbyIQ");
			String req_Id=""+i;
			Schedule s=new Schedule();
			s.setClient("new");
			s.setChannel("Cmore_Stars");
			s.setDateRequired(d.dateRequired());
			s.setRequestId(req_Id);
			s.setVersion("V001");
			s.setScheduleType("Primary");
			List<Items> l = new ArrayList<Items>();
			
			for (int j = 1; j <=matID; j++) {
				
				Items item1=new Items();
				String mat_Id="MAT-"+userName+"-"+i+"-"+j;
				item1.setMaterialID(mat_Id);
				item1.setContentType("Programme");
				item1.setTitle("test1");
				item1.setTimeRequired(d.timeRequired());
				item1.setDuration(100);
				item1.setMediaType("Video");
				item1.setMediaSource("File");
				
				l.add(item1);
				
				
			}
			s.setItems(l);
			Thread.sleep(500);
			//----->POST Request<-------
			Response response = request.baseUri("https://ulbw5caxdh.execute-api.eu-west-1.amazonaws.com/flag/").headers(headers).body(s).post("/sch-imp/schedule");
			int code=202;

			
			Assert.assertEquals(code, response.getStatusCode());
			System.out.println("Schedule Created successfully "+count);
			count++;
    	}
		*/
	}

	
	public static void test() throws ParseException {
		Scanner myObj = new Scanner(System.in);  
		System.out.println("Enter a String");
	    String userName = myObj.nextLine(); 
	    int count=1;
	    DateConfiguration d=new DateConfiguration();
	    System.out.println("Current Date is"+d.timeRequired());
	    System.out.println("TimeStamp is"+d.dateRequired());
		
		for (int i = 1; i <=3; i++) {
			
			
			for (int j = 1; j <=5; j++) {
				
				String reqId="MAT-"+userName+"-"+i+"-"+j;
				System.out.println(reqId);
				
				
			}
			System.out.println("Created successfully"+count);
			count=count+1;
			
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ParseException {
		
		postRequest();
		//test();
		
	}
}

