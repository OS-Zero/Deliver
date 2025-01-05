import { TableSearchParams } from '.';
import { App, Channel, ChannelProvider } from './channelApp';

export interface Template {
	templateId: number;
	templateName: string;
}
export interface User {
	usersType: number;
	usersTypeName: string;
}
export interface Message {
	messageType: string;
	messageTypeName: string;
}
export interface MessageTemplate extends Template, User, Channel, ChannelProvider, App, Message {
	templateDescription: string;
	templateStatus: number;
	createUser: string;
	createTime: string;
}
export interface SearchParams
	extends TableSearchParams,
		Pick<Partial<MessageTemplate>, 'usersType' | 'channelType' | 'channelProviderType' | 'messageType' | 'templateStatus'> {
	startTime?: string;
	endTime?: string;
}

export interface Message {
	messageType: string;
	messageTypeName: string;
}

export type MessageTemplateForm = Pick<
	MessageTemplate,
	'templateName' | 'templateDescription' | 'usersType' | 'channelType' | 'channelProviderType' | 'messageType' | 'appId' | 'templateId'
>;

export interface TestSendMessage {
	templateId: Template['templateId'];
	users: string[];
	paramMap: Record<string, any>;
}
