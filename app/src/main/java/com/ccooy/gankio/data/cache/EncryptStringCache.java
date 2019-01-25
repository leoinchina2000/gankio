package com.ccooy.gankio.data.cache;

import android.os.Build;
import com.ccooy.gankio.utils.encrypt.EncryptHelper;
import com.ccooy.gankio.utils.encrypt.MD5;

public abstract class EncryptStringCache extends StringCache{

  private String mStr;

  public void put(String text){
    provideSP().setValue(cacheName(), provideEncryptUtil().encrypt(text));
  }

  public String get(){
    if(mStr == null){
      mStr = provideEncryptUtil().decrypt(provideSP().getStringValue(cacheName()));
    }
    return mStr;
  }

  protected String cacheName(){
    return MD5.MD5Encode(super.cacheName() + MD5.MD5Encode(Build.FINGERPRINT));
  }

  abstract EncryptHelper provideEncryptUtil();

  @Override
  public void clearCache() {
    super.clearCache();
    mStr = null;
  }
}
