package org.wso2.finance.open.banking.conformance.mgt.dto;

import java.util.Date;

public class UserDTO {

    private String userID;
    private String userName;
    private String password;
    private Date regDate;

    public UserDTO(String userID, String userName, String password, Date regDate){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
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
}
