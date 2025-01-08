import { TableSearchParams } from '.';
import { App, ChannelProvider } from './channelApp';

export interface PlatformFile extends App {
	platformFileId: number;
	platformFileName: string;
	platformFileDescription: string;
	platformFileType: string;
	platformFileTypeName: string;
	platformFileKey: string;
	platformFileStatus: number;
	channelType: number;
	createUser: string;
	createTime: string;
}
export interface UploadPlatformFile
	extends Pick<PlatformFile, 'platformFileName' | 'platformFileDescription' | 'platformFileType' | 'channelType' | 'appId'> {
	platformFile: File;
	channelProviderType: ChannelProvider['channelProviderType'];
}
export interface SearchParams
	extends TableSearchParams,
		Pick<Partial<PlatformFile>, 'platformFileType' | 'platformFileKey' | 'channelType' | 'appId'> {
	channelProviderType?: ChannelProvider['channelProviderType'];
	startTime?: string;
	endTime?: string;
}
