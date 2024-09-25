package stepDefinitions;
import java.io.IOException;
import io.cucumber.java.Before;
public class Hooks {
    //we have before, after, etc annotations
    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        //because deletePlace needs place_id, we need to run the methods that creates placeId
        StepDefinition preCond = new StepDefinition();
        if(StepDefinition.place_id==null){
            preCond.add_place_payload_with("eden", "spanish", "san borja");
            preCond.user_calls_with_http_request("AddPlaceAPI", "POST");
            preCond.verifyPlace_idCreatedMapsToUsing("eden", "GetPlaceAPI");
        }

    }
}
