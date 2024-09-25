package resources;
//an enum class is a collection of constants or methods
public enum APIResources {
    AddPlaceAPI("maps/api/place/add/json"),
    GetPlaceAPI("maps/api/place/get/json"),
    DeletePlaceAPI("maps/api/place/delete/json");
    private String apiResource;

    APIResources(String resource){
        this.apiResource= resource;
    }

    public String getResource(){
        return this.apiResource;
    }
}
