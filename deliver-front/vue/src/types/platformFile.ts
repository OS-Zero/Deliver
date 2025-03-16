import { TableSearchParams } from '.';
import { App, Channel, ChannelProvider } from './channelApp';

export interface PlatformFile extends App, ChannelProvider, Channel {
	platformFileId: number;
	platformFileName: string;
	platformFileDescription: string;
	platformFileType: string;
	platformFileTypeName: string;
	platformFileKey: string;
	platformFileStatus: number;
	createUser: string;
	createTime: string;
}
export interface UploadPlatformFile
	extends Pick<PlatformFile, 'platformFileName' | 'platformFileDescription' | 'platformFileType' | 'channelType' | 'appId' | 'channelProviderType'> {
	platformFile: File;
}
export interface SearchParams
	extends TableSearchParams,
		Pick<Partial<PlatformFile>, 'platformFileType' | 'platformFileKey' | 'channelType' | 'appId' | 'platformFileName' | 'channelProviderType'> {
	startTime?: string;
	endTime?: string;
}
