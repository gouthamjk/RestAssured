package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String resp = given().header("Content-Type", "application/json").body(Payload.addBook1(isbn, aisle))
				.post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = ReUsableMethod.rawToJson(resp);
		String id = js.get("ID");
		System.out.println(id);

	}

	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"asdfg","1234"},{"hjkla","5678"},{"qwerty","9012"}};
	}
}
