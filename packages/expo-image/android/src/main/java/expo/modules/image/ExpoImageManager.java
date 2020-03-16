package expo.modules.image;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import expo.modules.image.events.ImageErrorEvent;
import expo.modules.image.events.ImageLoadEndEvent;
import expo.modules.image.events.ImageLoadEvent;
import expo.modules.image.events.ImageLoadStartEvent;
import expo.modules.image.events.ImageProgressEvent;

public class ExpoImageManager extends SimpleViewManager<ExpoImageView> {
  private static final String REACT_CLASS = "ExpoImage";

  private RequestManager mRequestManager;

  public ExpoImageManager(ReactApplicationContext applicationContext) {
    mRequestManager = Glide.with(applicationContext);
  }

  @NonNull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  @Nullable
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.<String, Object>builder()
        .put(ImageLoadStartEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoadStart"))
        .put(ImageProgressEvent.EVENT_NAME, MapBuilder.of("registrationName", "onProgress"))
        .put(ImageErrorEvent.EVENT_NAME, MapBuilder.of("registrationName", "onError"))
        .put(ImageLoadEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoad"))
        .put(ImageLoadEndEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoadEnd"))
        .build();
  }

  // Props setters

  @ReactProp(name = "source")
  public void setSource(ExpoImageView view, @Nullable ReadableMap sourceMap) {
    view.setSource(sourceMap);
  }

  // View lifecycle

  @NonNull
  @Override
  public ExpoImageView createViewInstance(@NonNull ThemedReactContext context) {
    return new ExpoImageView(context, mRequestManager);
  }

  @Override
  protected void onAfterUpdateTransaction(@NonNull ExpoImageView view) {
    view.onAfterUpdateTransaction();
    super.onAfterUpdateTransaction(view);
  }

  @Override
  public void onDropViewInstance(@NonNull ExpoImageView view) {
    view.onDrop();
    super.onDropViewInstance(view);
  }
}
