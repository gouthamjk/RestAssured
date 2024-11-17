package defaultPackage;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public class OAuthAuthetication2 {

	public static void main(String[] args) {
		
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		
String response1 =given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
.formParam("grant_type", "client_credentials")
.formParam("scope", "trust")
.when().log().all()
.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

JsonPath js = new JsonPath(response1);
String AT =js.getString("access_token");
System.out.println(AT);

GetCourse response2 =given().queryParams("access_token", AT)
.when().log().all()
.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

System.out.println(response2.getUrl());
System.out.println(response2.getInstructor());
System.out.println(response2.getCourses().getApi().get(1).getCourseTitle());

List<API>apiCourses=  response2.getCourses().getApi();
for(int i=0;i<apiCourses.size();i++)
	if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
	{
		System.out.println(apiCourses.get(i).getPrice());
	}

ArrayList<String> a = new ArrayList<String>();
List<WebAutomation> w =response2.getCourses().getWebAutomation();
for(int j=0;j<w.size();j++)
{
	a.add(w.get(j).getCourseTitle());
}

List<String> expectedList = Arrays.asList(courseTitles);
Assert.assertTrue(a.equals(expectedList));
	}

}
