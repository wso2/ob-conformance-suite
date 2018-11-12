/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.finance.open.banking.conformance.mgt.dto;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * DTO for User.
 */
public class UserDTO {

    private String userID;
    private String userName;
    private String password;
    private Date regDate;

    public UserDTO(String userID, String userName, String password, Date regDate){
        this.userID = userID;
        this.userName = userName;
        this.password = encryptPassword(password);
        this.regDate = new Date(regDate.getTime());
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegDate() {
        return new Date(regDate.getTime());
    }

    /**
     *This method will encrypt the input plain password using SHA-512 algorithm.
     * @param plainPassword : Human readable password
     * @return encrypted password using SHA-512
     */
    public static String encryptPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(plainPassword.getBytes(Charset.forName("UTF-8")));
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
