import React from 'react';
import {
  AccessibilityProps,
  ImageSourcePropType,
  ImageStyle,
  NativeSyntheticEvent,
  StyleProp,
} from 'react-native';

import ExpoImage from './ExpoImage';
import { ImageErrorEventData, ImageLoadEventData, ImageLoadProgressEventData } from './Image.types';

export interface ImageProps extends AccessibilityProps {
  // On one hand we want to pass resolved source to native module.
  // On the other hand, react-native-web doesn't expose a resolveAssetSource
  // function, so we can't use it there. So we pass the unresolved source
  // to "native components" and they decide whether to resolve the value
  // or not.
  source?: ImageSourcePropType | null;
  style?: StyleProp<ImageStyle>;

  onLoadStart?: () => void;
  onProgress?: (event: NativeSyntheticEvent<ImageLoadProgressEventData>) => void;
  onLoad?: (event: NativeSyntheticEvent<ImageLoadEventData>) => void;
  onError?: (error: NativeSyntheticEvent<ImageErrorEventData>) => void;
  onLoadEnd?: () => void;
}

export default class Image extends React.Component<ImageProps> {
  render() {
    return <ExpoImage {...this.props} />;
  }
}
