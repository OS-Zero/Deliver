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

package com.oszero.deliver.business.server.pretreatment.constant;

import com.oszero.deliver.business.common.constant.CommonConstant;
import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.common.enums.UsersTypeEnum;

/**
 * @author oszero
 * @version 1.0.0
 */
public class PretreatmentCodeConstant {

    /********** 电话号码的一些发送常量 **********/
    public static final String PHONE_CALL = getPretreatmentCode(UsersTypeEnum.PHONE, ChannelTypeEnum.CALL);
    public static final String PHONE_SMS = getPretreatmentCode(UsersTypeEnum.PHONE, ChannelTypeEnum.SMS);
    public static final String PHONE_DING = getPretreatmentCode(UsersTypeEnum.PHONE, ChannelTypeEnum.DING);
    public static final String PHONE_WECHAT = getPretreatmentCode(UsersTypeEnum.PHONE, ChannelTypeEnum.WECHAT);
    public static final String PHONE_FEI_SHU = getPretreatmentCode(UsersTypeEnum.PHONE, ChannelTypeEnum.FEI_SHU);

    /********** 邮箱的一些发送常量 **********/
    public static final String EMAIL_MAIL = getPretreatmentCode(UsersTypeEnum.EMAIL, ChannelTypeEnum.MAIL);

    /********** 平台ID的一些发送常量 **********/
    public static final String USERID_DING = getPretreatmentCode(UsersTypeEnum.DING_USER_ID, ChannelTypeEnum.DING);
    public static final String USERID_WECHAT = getPretreatmentCode(UsersTypeEnum.WECHAT_USER_ID, ChannelTypeEnum.WECHAT);
    public static final String USERID_FEI_SHU = getPretreatmentCode(UsersTypeEnum.FEI_SHU_USER_ID, ChannelTypeEnum.FEI_SHU);
    public static final String USERID_OFFICIAL_ACCOUNT = getPretreatmentCode(UsersTypeEnum.OFFICIAL_ACCOUNT_USER_ID, ChannelTypeEnum.OFFICIAL_ACCOUNT);

    private static String getPretreatmentCode(UsersTypeEnum usersTypeEnum, ChannelTypeEnum channelTypeEnum) {
        return usersTypeEnum.getCode() + CommonConstant.CODE_SEPARATE + channelTypeEnum.getCode();
    }

    public static final String CONVERT_PRE = "convert" + CommonConstant.CODE_SEPARATE;

}
