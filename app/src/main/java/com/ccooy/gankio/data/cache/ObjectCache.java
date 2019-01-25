package com.ccooy.gankio.data.cache;

import android.os.Build;
import android.text.TextUtils;
import com.ccooy.gankio.utils.encrypt.DefaultSharedPreferenceUtil;
import com.ccooy.gankio.utils.encrypt.MD5;
import com.ccooy.gankio.utils.encrypt.Serializer;

import java.lang.reflect.ParameterizedType;

public abstract class ObjectCache<T>{

  public void putObject(T t) {
    provideSP().setValue(cacheName(), provideSerializer().serialize(t));
  }

  public T getObject(){
    return provideSerializer().deserialize(provideSP().getStringValue(cacheName()), provideClass());
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

  abstract Serializer provideSerializer();

  abstract DefaultSharedPreferenceUtil provideSP();

  Class<T> provideClass(){
    Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
    return tClass;
  }
}
