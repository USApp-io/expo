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
    mediaType?: string | null;
  };
}

export interface ImageLoadProgressEventData {
  loaded: number;
  total: number;
}

export interface ImageErrorEventData {
  error: string;
  ios?: {
    code: number;
    domain: string;
    description: string;
    helpAnchor: string | null;
    failureReason: string | null;
    recoverySuggestion: string | null;
  };
}
