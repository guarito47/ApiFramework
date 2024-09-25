package GRAPHQL;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;


import static io.restassured.RestAssured.given;

public class GraphQlScript {
    public static void main(String[] args) {
        // FOR QUERY KIND
        int characterId=14; //you can inject into variables script as string
        String reponseGraph=
        given().log().all().header("Content-Type", "application/json")
        //IMPORTANT!: dont use the graphql syntax, transform to json to have the parse graphql to json
        // execute the query in grapghql page>inspect > network (tab) > click graphql > payload (tab)
        // there is your converted json script
                .body("{\"query\":\"query($characterId: Int!, $episodeId: Int!){\\n  character(characterId: $characterId){\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  \\n  location(locationId:650){\\n    name\\n    dimension\\n  }\\n  \\n  episode(episodeId: $episodeId){\\n    name\\n    air_date\\n    episode\\n  }\\n  \\n  characters(filters: {name: \\\"rahul\\\"}){\\n    info{\\n      count\\n    }\\n    result{\\n      name\\n      type\\n    }\\n  }  \\n  \\n  episodes(filters: {episode:\\\"hulu\\\"}){\\n    result{\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":650,\"episodeId\":100}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(reponseGraph);
        JsonPath js = new JsonPath(reponseGraph);
        String charName= js.getString("data.character.name");
        Assert.assertEquals(charName, "Rahul");

        // FOR MUTATIONS KIND
        String characterName = "sayler guarachi 2";
        String mutationResponseGraph=
                given().log().all().header("Content-Type", "application/json")
                        //IMPORTANT!: dont use the graphql syntax, transform to json to have the parse graphql to json
                        // execute the query in grapghql page>inspect > network (tab) > click graphql > payload (tab)
                        // there is your converted json script
                        .body("{\"query\":\"mutation ($locationName: String!, $characterName: String!, $episodeName: String! ){\\n  createLocation(location: {name: $locationName, type:\\\"Southzone\\\", dimension:\\\"234\\\"}){\\n    id\\n  }  \\n \\t\\n  createCharacter(character: {name:$characterName, type:\\\"Macho\\\", status:\\\"alive\\\",\\n    species:\\\"fantasy\\\", gender:\\\"male\\\", image:\\\"/pic.png\\\", originId: 13287, locationId:13287}){\\n    id\\n  }\\n  \\n  createEpisode(episode:{name:$episodeName, air_date:\\\"2025 february\\\", episode:\\\"hulu\\\"}){\\n    id\\n  }\\n  \\n  deleteLocations(locationIds: [13291,13290 ]){\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"locationName\":\"NewZealand\",\"characterName\":\""+characterName+"\",\"episodeName\":\"Manifest\"}}")
                        .when().post("https://rahulshettyacademy.com/gq/graphql")
                        .then().extract().response().asString();
        System.out.println(mutationResponseGraph);
        JsonPath jsm = new JsonPath(mutationResponseGraph);
        String extractName= jsm.getString("data.createCharacter.id");
        Assert.assertEquals(extractName , "8847");

    }
}
