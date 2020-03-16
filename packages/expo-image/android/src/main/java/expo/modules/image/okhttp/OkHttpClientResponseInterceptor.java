package expo.modules.image.okhttp;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Response;

public class OkHttpClientResponseInterceptor implements Interceptor {
  private Map<String, Collection<ResponseListener>> mResponseListeners;

  private OkHttpClientResponseInterceptor() {
    mResponseListeners = new HashMap<>();
  }

  private static OkHttpClientResponseInterceptor sInstance;

  public static OkHttpClientResponseInterceptor getInstance() {
    synchronized (OkHttpClientResponseInterceptor.class) {
      if (sInstance == null) {
        sInstance = new OkHttpClientResponseInterceptor();
      }
    }
    return sInstance;
  }

  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    final String requestUrl = chain.call().request().url().toString();
    Response response = chain.proceed(chain.request());

    Collection<ResponseListener> responseListeners = mResponseListeners.get(requestUrl);
    if (responseListeners != null) {
      for (ResponseListener responseListener : responseListeners) {
        responseListener.onResponse(response);
      }
    }

    return response;
  }

  public void registerResponseListener(String requestUrl, ResponseListener responseListener) {
    Collection<ResponseListener> responseListeners = mResponseListeners.get(requestUrl);
    if (responseListeners == null) {
      responseListeners = new HashSet<>();
      mResponseListeners.put(requestUrl, responseListeners);
    }
    responseListeners.add(responseListener);
  }

  public void unregisterResponseListener(String requestUrl, ResponseListener responseListener) {
    Collection<ResponseListener> responseListeners = mResponseListeners.get(requestUrl);
    if (responseListeners != null) {
      responseListeners.remove(responseListener);

      if (responseListeners.isEmpty()) {
        mResponseListeners.remove(requestUrl);
      }
    }
  }
}
