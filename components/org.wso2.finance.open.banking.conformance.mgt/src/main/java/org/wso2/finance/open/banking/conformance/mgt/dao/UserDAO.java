package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.dto.UserDTO;

public interface UserDAO {
    public void addUser(UserDTO userDTO);
    public void updateUser(UserDTO userDTO);
    public UserDTO getUser(String userID, String password);
    public void deleteUser(String UserID);
}
