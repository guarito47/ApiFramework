package stepDefinitions;

import POJO.AddPlace;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinition extends Utils {

    RequestSpecification givenSpecs;
    ResponseSpecification respSpecs;
    Response response;
    TestDataBuild data = new TestDataBuild();
    //AddPlace place;
    //static yo share this variable across all the tests otherwise will be reset to null
    static String place_id;

    @Given("Add place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {
        //requestSpecification comes from inheritance to reuse
        //specBuilder with base ur user and psw and body type
        /* this lines will go to
        "user calls AddPlaceAPI with post http request" that is the right place
        that talks about this tasks
        respSpecs=
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build();//applies to responseSpecification too
        */
        //HashMap<String, Object> place = data.addPlaceHashMapExcelDriven();
        //System.out.println("place content: "+place.get("name").toString());
        givenSpecs=  given().spec(requestSpecification())
                //.body(data.addPlacePayload(name, language, address));
        // tu use data from excel parsing to hash map object use the follow
        .body(data.addPlaceHashMapExcelDriven());

        //place= data.addPlacePayload(name, language, address);
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        // instead to use hard code values for constants we change for enum class
        // and in the enum cla
        // ss by using valueOf, we call the specific contructor (AddPaceAPI)
        APIResources apiRes= APIResources.valueOf(resource);
        // this will return an object where we can get the constant value using getResource
        System.out.println(apiRes.getResource()); //"maps/api/place/add/json"

        /*respSpecs=
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build();*/

        if (method.equalsIgnoreCase("POST")){
            //apiRes is the enum class that gives url resource
            response= givenSpecs.when().post(apiRes.getResource());
        } else if(method.equalsIgnoreCase("GET")){
            response= givenSpecs.when().get(apiRes.getResource());
        }
    }

    @Then("the api call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer stcode) {
        //just doing the assertions
        assertEquals(response.getStatusCode(), 200);

    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        assertEquals(getJsonPath(response, keyValue),expectedValue);
    }


    @And("verify place_id created maps to {string} using {string}")
    public void verifyPlace_idCreatedMapsToUsing(String providedName, String resource) throws IOException {
        //to call GET we need placeid, that we can find in response object using our getJsonPath
        place_id= getJsonPath(response,"place_id" );
        //1rt prepare request spec , we reuse the global requestSpecification givenSpecs
        //the basic info that uses all the methods are in the requestSpecification method
        givenSpecs=  given().spec(requestSpecification())
                .queryParam("place_id", place_id);
        //now we call the resource GetPlaceAPI, by reusing the method...
        user_calls_with_http_request(resource, "GET");
        //remember that above method save in the global RESPONSE object, so we can extract
        String resulApiName= getJsonPath(response,"name" );
        //resulApiName is the value returned by the api
        assertEquals(resulApiName, providedName);
    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        //we only prepare the request specs sending the place id json format
        givenSpecs= given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
        //and this givenSpecs will be used when calling method with deletePlaceAPI and POST
    }
}
