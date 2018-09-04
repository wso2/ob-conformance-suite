package com.wso2.finance.open.banking.conformance.test.core;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Validator {

    public static void main(String[] args){
        ResponseValidator RV = new ResponseValidator();
        RV.ValidateResponse();

    }
}
