/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.server.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息链路追踪日志类
 *
 * @author oszero
 * @version 1.0.0
 */
public class MessageLinkTraceLogger {

    private final static Logger logger = LoggerFactory.getLogger("MessageLinkTraceLogger");

    public static void info(String s) {
        logger.info(s);
    }

    public static void info(String s, Object... objects) {
        logger.info(s, objects);
    }

    public static void error(String s) {
        logger.error(s);
    }

    public static void error(String s, Object... objects) {
        logger.error(s, objects);
    }
}
