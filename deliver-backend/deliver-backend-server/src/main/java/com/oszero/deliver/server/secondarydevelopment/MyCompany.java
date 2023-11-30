package com.oszero.deliver.server.secondarydevelopment;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.pretreatment.link.convert.CompanyAccountConvert;
import com.oszero.deliver.server.pretreatment.link.idcheck.CompanyAccountCheck;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 我的企业二次开发配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
public class MyCompany {

    /**
     * 企业账号真实性检测
     *
     * @author oszero
     * @version 1.0.0
     */
    @Component
    static class MyCheckCompanyAccount implements CompanyAccountCheck.CheckCompanyAccount {
        @Override
        public void check(String companyAccount) {
            if (!"oszero".equals(companyAccount)) {
                throw new MessageException("非法的企业账号！！！");
            }
        }
    }

    /**
     * 企业账号转换电话
     *
     * @author oszero
     * @version 1.0.0
     */
    @Component
    static class MyCompanyAccount2Phone implements CompanyAccountConvert.CompanyAccount2Phone {

        @Override
        public List<String> convert(List<String> companyAccountList) {
            return companyAccountList.stream().map(this::convert).collect(Collectors.toList());
        }

        /**
         * 企业账号转换手机号码
         *
         * @param companyAccount 企业账号
         * @return 手机号码
         */
        private String convert(String companyAccount) {
            // 编写适配自己企业的转换逻辑
            return "";
        }
    }

    /**
     * 企业账号转换邮箱
     *
     * @author oszero
     * @version 1.0.0
     */
    @Component
    static class MyCompanyAccount2Mail implements CompanyAccountConvert.CompanyAccount2Mail {

        @Override
        public List<String> convert(List<String> companyAccountList) {
            return companyAccountList.stream().map(this::convert).collect(Collectors.toList());
        }

        /**
         * 企业账号转换邮箱
         *
         * @param companyAccount 企业账号
         * @return 邮箱
         */
        private String convert(String companyAccount) {
            // 编写适配自己企业的转换逻辑
            return "";
        }
    }
}
