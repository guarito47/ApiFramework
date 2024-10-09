package resources;

import POJO.AddPlace;
import POJO.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public HashMap<String, Object> addPlaceHashMapExcelDriven() throws IOException {

        ApiExcelDriven scraper = new ApiExcelDriven();
        ArrayList<String> data= scraper.getData("addPlace");

        HashMap<String, Object> place = new HashMap<>();
        place.put("name", data.get(0));
        place.put("accuracy", data.get(5));
        place.put("phone_number", data.get(8));
        place.put("address", data.get(6));
        place.put("website", data.get(9));
        place.put("language", data.get(10));

        HashMap<String, Object> coordenates = new HashMap<>();
        coordenates.put("lat",data.get(1) );
        coordenates.put("lng",data.get(2) );
        place.put("location", coordenates);

        List<String> types = new ArrayList<>();
        types.add(data.get(3));
        types.add(data.get(4));
        place.put("types", types);

        return place;
    }

    public String deletePlacePayload(String placeId){
        return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
    }


}
