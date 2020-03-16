package expo.modules.image.okhttp;

import okhttp3.Response;

public interface ResponseListener {
  void onResponse(Response response);
}
