package com.oszero.deliver.server.pretreatment.action.idcheck;

import com.oszero.deliver.server.exception.PipelineProcessException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyAccountCheckAction implements BusinessProcess<SendTaskDto> {

    private CompanyAccount2Phone companyAccount2Phone;
    private CheckCompanyAccount checkCompanyAccount;

    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        if (Objects.isNull(checkCompanyAccount) || Objects.isNull(companyAccount2Phone)) {
            throw new PipelineProcessException("[CompanyAccountCheckAction#process]错误：请注入相关实现！");
        }
        SendTaskDto sendTaskDto = context.getProcessModel();
        Stream<String> stream = sendTaskDto.getUsers().stream();
        // 企业自定义检查
        stream.forEach(companyAccount -> checkCompanyAccount.check(companyAccount));
        // 企业自定义转换为手机号
        sendTaskDto.setUsers(stream.map(companyAccount -> companyAccount2Phone.convert(companyAccount)).collect(Collectors.toList()));
    }

    public interface CheckCompanyAccount {
        void check(String companyAccount);
    }

    public interface CompanyAccount2Phone {
        String convert(String companyAccount);
    }
}
