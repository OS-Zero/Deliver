import { TableSearchParams } from '.';

export interface MessageTemplate {
	templateId: number;
	templateName: string;
	templateDescription: string;
	usersType: number;
	usersTypeName: string;
	channelType: number;
	channelTypeName: string;
	channelProviderType: number;
	channelProviderTypeName: string;
	messageType: string;
	messageTypeName: string;
	templateStatus: number;
	createUser: string;
	createTime: string;
	appId: number;
	appName: string;
}
export interface SearchParams
	extends TableSearchParams,
		Pick<Partial<MessageTemplate>, 'usersType' | 'channelType' | 'channelProviderType' | 'messageType' | 'templateStatus' | 'createTime'> {
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

export type MoreMessageTemplateForm = Omit<MessageTemplate, keyof MessageTemplateForm>;

export interface TestSendMessage {
	templateId: Pick<MessageTemplate, 'templateId'>;
	users: string[];
	paramMap: Record<string, any>;
}
