package com.wso2.finance.open.banking.conformance.test.core;

public class Context {

    private static Context context = new Context();

    private  String baseURL;
    private  String bankID;

    private Context(){
        baseURL = "https://api-openbanking.wso2.com/OpenBankAPI";
        bankID = "bank-4020-01";

    }

    public static Context getInstance(){
        return context;
    }

    public  String getBaseURL()
    {
        return baseURL;
    }

    public String getBankID(){
        return bankID;
    }

}
