package defaultPackage;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {

	public static void main(String[] args) {
		
RestAssured.baseURI = "https://zhakaine.atlassian.net/";
String response = given().header("Content-Type","application/json")
.header("Authorization","Basic emhha2FpbmVAZ21haWwuY29tOkFUQVRUM3hGZkdGMGx0S0VmZTByXzdXbmJ4Q29MYVBiUVRkRm1KNThka3B1dloyN29Xa0ZINWlYUXpHWjRISmF0c2oxMGx3bmRqdVlMazFjTko3V3YzS2ZWaFlKOWRFem9rdlMwdzJoOVZUTkt2bDkxUWpxLThJeVN5VmhlZTk2dlNOdEdSYjFaSmlzcWhncWFlRUFfX2JQWWdsSzJRc2xNY3lyZ0hOd05jVk5oNGw4bHNPeHZKWT00OEU1NzlBRQ==")
.body("{\r\n"
		+ "    \"fields\": {\r\n"
		+ "       \"project\":\r\n"
		+ "       {\r\n"
		+ "          \"key\": \"SCRUM\"\r\n"
		+ "       },\r\n"
		+ "       \"summary\": \"Menu are not working\",\r\n"
		+ "       \"issuetype\": {\r\n"
		+ "          \"name\": \"Bug\"\r\n"
		+ "       }\r\n"
		+ "   }\r\n"
		+ "}").log().all()
.when().post("rest/api/3/issue")
.then().log().all().assertThat().statusCode(201).contentType("application/json").extract().response().asString();

JsonPath js = new JsonPath(response);
String issueId = js.get("id");

given().header("X-Atlassian-Token","no-check").pathParam("key", issueId)
.header("Authorization","Basic emhha2FpbmVAZ21haWwuY29tOkFUQVRUM3hGZkdGMGx0S0VmZTByXzdXbmJ4Q29MYVBiUVRkRm1KNThka3B1dloyN29Xa0ZINWlYUXpHWjRISmF0c2oxMGx3bmRqdVlMazFjTko3V3YzS2ZWaFlKOWRFem9rdlMwdzJoOVZUTkt2bDkxUWpxLThJeVN5VmhlZTk2dlNOdEdSYjFaSmlzcWhncWFlRUFfX2JQWWdsSzJRc2xNY3lyZ0hOd05jVk5oNGw4bHNPeHZKWT00OEU1NzlBRQ==")
.multiPart("file", new File("C:\\Users\\goutham.jk\\Downloads\\image (36).png")).log().all()
.when().post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);


	}

}
