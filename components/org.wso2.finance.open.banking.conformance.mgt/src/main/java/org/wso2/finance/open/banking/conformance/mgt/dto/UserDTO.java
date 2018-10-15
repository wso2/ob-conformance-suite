package org.wso2.finance.open.banking.conformance.mgt.dto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class UserDTO {

    private String userID;
    private String userName;
    private String password;
    private Date regDate;

    public UserDTO(String userID, String userName, String password, Date regDate){
        this.userID = userID;
        this.userName = userName;
        this.password = encryptPassword(password);
        this.regDate = regDate;
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
        return regDate;
    }

    public static String encryptPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(plainPassword.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
