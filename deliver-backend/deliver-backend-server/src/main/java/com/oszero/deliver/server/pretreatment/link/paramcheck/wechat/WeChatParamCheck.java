package com.oszero.deliver.server.pretreatment.link.paramcheck.wechat;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 企业微信消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class WeChatParamCheck implements BusinessLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String pushSubject = paramMap.getOrDefault("pushSubject", "").toString();
        String wechatUserIdType = paramMap.getOrDefault("wechatUserIdType", "").toString();

        if (StrUtil.isBlank(pushSubject) || StrUtil.isBlank(wechatUserIdType)) {
            throw new MessageException("请传递参数：pushSubject 和 wechatUserIdType！！！");
        }

        List<String> users = sendTaskDto.getUsers();
        if ("app".equals(pushSubject)) {
            switch (wechatUserIdType) {
                case "touser":
                case "toparty":
                case "totag": {
                    paramMap.put(wechatUserIdType, String.join("|", users));
                    break;
                }
                case "to_parent_userid":
                case "to_student_userid":
                case "to_party": {
                    paramMap.put(wechatUserIdType, users);
                    break;
                }
                case "toall": {
                    break;
                }
                case "chatid": {
                    paramMap.put(wechatUserIdType, users.get(0));
                    break;
                }
                default: {
                    throw new MessageException("输入了非法的 wechatUserIdType，应该为 touser 或者 toparty 或者 totag 或者 to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall 或者 chatid！！！");
                }
            }
        } else if ("robot".equals(pushSubject)) {
            if (!"webhook".equals(wechatUserIdType)) {
                throw new MessageException("输入了非法的 wechatUserIdType，应该为 webhook！！！");
            }
        } else {
            throw new MessageException("输入了非法的 pushSubject，应该为 app 或者 robot！！！");
        }
    }
}
