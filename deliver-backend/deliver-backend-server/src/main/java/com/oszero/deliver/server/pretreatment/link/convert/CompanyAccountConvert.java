package com.oszero.deliver.server.pretreatment.link.convert;

import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 企业账号转换
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyAccountConvert implements BusinessLink<SendTaskDto> {

    private CompanyAccount2Phone companyAccount2Phone;
    private CompanyAccount2Mail companyAccount2Mail;

    private static final Map<String, String> codeUpdateMap = new HashMap<>();

    static {
        codeUpdateMap.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_DING, PretreatmentCodeConstant.PHONE_DING);
        codeUpdateMap.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_WECHAT, PretreatmentCodeConstant.PHONE_WECHAT);
        codeUpdateMap.put(PretreatmentCodeConstant.COMPANY_ACCOUNT_FEI_SHU, PretreatmentCodeConstant.PHONE_FEI_SHU);
    }

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        if (Objects.isNull(companyAccount2Phone) || Objects.isNull(companyAccount2Mail)) {
            throw new LinkProcessException("[CompanyAccountConvert#process]错误：请注入相关实现！");
        }
        String code = context.getCode();
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        // 策略模式实现企业账号转换
        if (PretreatmentCodeConstant.COMPANY_ACCOUNT_MAIL.equals(code)) {
            sendTaskDto.setUsers(companyAccount2Mail.convert(users));
        } else {
            sendTaskDto.setUsers(companyAccount2Phone.convert(users));
            // 修改 code 码，以便于手机号转换平台 ID
            context.setCode(codeUpdateMap.get(code));
        }
    }

    public interface CompanyAccount2Phone {
        List<String> convert(List<String> companyAccountList);
    }

    public interface CompanyAccount2Mail {
        List<String> convert(List<String> companyAccountList);
    }
}
