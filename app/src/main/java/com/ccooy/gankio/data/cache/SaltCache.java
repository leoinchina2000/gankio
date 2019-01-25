package com.ccooy.gankio.data.cache;

import com.ccooy.gankio.utils.encrypt.DefaultSharedPreferenceUtil;

import javax.inject.Inject;

public class SaltCache extends StringCache {

  @Inject
  DefaultSharedPreferenceUtil mDefaultSharedPreferenceUtil;

  @Inject
  public SaltCache() {
  }

  @Override
  DefaultSharedPreferenceUtil provideSP() {
    return mDefaultSharedPreferenceUtil;
  }
}
