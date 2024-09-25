package resources;

import POJO.AddPlace;
import POJO.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    /*instead to have the dummy data for test we will put in a class a method */
    public AddPlace addPlacePayload(String name, String language, String address){

        AddPlace pojoPlace = new AddPlace();
        Location location= new Location();
        location.setLat(-38.383494);
        location.setLng(33.383494);
        List<String> types = new ArrayList<String>();
        types.add("sucre park");
        types.add("umss");

        pojoPlace.setLocation(location);
        pojoPlace.setTypes(types);
        pojoPlace.setAccuracy(80);
        pojoPlace.setAddress(address);
        pojoPlace.setName(name);
        pojoPlace.setPhone_number("591-72207752");
        pojoPlace.setWebsite("bit-consulting.org");
        pojoPlace.setLanguage(language);

        return pojoPlace;
    }

    public String deletePlacePayload(String placeId){
        return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
    }
}
