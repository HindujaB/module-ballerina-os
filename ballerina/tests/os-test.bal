// Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/jballerina.java;
import ballerina/test;

@test:Config {}
function testGetEnv() {
    string expectedValue = getExpectedValidEnv();
    test:assertEquals(getEnv("JAVA_HOME"), expectedValue);
}

@test:Config {}
isolated function testGetEnvNegative() {
    test:assertEquals(getEnv("JAVA_XXXX"), "");
}

@test:Config {}
function testGetUserHome() {
    test:assertEquals(getUserHome(), getExpectedUserHome());
}

@test:Config {}
function testGetUsername() {
    test:assertEquals(getUsername(), getExpectedUserName());
}

@test:Config {}
function testSetEnv() {
    Error? result = setEnv("foo", "bar");
    if result is Error {
        test:assertFail("failed to set environment variable: " + result.message());
    } else {
        test:assertEquals(getEnv("foo"), "bar");
    }
}

@test:Config {}
function testGetSystemPropertyNegative() {
    test:assertEquals(getSystemProperty("non-existing-key"), "");
}

function getExpectedValidEnv() returns string = @java:Method {
    name: "testValidEnv",
    'class: "io.ballerina.stdlib.os.testutils.OSTestUtils"
} external;

function getExpectedUserHome() returns string = @java:Method {
    name: "testGetUserHome",
    'class: "io.ballerina.stdlib.os.testutils.OSTestUtils"
} external;

function getExpectedUserName() returns string = @java:Method {
    name: "testGetUserName",
    'class: "io.ballerina.stdlib.os.testutils.OSTestUtils"
} external;

isolated function isWindowsEnvironment() returns boolean = @java:Method {
    name: "isWindowsEnvironment",
    'class: "io.ballerina.stdlib.os.testutils.EnvironmentTestUtils"
} external;

function getSystemProperty(string key) returns string = @java:Method {
    name: "getSystemProperty",
    'class: "io.ballerina.stdlib.os.utils.OSUtils"
} external;
