package com.oszero.deliver.server.client;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道-飞书工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class FeiShuClient {

    /**
     * 获取 tenantAccessToken
     *
     * @param feiShuApp 飞书 APP 配置
     * @return 返回 tenantAccessToken
     */
    public String getTenantAccessToken(FeiShuApp feiShuApp, SendTaskDto sendTaskDto) {
        @Data
        @AllArgsConstructor
        class TenantAccessTokenReqBody {
            private String app_id;
            private String app_secret;
        }
        TenantAccessTokenReqBody tenantAccessTokenReqBody =
                new TenantAccessTokenReqBody(feiShuApp.getAppId(), feiShuApp.getAppSecret());

        String body;
        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal")
                .header("Content-Type", "application/json; charset=utf-8")
                .body(JSONUtil.toJsonStr(tenantAccessTokenReqBody))
                .execute()) {
            @Data
            class TenantAccessTokenRespBody {
                private Integer code;
                private String msg;
                private String tenant_access_token;
                private Integer expire;
            }
            body = response.body();

            TenantAccessTokenRespBody tenantAccessTokenRespBody = JSONUtil.toBean(body, TenantAccessTokenRespBody.class);

            if (!Objects.equals(tenantAccessTokenRespBody.getCode(), 0)) {
                throw new MessageException(sendTaskDto, "获取飞书 tenantAccessToken 失败，" + tenantAccessTokenRespBody.getMsg());
            }

            log.info("获取飞书 tenantAccessToken 成功");
            return "Bearer " + tenantAccessTokenRespBody.getTenant_access_token();
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "获取飞书 tenantAccessToken 失败，服务异常");
        }
    }

    /**
     * 发送消息
     *
     * @param tenantAccessToken 飞书 token
     * @param sendTaskDto       消息 dto
     */
    public void sendMessage(String tenantAccessToken, SendTaskDto sendTaskDto) {

        @Data
        class SendMessageResponse {
            private Integer code;
            private String msg;
            private Object data;
        }
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String feiShuUserIdType = paramMap.get("feiShuUserIdType").toString();
        paramMap.remove("feiShuUserIdType");

        List<String> user_ids = (List<String>) paramMap.get("user_ids");
        paramMap.remove("user_ids");

        user_ids.forEach(userId -> {
            paramMap.put("uuid", UUID.randomUUID().toString());
            paramMap.remove("receive_id");
            paramMap.put("receive_id", userId);
            Object content = paramMap.get("content");
            String contentJson = JSONUtil.toJsonStr(content);
            paramMap.put("content", contentJson);
            String jsonStr = JSONUtil.toJsonStr(paramMap);

            SendMessageResponse sendMessageResponse;

            try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/im/v1/messages?receive_id_type=" + feiShuUserIdType)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .header("Authorization", tenantAccessToken)
                    .body(jsonStr)
                    .execute()) {

                sendMessageResponse = JSONUtil.toBean(response.body(), SendMessageResponse.class);
                if (!sendMessageResponse.getCode().equals(0)) {
                    throw new MessageException(sendTaskDto, "飞书发送消息失败，" + sendMessageResponse.getMsg());
                }
                log.info("飞书消息发送成功");
            } catch (Exception e) {
                throw new MessageException(sendTaskDto, "飞书消息发送失败，服务异常");
            }
        });

    }

    /**
     * 批量发送消息
     *
     * @param tenantAccessToken 飞书 token
     * @param sendTaskDto       消息 dto
     */
    public void sendMessageBatch(String tenantAccessToken, SendTaskDto sendTaskDto) {

        @Data
        class SendMessageResponse {
            private Integer code;
            private String msg;
            private Object data;
        }
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        // 移除掉用户判断的 feiShuUserIdType
        paramMap.remove("feiShuUserIdType");
        String body = JSONUtil.toJsonStr(paramMap);
        SendMessageResponse sendMessageResponse;

        try (HttpResponse response = HttpRequest.post("https://open.feishu.cn/open-apis/message/v4/batch_send/")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(body)
                .execute()) {

            sendMessageResponse = JSONUtil.toBean(response.body(), SendMessageResponse.class);
            if (!sendMessageResponse.getCode().equals(0)) {
                throw new MessageException(sendTaskDto, "飞书发送消息失败，" + sendMessageResponse.getMsg());
            }
            log.info("飞书消息发送成功");
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "飞书消息发送失败，服务异常");
        }
    }

    /**
     * 检查 userId 是否正确
     *
     * @param tenantAccessToken token
     * @param userId            id
     */
    public void checkUserId(String tenantAccessToken, String userId, SendTaskDto sendTaskDto) {

        try (HttpResponse execute = HttpRequest.get("https://open.feishu.cn/open-apis/contact/v3/users/" + userId + "?user_id_type=user_id")
                .header("Authorization", tenantAccessToken)
                .execute()) {
            @Data
            class FeiShuUserInfoRespBody {
                private Integer code;
                private String msg;
                private Object data;
            }
            FeiShuUserInfoRespBody feiShuUserInfoRespBody = JSONUtil.toBean(execute.body(), FeiShuUserInfoRespBody.class);
            if (!Objects.equals(feiShuUserInfoRespBody.getCode(), 0)) {
                throw new MessageException(sendTaskDto, "用户: " + userId + " userId 检验失败，" + feiShuUserInfoRespBody.getMsg());
            }
            log.info("飞书用户：" + userId + " ID 校验成功");
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "飞书用户 ID 校验失败，服务异常");
        }
    }

    /**
     * 通过手机号获取用户id
     *
     * @param tenantAccessToken token
     * @param phones            手机号列表
     * @return 用户id列表
     */
    public List<String> getUserIdsByPhones(String tenantAccessToken, List<String> phones, SendTaskDto sendTaskDto) {
        @Data
        class PhoneRequestBody {
            private List<String> mobiles;
        }
        PhoneRequestBody phoneRequestBody = new PhoneRequestBody();
        phoneRequestBody.setMobiles(phones);
        String body = JSONUtil.toJsonStr(phoneRequestBody);
        String respBody;

        try (HttpResponse execute = HttpRequest.post("https://open.feishu.cn/open-apis/contact/v3/users/batch_get_id" + "?user_id_type=user_id")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", tenantAccessToken)
                .body(body)
                .execute()) {
            @Data
            class UserIdAndPhone {
                private String user_id;
                private String mobile;
            }
            @Data
            class DataResp {
                List<UserIdAndPhone> user_list;
            }
            @Data
            class FeiShuUserIdRespBody {
                private Integer code;
                private String msg;
                private DataResp data;
            }
            respBody = execute.body();

            FeiShuUserIdRespBody feiShuUserIdRespBody = JSONUtil.toBean(respBody, FeiShuUserIdRespBody.class);
            if (!Objects.equals(feiShuUserIdRespBody.getCode(), 0)) {
                throw new MessageException(sendTaskDto, "转换 userId 失败，" + feiShuUserIdRespBody.getMsg());
            }
            return feiShuUserIdRespBody.getData().getUser_list().stream().map(UserIdAndPhone::getUser_id).collect(Collectors.toList());
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "飞书手机号转换用户 ID 失败，服务异常");
        }
    }
}
