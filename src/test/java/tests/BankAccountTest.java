package tests;

import api.BankAccountAPI;
import api.LoginAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BankAccount;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Bank Accounts")
public class BankAccountTest {
    private final static String TOKEN = LoginAPI.getToken();
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Test
    @Tag("Bank-accounts")
    @DisplayName("Can create bank account")
    @Description("Checks that user can create default bank account")
    public void canCreateDefaultBankAccount(){
        //Create bank account instance
        BankAccountAPI bankAccountAPI = new BankAccountAPI(TOKEN);
        //Create dto
        BankAccount uniCreditBankAccount = new BankAccount();
        uniCreditBankAccount.setBankEng("Uni Credit BulBank");
                uniCreditBankAccount.setBank("УКББ");
                uniCreditBankAccount.setIban("BG25UNCR7000322253226");
                uniCreditBankAccount.setCurrency("BGN");
                uniCreditBankAccount.setIsDefault(true);
                uniCreditBankAccount.setBic("UNCR");
        Response createResp = bankAccountAPI.createBankAccount(uniCreditBankAccount);
        Assertions.assertEquals(201,createResp.statusCode());
        int id = createResp.then().extract().path("id");
        //Retrieve new bank account
        Response getResp = bankAccountAPI.getBankAccount(id);
        Assertions.assertEquals(200,getResp.statusCode());
        BankAccount createdBankAccount = gson.fromJson(getResp.asString(), BankAccount.class);
        Assertions.assertEquals(uniCreditBankAccount.getBank(),createdBankAccount.getBank());
        Assertions.assertEquals(uniCreditBankAccount.getIban(),createdBankAccount.getIban());
        Assertions.assertEquals(uniCreditBankAccount.getCurrency(),createdBankAccount.getCurrency());
        Assertions.assertTrue(createdBankAccount.getIsDefault(),"The bank account is not marked as default");
        Assertions.assertFalse(createdBankAccount.getDeleted(),"The bank account is not deleted");
        //Delete bank account
        Response deleteResp = bankAccountAPI.deleteBankAccount(id);
        Assertions.assertEquals(204,deleteResp.statusCode());
    }

    @Test
    @Tag("Bank-accounts")
    @DisplayName("Can update bank account")
    @Description("Checks that user can update default bank account")
    public void canUpdateBankAccount(){
        BankAccountAPI bankAccountAPI = new BankAccountAPI(TOKEN);
        BankAccount fibBankAccount = new BankAccount();
        fibBankAccount.setBankEng("Uni Credit BulBank");
        fibBankAccount.setBank("УКББ");
        fibBankAccount.setIban("BG25UNCR70003222538326");
        fibBankAccount.setCurrency("BGN");
        fibBankAccount.setIsDefault(true);
        fibBankAccount.setBic("UNCR");
        Response createResp = bankAccountAPI.createBankAccount(fibBankAccount);
        Assertions.assertEquals(201,createResp.statusCode());
        int id = createResp.then().extract().path("id");
        //Retrieve new bank account
        Response getResp = bankAccountAPI.getBankAccount(id);
        Assertions.assertEquals(200,getResp.statusCode());
        //Update currency
        fibBankAccount.setCurrency("EUR");
        //Update bank account

        Response updateResp = bankAccountAPI.updateBankAccount(id,fibBankAccount);
        Assertions.assertEquals(204,updateResp.statusCode());
        //Get bank account
        getResp = bankAccountAPI.getBankAccount(id);
        Assertions.assertEquals("EUR",getResp.then().extract().path("currency"));

        //Delete bank account
        Response deleteResp = bankAccountAPI.deleteBankAccount(id);
        Assertions.assertEquals(204,deleteResp.statusCode());
    }

    @Test
    @Tag("Bank-accounts")
    @Tag("negative")
    @DisplayName("Can't create bank account")
    @Description("Checks that user can't create bank account with duplicate IBAN")
    public void cantCreateDefaultBankAccountWithDuplicateIban(){
        //Create bank account instance
        BankAccountAPI bankAccountAPI = new BankAccountAPI(TOKEN);
        //Create dto
        BankAccount uniCreditBankAccount = new BankAccount();
        uniCreditBankAccount.setBankEng("Uni Credit BulBank");
        uniCreditBankAccount.setBank("УКББ");
        uniCreditBankAccount.setIban("BG25UNCR7000322253226");
        uniCreditBankAccount.setCurrency("BGN");
        uniCreditBankAccount.setIsDefault(true);
        uniCreditBankAccount.setBic("UNCR");
        Response createResp = bankAccountAPI.createBankAccount(uniCreditBankAccount);
        Assertions.assertEquals(201,createResp.statusCode());
        int id = createResp.then().extract().path("id");
       //Try to create duplicate bank account
        Response createDuplicateAccountRes = bankAccountAPI.createBankAccount(uniCreditBankAccount);
        Assertions.assertEquals(400,createDuplicateAccountRes.statusCode());
        String actualError = createDuplicateAccountRes.path("error");
        Assertions.assertEquals("Вече съществува сметка с такъв IBAN",actualError);
        //Delete bank account
        Response deleteResp = bankAccountAPI.deleteBankAccount(id);
        Assertions.assertEquals(204,deleteResp.statusCode());
    }



}
