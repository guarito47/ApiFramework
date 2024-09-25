package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    //util handles the url, user pasw, and read json files an return specific value inside
    /* public and static the req object is mandatory to use this object across the entire
    //test executions for multiple sets of data, to avoid to create new req for every test
    //making static its like saving req in memory for further use when is called by this class
    once again and reuse the req object with the last setup*/
    public static RequestSpecification req; //is the return type of the builder
    //because this is common for most of the request task we will reuse through this method
    public RequestSpecification requestSpecification() throws IOException {
        //to continue adding logs to the existing logging.txt file, we only need to create one time
        //the requestSpecification req because everytime that executes this method will be created
        //a new one, replacing the previous test logs, we solve comparing to null
        if(req==null){
            //log wil be used later to save the related logging messages into logging.txt file
            PrintStream log= new PrintStream(new FileOutputStream("logging.txt"));
            req= //is the return type of the builder
                    new RequestSpecBuilder()
                            .setBaseUri(getGlobalValue("baseUrl"))
                            .addQueryParam("key", "qaclick123")
                            //we will log all related messages to send login info by adding this filter by requestLoggingFilter
                            //and the way to save will set through logRequestTo as logging.txt handled by PrintStream log
                            .addFilter(RequestLoggingFilter.logRequestTo(log))
                            //in the same way to catch the message related to logging results we reuse the log to add logs
                            .addFilter(ResponseLoggingFilter.logResponseTo(log))
                            .setContentType(ContentType.JSON)
                            .build();//always finish with build
            return req;
        }
        return req;

    }

    public static String getGlobalValue(String key) throws IOException {
        //here we read the global.properties file and return the value inside
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key){
        //reads a response object and return the value of "key"
        String res = response.asString();
        JsonPath js= new JsonPath(res);
        return js.get(key).toString();
    }
}
