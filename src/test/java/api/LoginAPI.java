package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Credentials;
import io.restassured.RestAssured;

public class LoginAPI {
    private static final String BASE_URI = "https://api.inv.bg";
    private static final String BASE_PATH = "/v3";
    private static final String EMAIL = "nikovbogdan@gmail.com";
    private static final String PASSWORD = "Velin19-06-15";
    private static final String DOMAIN = "bogdanwst";
    private static final String LOGIN_ENDPOINT = "/login/token";
    private static final Credentials credentials = new Credentials(EMAIL, PASSWORD, DOMAIN);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String getToken(){
        return RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .header("User-Agent", "Mozilla")
                .header("Content-Type", "application/json")
                .body(gson.toJson(credentials))
                .post(LOGIN_ENDPOINT)
                .prettyPeek()
                .then()
                .extract()
                .path("token");
    }
}
