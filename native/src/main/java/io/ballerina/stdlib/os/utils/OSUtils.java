/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.ballerina.stdlib.os.utils;

import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;

import java.io.IOException;

import static io.ballerina.stdlib.os.nativeimpl.ModuleUtils.getModule;
import static io.ballerina.stdlib.os.utils.OSConstants.PROCESS_FIELD;
import static io.ballerina.stdlib.os.utils.OSConstants.PROCESS_TYPE;

/**
 * @since 0.8.0
 */
public class OSUtils {

    private static final BString UNKNOWN_MESSAGE = StringUtils.fromString("Unknown Error");

    /**
     * Returns error object  with message. Error type is generic ballerina error type. This utility to construct
     * error object from message.
     *
     * @param typeId The string type ID of the particular error object.
     * @param ex    Java throwable object to capture description of error struct. If throwable object is null,
     *              "Unknown Error" sets to message by default.
     * @return Ballerina error object.
     */
    public static BError getBallerinaError(String typeId, Throwable ex) {
        BString errorMsg = ex != null && ex.getMessage() != null ? StringUtils.fromString(ex.getMessage()) :
                UNKNOWN_MESSAGE;
        return getBallerinaError(typeId, errorMsg);
    }

    /**
     * Returns error object with message. Error type is generic ballerina error type. This utility to construct error
     * object from message.
     *
     * @param typeId  The specific error type ID.
     * @param message Java throwable object to capture description of error struct. If throwable object is null,
     *                "Unknown Error" is set to message by default.
     * @return Ballerina error object.
     */
    public static BError getBallerinaError(String typeId, BString message) {
        return ErrorCreator.createDistinctError(typeId, getModule(), message);
    }

    public static BObject getProcessObject(Process process) throws IOException {
        BObject obj = ValueCreator.createObjectValue(getModule(), PROCESS_TYPE);
        obj.addNativeData(PROCESS_FIELD, process);
        return obj;
    }

    public static Process processFromObject(BObject objVal) {
        return (Process) objVal.getNativeData(PROCESS_FIELD);
    }

    /**
     * Returns the os property which corresponds to the given key.
     *
     * @param key os property key
     * @return os property as a {@link String} or {@code PredefinedTypes.TYPE_STRING.getZeroValue()} if the
     * property does not exist.
     */
    public static BString getSystemProperty(BString key) {
        String value = System.getProperty(key.toString());
        if (value == null) {
            return StringUtils.fromString(
                    io.ballerina.runtime.api.PredefinedTypes.TYPE_STRING.getZeroValue().toString());
        }
        return StringUtils.fromString(value);
    }
}
