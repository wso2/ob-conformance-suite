
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

package com.wso2.finance.open.banking.conformance.test.core.utilities;

import org.apache.log4j.Logger;

/**
 * Class that provides logging methods.
 */
public class Log {

    private static Logger log = Logger.getLogger(Log.class.getName());

    /**
     * log Info messages.
     *
     * @param message
     */
    public static void info(String message) {

        log.info(message);
    }

    /**
     * log Warning messages.
     *
     * @param message
     */
    public static void warn(String message) {

        log.warn(message);
    }

    /**
     * log Error messages.
     *
     * @param message
     */
    public static void error(String message) {

        log.error(message);
    }

    /**
     * log Fatal Error messages.
     *
     * @param message
     */
    public static void fatal(String message) {

        log.fatal(message);
    }

    /**
     * log debug messages.
     *
     * @param message
     */
    public static void debug(String message) {

        log.debug(message);
    }
}
