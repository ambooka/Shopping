package com.msah.mobilepos.basket;

import com.google.gson.Gson;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;

public class  MpesaStkPush {

    private static final String BASE_URL = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String sendRequest(@Nullable Integer value, long paymentNumber) throws Exception {

        // Data object representing the request body (consider using a custom object for better clarity)
        MpesaRequestData requestData = new MpesaRequestData(
                174379,
                "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjQwMzA0MjIzODA5",
                "20240304223809",
                "CustomerPayBillOnline",
                 value,
                paymentNumber,
                174379,
                paymentNumber,
                "https://mydomain.com/path",
                "CompanyXLTD",
                "Payment of X"
        );

        // Create JSON string from the data object
        Gson gson = new Gson();
        String json = gson.toJson(requestData);

        // Building the request using OkHttp library
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer I5UndGJ4htTM59B3kAhVyzQq2r8v")
                .build();

        // Execute request and handle response
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            assert response.body() != null;
            throw new Exception("Request failed with code: "+ paymentNumber + response.body().string());
        }

        // Process the response (e.g., parse JSON, extract data)
        assert response.body() != null;
        return response.body().string();
    }
}

// Class representing the Mpesa request data (optional but recommended for better code organization)
class MpesaRequestData {
    int BusinessShortCode;
    String Password;
    String Timestamp;
    String TransactionType;
    int Amount;
    long PartyA;
    int PartyB;
    long PhoneNumber;
    String CallBackURL;
    String AccountReference;
    String TransactionDesc;

    public MpesaRequestData(int businessShortCode, String password, String timestamp, String transactionType,
                            int amount, long partyA, int partyB, long phoneNumber, String callBackURL,
                            String accountReference, String transactionDesc) {
        this.BusinessShortCode = businessShortCode;
        this.Password = password;
        this.Timestamp = timestamp;
        this.TransactionType = transactionType;
        this.Amount = amount;
        this.PartyA = partyA;
        this.PartyB = partyB;
        this.PhoneNumber = phoneNumber;
        this.CallBackURL = callBackURL;
        this.AccountReference = accountReference;
        this.TransactionDesc = transactionDesc;
    }
}
