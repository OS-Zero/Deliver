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

package com.oszero.deliver.business.common.util;

import com.oszero.deliver.business.common.constant.MDCConstant;

/**
 * @author oszero
 * @version 1.0.0
 */
public class TraceIdUtils {
    public static String getTraceId() {
        return MDCUtils.get(MDCConstant.TRANCE_ID_NAME);
    }

    public static void setTraceId(String traceId) {
        MDCUtils.put(MDCConstant.TRANCE_ID_NAME, traceId);
    }
}
