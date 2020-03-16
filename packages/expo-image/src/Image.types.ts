export enum ImageCacheType {
  LOCAL_OR_UNKNOWN = 0,
  REMOTE = 1,
  DISK = 2,
  MEMORY = 3,
}

export interface ImageLoadEventData {
  cacheType?: ImageCacheType;
  source: {
    url: string;
    width: number;
    height: number;
    mediaType?: string;
  };
}

export interface ImageLoadProgressEventData {
  loaded: number;
  total: number;
}

export interface ImageErrorEventData {
  error: string;
}
