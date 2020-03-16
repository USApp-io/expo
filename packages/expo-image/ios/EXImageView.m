// Copyright 2020-present 650 Industries. All rights reserved.

#import <expo-image/EXImageView.h>
#import <React/RCTConvert.h>

static NSString * const sourceUriKey = @"uri";

@implementation EXImageView

- (void)dealloc
{
  // Stop any active operations or downloads
  [self sd_setImageWithURL:nil];
}

# pragma mark -  Custom prop setters

- (void)setSource:(NSDictionary *)source
{
  NSURL *imageURL = [RCTConvert NSURL:source[sourceUriKey]];

  if (self.onLoadStart) {
    self.onLoadStart(@{
      @"url": imageURL.absoluteString ?: [NSNull null]
    });
  }

  [self sd_setImageWithURL:imageURL
          placeholderImage:nil
                   options:0
                  progress:[self progressBlock]
                 completed:[self completionBlock]];
}

- (SDImageLoaderProgressBlock)progressBlock
{
  __weak EXImageView *weakSelf = self;
  return ^(NSInteger receivedSize, NSInteger expectedSize, NSURL * _Nullable targetURL) {
    __strong EXImageView *strongSelf = weakSelf;
    if (strongSelf.onProgress) {
      strongSelf.onProgress(@{
        @"loaded": @(receivedSize),
        @"total": @(expectedSize)
      });
    }
  };
}

- (SDExternalCompletionBlock)completionBlock
{
  __weak EXImageView *weakSelf = self;
  return ^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
    __strong EXImageView *strongSelf = weakSelf;
    if (!strongSelf) {
      // Nothing to do
      return;
    }

    if (error && strongSelf.onError) {
      strongSelf.onError(@{
        @"error": error.localizedDescription
      });
    } else if (image && strongSelf.onLoad) {
      strongSelf.onLoad(@{
        @"cacheType": @([self convertToCacheTypeEnum:cacheType]),
        @"source": @{
            @"url": imageURL.absoluteString,
            @"width": @(image.size.width),
            @"height": @(image.size.height)
        }
      });
    }

    if (strongSelf.onLoadEnd) {
      strongSelf.onLoadEnd(@{});
    }
  };
}

- (EXImageCacheTypeEnum)convertToCacheTypeEnum:(SDImageCacheType)imageCacheType
{
  switch (imageCacheType) {
    case SDImageCacheTypeNone:
      return EXImageCacheRemote;
    case SDImageCacheTypeDisk:
      return EXImageCacheDisk;
    case SDImageCacheTypeMemory:
      return EXImageCacheMemory;
    default:
      return EXImageCacheUnknown;
  }
}

@end
