package com.oszero.deliver.server.pretreatment.link.idcheck;

import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 企业账号检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyAccountCheck implements BusinessLink<SendTaskDto> {

    private final CheckCompanyAccount checkCompanyAccount;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        if (Objects.isNull(checkCompanyAccount)) {
            throw new MessageException("[CompanyAccountCheck#process]错误：请注入相关实现！");
        }
        SendTaskDto sendTaskDto = context.getProcessModel();
        // 企业账号检查
        sendTaskDto.getUsers().forEach(checkCompanyAccount::check);
    }

    public interface CheckCompanyAccount {
        void check(String companyAccount);
    }
}
