package defaultPackage;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ECommerceAPITest {

	public static void main(String[] args) {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("zhakaine@gmail.com");
		loginRequest.setPassword("Callofduty");
		
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse =reqLogin.when().post("/api/ecom/auth/login")
				.then().log().all().extract().response().as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId = loginResponse.getUserId();
		
		//Add Product

		RequestSpecification AddProductBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("Authorization", token).build();
		
		RequestSpecification reqAddProduct = given().log().all().spec(AddProductBaseReq).param("productName", "Contact")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Addias Originals").param("productFor", "women")
		.multiPart("productImage",new File("C:\\Users\\goutham.jk\\Downloads\\New folder (55)\\p02B_Canada_Contact.jpg"));
		
		String addProductResponse =reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId =js.get("productId");
		
		//Create Order
		RequestSpecification createOrderBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq= given().log().all().spec(createOrderBaseReq).body(orders);
		String responseAddOrder =createOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		System.out.println(responseAddOrder);
		
		//Delete Product
		RequestSpecification deleteProdBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		RequestSpecification deleteProdReq=given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
	String deleteProductResponse =	deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
	JsonPath js1 = new JsonPath(deleteProductResponse);
	System.out.println(js1.getString("message"));
	}

}
