package tests;

import api.ItemAPI;
import api.LoginAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Item;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Items")
public class ItemAPITest {

    private static final String TOKEN = LoginAPI.getToken();
    private final static ItemAPI itemAPI = new ItemAPI(TOKEN);
    private Item item;

    @BeforeEach
    public void beforeEach() {
        item = Item.builder()
                .name("Nescafe")
                .price(20.50)
                .priceForQuantity(5.0)
                .currency("BGN")
                .unit("kg.")
                .build();

    }


    @Test
    @DisplayName("Create/Delete item")
    public void creatingAndDeletingItem() {

        Response createResp = itemAPI.createItem(item);
        assertEquals(201, createResp.statusCode());
        int id = createResp.then().extract().path("id");

        Response deleteResp = itemAPI.deleteItem(id);
        assertEquals(204,deleteResp.statusCode());
    }


}
