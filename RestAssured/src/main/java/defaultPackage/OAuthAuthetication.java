package defaultPackage;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuthAuthetication {

	public static void main(String[] args) {
String response1 =given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
.formParam("grant_type", "client_credentials")
.formParam("scope", "trust")
.when().log().all()
.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

JsonPath js = new JsonPath(response1);
String AT =js.getString("access_token");
System.out.println(AT);

String response2 =given().queryParams("access_token", AT)
.when().log().all()
.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
System.out.println(response2);

	}

}
