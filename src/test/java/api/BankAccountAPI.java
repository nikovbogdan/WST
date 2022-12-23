package api;

import core.Request;
import dto.BankAccount;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

public class BankAccountAPI extends Request {
    private static final String BANK_ACCOUNT_ENDPOINT = "bank/accounts";

    public BankAccountAPI(String token) {
        super(token);
    }

    /**
     * Retrieves bank accounts based on a criteria.
     *
     * @param queryParams filters line (order_by, per_page, order ...)
     * @return Response
     */
    @Step("Get bank accounts matching criteria")
    public Response getBankAccounts(Map<String, ?> queryParams) {
        return get(BANK_ACCOUNT_ENDPOINT, queryParams);
    }

    /**
     * Retrieves bank account by ID
     *
     * @param id Bank ID
     * @return Response
     */
    @Step("Get bank account by id {id}")
    public Response getBankAccount(int id) {
        return get(BANK_ACCOUNT_ENDPOINT + "/" + id);
    }

    /**
     * Retrieves bank account by ID
     *
     * @param id bank account ID
     * @return BankAccount
     */
    @Step("Get bank account as object")
    public BankAccount getBankAccountObj(int id) {
        Response response = getBankAccount(id);
        return response.then().extract().as(BankAccount.class);
    }

    /**
     * Deletes bank account by ID
     *
     * @param id bank account
     * @return Response
     */
    @Step("Delete bank account by ID")
    public Response deleteBankAccount(int id) {
        return delete(BANK_ACCOUNT_ENDPOINT + "/" + id);
    }

    /**
     * Creates bank account
     *
     * @param bankAccount bank account info
     * @return Response
     */
    @Step("Delete bank account ")
    public Response createBankAccount(BankAccount bankAccount) {
        return post(BANK_ACCOUNT_ENDPOINT, gson.toJson(bankAccount));
    }

    /**
     * Updates bank account
     *
     * @param bankAccount bank account info
     * @return Response
     */
    @Step("Delete bank account ")
    public Response updateBankAccount(int id, BankAccount bankAccount) {
        return patch(BANK_ACCOUNT_ENDPOINT + "/" + id, gson.toJson(bankAccount));
    }
}
