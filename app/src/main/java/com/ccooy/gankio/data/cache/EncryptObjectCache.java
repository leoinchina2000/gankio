package com.ccooy.gankio.data.cache;

import com.ccooy.gankio.utils.encrypt.EncryptHelper;

public abstract class EncryptObjectCache<T> extends ObjectCache<T>{

  private T mObj;

  public void putObject(T t) {
    provideSP().setValue(cacheName(), provideEncryptUtil().encrypt(provideSerializer().serialize(t)));
  }

  public T getObject(){
    if(mObj == null){
      mObj = provideSerializer()
          .deserialize(provideEncryptUtil().decrypt(provideSP().getStringValue(cacheName())), provideClass());
    }
    return mObj;
  }

  abstract EncryptHelper provideEncryptUtil();

  @Override
  public void clearCache() {
    super.clearCache();
    mObj = null;
  }
}
