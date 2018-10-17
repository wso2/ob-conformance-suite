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

package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.dto.UserDTO;
import org.wso2.finance.open.banking.conformance.mgt.exceptions.ConformanceMgtException;

/**
 * Interface to create a UserDAO.
 */
public interface UserDAO {

    /**
     *This method will add a new row to the user table when a new user is registered.
     * @param userDTO : UserDTO object
     */
    public void addUser(UserDTO userDTO) throws ConformanceMgtException;

    /**
     *This method will update an existing row in the user table when a UserDTO is given.
     * @param userDTO : UserDTO object
     */
    public void updateUser(UserDTO userDTO) throws ConformanceMgtException;

    /**
     *This method will return a UserDTO object when the username and password
     * for a particular user is given.
     * @param userID : ID of the user
     * @param password : Password of the user
     * @return UserDTO object for the requested user.
     */
    public UserDTO getUser(String userID, String password) throws ConformanceMgtException;

    /**
     *This method will delete the row in the user table belonging to the
     * given userID.
     * @param userID : ID of the user
     */
    public void deleteUser(String userID) throws ConformanceMgtException;
}
