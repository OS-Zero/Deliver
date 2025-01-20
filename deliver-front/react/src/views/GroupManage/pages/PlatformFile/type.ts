import { Pagination } from '@/types';

export interface PlatformFileBase {
  platformFileName: string;
  platformFileType: string;
  platformFileKey: string;
  channelType: number;
  appId: number;
}

export interface PlatformFileSearchParams extends PlatformFileBase, Pagination {
  startTime: string;
  endTime: string;
}

export interface PlatformFileDetail extends PlatformFileBase {
  platformFileId: number;
  platformFileDescription: string;
  platformFileTypeName: string;
  platformFileStatus: number;
  appName: string;
  createUser: string | null;
  createTime: string;
}

export interface PlatformFileUploadParams {
  platformFile: File;
  platformFileName: string;
  platformFileDescription: string;
  platformFileType: number;
  channelType: number;
  appId: number;
}