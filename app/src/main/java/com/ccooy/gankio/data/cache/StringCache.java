package com.ccooy.gankio.data.cache;

import android.os.Build;
import android.text.TextUtils;
import com.ccooy.gankio.utils.encrypt.DefaultSharedPreferenceUtil;
import com.ccooy.gankio.utils.encrypt.MD5;

public abstract class StringCache {

  public void put(String text){
    provideSP().setValue(cacheName(), text);
  }

  public String get(){
    return provideSP().getStringValue(cacheName());
  }

  protected String cacheName(){
    return MD5.MD5Encode(getClass().getSimpleName() + Build.FINGERPRINT);
  }

  public boolean isCacheValid(){
    return !TextUtils.isEmpty(provideSP().getStringValue(cacheName()));
  }

  public void clearCache(){
    provideSP().removeKey(cacheName());
  }

  abstract DefaultSharedPreferenceUtil provideSP();
}
