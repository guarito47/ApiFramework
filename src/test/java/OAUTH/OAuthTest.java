package OAUTH;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main(String[] args) throws InterruptedException {
        /*code as service to get the code from google auth*/
        /*WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //once given the url with client id, auth uri, response type etc, we pas email & pass
        //to the google singin window using selenium
        driver.get("https://accounts.google.com/signin/oauth/identifier?andParmasProvidedByDeveloper");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("emailEx");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys (Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("passEx");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        //the response from google is a url with the code inside and other params
        String urlFromGoogle= driver.getCurrentUrl();*/
        String urlFromGoogle ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
        //to takeOut initial text cutting by code= and keep the begining of the code + garbage
        String rightSideUrl=urlFromGoogle.split("code=")[1];
        //to keep the code and takeOut the rest by cutting using &scope
        String code=rightSideUrl.split("&scope")[0];

        /*code as service to get token*/
        String accesstokenResponse=
        //to keep teh special characters of code without changing MANDATORY use urlEncodingEnabled

        given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                //.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                /* after when, with log().all() we can see the logs of the console*/
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();
        /*because we hay the response in raw json we wil handle with js to handle easier*/
        JsonPath js= new JsonPath(accesstokenResponse);
        String accessToken= js.getString("access_token");
        /* */
        String response= given().queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);

    }

}
