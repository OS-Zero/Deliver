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

    private final static Logger LOGGER = LoggerFactory.getLogger("MessageLinkTraceLogger");

    /**
     * 打印info信息
     *
     * @param s 信息
     */
    public static void info(String s) {
        LOGGER.info(s);
    }

    /**
     * 格式打印info信息
     *
     * @param s       信息
     * @param objects 格式
     */
    public static void info(String s, Object... objects) {
        LOGGER.info(s, objects);
    }

    /**
     * 打印error信息
     *
     * @param s 信息
     */
    public static void error(String s) {
        LOGGER.error(s);
    }

    /**
     * 格式打印error信息
     *
     * @param s       信息
     * @param objects 格式
     */
    public static void error(String s, Object... objects) {
        LOGGER.error(s, objects);
    }
}
