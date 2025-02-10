package com.oszero.deliver.business.server.mq.consumer.disruptor;

import com.lmax.disruptor.EventHandler;
import com.oszero.deliver.business.server.handler.BaseHandler;
import com.oszero.deliver.business.server.handler.impl.*;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.event.disruptor.DisruptorBaseEvent;
import com.oszero.deliver.business.server.mq.consumer.common.MQCommonConsumer;
import com.oszero.deliver.business.server.mq.producer.Producer;
import com.oszero.deliver.platformclient.client.wechat.OfficialAccountClient;
import lombok.AllArgsConstructor;

/**
 * @author oszero
 * @version 1.0.0
 */
public class DisruptorHandler {

    public static Producer PRODUCER;

    private static void onMessageAck(DisruptorBaseEvent event, BaseHandler handler) {
        SendTaskDto sendTaskDto = event.getSendTaskDto();
        try {
            MQCommonConsumer.tryHandle(sendTaskDto, handler);
        } catch (Exception exception) {
            MQCommonConsumer.catchHandle(sendTaskDto, exception, PRODUCER);
        }
    }

    @AllArgsConstructor
    public static class DisruptorCallEventHandler implements EventHandler<DisruptorBaseEvent.CallEventDisruptor> {

        private final CallHandler callHandler;

        @Override
        public void onEvent(DisruptorBaseEvent.CallEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, callHandler);
        }
    }

    @AllArgsConstructor
    public static class DisruptorSmsEventHandler implements EventHandler<DisruptorBaseEvent.SmsEventDisruptor> {

        private final SmsHandler smsHandler;

        @Override
        public void onEvent(DisruptorBaseEvent.SmsEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, smsHandler);
        }
    }

    @AllArgsConstructor
    public static class DisruptorMailEventHandler implements EventHandler<DisruptorBaseEvent.MailEventDisruptor> {

        private final MailHandler mailHandler;

        @Override
        public void onEvent(DisruptorBaseEvent.MailEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, mailHandler);
        }
    }

    @AllArgsConstructor
    public static class DisruptorDingEventHandler implements EventHandler<DisruptorBaseEvent.DingEventDisruptor> {

        private final DingHandler dingHandler;
        @Override
        public void onEvent(DisruptorBaseEvent.DingEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, dingHandler);
        }

    }

    @AllArgsConstructor
    public static class DisruptorWeChatEventHandler implements EventHandler<DisruptorBaseEvent.WeChatEventDisruptor> {

        private final WeChatHandler weChatHandler;
        @Override
        public void onEvent(DisruptorBaseEvent.WeChatEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, weChatHandler);
        }

    }

    @AllArgsConstructor
    public static class DisruptorFeiShuEventHandler implements EventHandler<DisruptorBaseEvent.FeiShuEventDisruptor> {

        private final FeiShuHandler feiShuHandler;
        @Override
        public void onEvent(DisruptorBaseEvent.FeiShuEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, feiShuHandler);
        }
    }

    @AllArgsConstructor
    public static class DisruptorOfficialAccountEventHandler implements EventHandler<DisruptorBaseEvent.OfficialAccountEventDisruptor> {

        private final OfficialAccountHandler officialAccountHandler;
        @Override
        public void onEvent(DisruptorBaseEvent.OfficialAccountEventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
            DisruptorHandler.onMessageAck(event, officialAccountHandler);
        }
    }
}
