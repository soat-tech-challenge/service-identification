package br.com.grupo63.serviceidentification.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class StepsDefinition {

    private int orderId;
    private String currentNationalId;
    private int productAmount;
    private int productId;
    private Response response;
    private String authorizationHeader = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4Y2Q5MjVlOS0wZTM4LTRkMTUtODNlMC1hZDUxN2MxYmI5ODcifQ.Hqk-xR35ZSKuhTIetzyaMhwMSr5ANCvgLm4xM6kmkUBqlex0zAsIY-DKGV_TCEFI8H1oJL8OTdeZKq4CMmTWdADxSGf8bxBVi3Z0PSClWFRrxwl1-Za5oyyypUfSS1mYgDTUDb3tAg7susUS5CKUzHd30zwWjOKCXRFzbxVm8j5VTh3Zjz2aI5EJMjzyZ79KxEyN0cS59Xtm0Crhaor9-nhGI6S_73HOmHvbrA5_AOsUVNpTZgppYMjJkHxwDimxyxH_tzakCQLUAG0Yw2YNKhUPVADEeBFuDN2kiy81XbYt12JgPK3_KdDqzb6cKjXYMeFVAEFJkcF6IGbcRQK__g";
    private static final String BASE_URL_IDENTIFICATION = "http://127.0.0.1:8081";
    private static final String CONTEXT_PATH_IDENTIFICATION = "/identification/clients";
    private static final String BASE_URL_ORDERS = "http://127.0.0.1:8083";
    private static final String CONTEXT_PATH_ORDERS = "/order/orders";
    private static final String BASE_URL_PAYMENT = "http://127.0.0.1:8080";
    private static final String CONTEXT_PATH_PAYMENT = "/payment/payments";
    private static final String BASE_URL_PRODUCTION = "http://127.0.0.1:8082";
    private static final String CONTEXT_PATH_PRODUCTION = "/production/status";

    @Given("User wants to identify with national id {string}")
    public void userWantsToIdentifyWithNationalId(String nationalId) {
        currentNationalId = nationalId;
        System.err.println("User will be identified with national ID: " + currentNationalId);
    }

    @When("Send the request with valid data")
    public void sendTheRequestWithValidData() {
        response = given()
                .when()
                .post(BASE_URL_IDENTIFICATION + CONTEXT_PATH_IDENTIFICATION + "/identification?nationalId=" + currentNationalId);
        System.err.println("A request with the national ID: " + currentNationalId + " was sent and returned status: " + response.statusCode());
    }

    @Then("User identified")
    public void userIdentified() {
        HashMap<String, Object> body = response.as(HashMap.class);
        System.err.println("The identification was done and the client has id: " + body.get("id"));
    }

    @Given("User selected product {int} unit of product {int}")
    public void userSelectedProductUnitOfProduct(int amount, int id) {
        productAmount = amount;
        productId = id;
        System.err.println("Order will be created with " + productAmount + " product with id " + productId);
    }

    @When("User checkout")
    public void userCheckout() {
        response = given()
                .header("Authorization", authorizationHeader)
                .contentType(ContentType.JSON)
                .body(new HashMap<String, Object>(){{
                    put("items", new ArrayList<HashMap<String, Object>>() {{
                        add(new HashMap<String, Object>() {{
                            put("id", productId);
                            put("quantity", productAmount);
                        }});
                    }});
                }})
                .when()
                .post(BASE_URL_ORDERS + CONTEXT_PATH_ORDERS);
        System.err.println("A checkout request was made and returned " + response.statusCode());
    }

    @Then("Order created")
    public void orderCreated() {
        HashMap<String, Object> body = response.as(HashMap.class);
        System.err.println("Order created with id: " + body.get("id") + " and value: " + body.get("totalPrice"));
    }

    @Given("User wants to pay order {int} with QR Code")
    public void userWantsToPayOrderWithQRCode(int orderId) {
        this.orderId = orderId;
        System.err.println("Selecting order " + orderId + " to initialize payment");
    }

    @When("Initialize payment")
    public void initializePayment() {
        response = given()
                .header("Authorization", authorizationHeader)
                .when()
                .post(BASE_URL_PAYMENT + CONTEXT_PATH_PAYMENT + "/initialize?orderId=" + orderId);
        System.err.println("A initialize payment request was made and returned " + response.statusCode());
    }

    @Then("QR Code generated")
    public void qrCodeGenerated() {
        HashMap<String, Object> body = response.as(HashMap.class);
        System.err.println("Payment initialized and returned QR Code with data: " + body.get("qrData"));
    }

    @Given("User paid initialized payment order {int}")
    public void userPaidInitializedPaymentOrder(int orderId) {
        this.orderId = orderId;
        System.err.println("Finishing order " + orderId + " payment");
    }

    @When("Finish payment")
    public void finishPayment() {
        response = given()
                .header("Authorization", authorizationHeader)
                .when()
                .post(BASE_URL_PAYMENT + CONTEXT_PATH_PAYMENT + "/finalize?orderId=" + orderId);
        System.err.println("A finalize payment request was made and returned " + response.statusCode());
    }

    @Then("Order should be received")
    public void orderShouldBeReceived() {
        response = given()
                .header("Authorization", authorizationHeader)
                .when()
                .get(BASE_URL_PAYMENT + CONTEXT_PATH_PAYMENT + "/status?orderId=" + orderId);
        HashMap<String, Object> body = response.as(HashMap.class);
        System.err.println("Order finished payment with status: " + body.get("status"));
    }

    @Given("Staff started preparing order {int}")
    public void staffStartedPreparingOrder(int orderId) {
        this.orderId = orderId;
        System.err.println("Staff started preparing order " + 1);
    }

    @Given("Staff finished preparing order {int}")
    public void staffFinishedPreparingOrder(int orderId) {
        this.orderId = orderId;
        System.err.println("Staff finished preparing order " + 1);
    }

    @Given("Staff delivered order {int}")
    public void staffDeliveredOrder(int orderId) {
        this.orderId = orderId;
        System.err.println("Staff delivered preparing order " + 1);
    }

    @When("Update order status")
    public void updateOrderStatus() {
        response = given()
                .header("Authorization", authorizationHeader)
                .when()
                .post(BASE_URL_PRODUCTION + CONTEXT_PATH_PRODUCTION + "/advance-status?orderId=" + orderId);
        System.err.println("A advance status request was made and returned " + response.statusCode());
    }

    @Then("Order status should be updated")
    public void orderStatusShouldBeUpdated() {
        HashMap<String, Object> body = response.as(HashMap.class);
        System.err.println("A advance status request was made and returned " + response.statusCode() + " and newStatus is: " + body.get("newStatus"));
    }
}
