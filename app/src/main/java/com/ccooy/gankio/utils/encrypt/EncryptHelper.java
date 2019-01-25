package com.ccooy.gankio.utils.encrypt;

import android.text.TextUtils;
import com.ccooy.gankio.data.cache.SaltCache;
import com.ccooy.gankio.utils.encrypt.AesCbcWithIntegrity.CipherTextIvMac;
import com.ccooy.gankio.utils.encrypt.AesCbcWithIntegrity.SecretKeys;

import javax.inject.Inject;

import static com.ccooy.gankio.utils.encrypt.AesCbcWithIntegrity.generateSalt;
import static com.ccooy.gankio.utils.encrypt.AesCbcWithIntegrity.saltString;

public class EncryptHelper {

  private static final String PASSWORD = "13101311";

  @Inject
  SaltCache mSaltCache;

  private SecretKeys mKeys;

  @Inject
  public EncryptHelper() {
  }

  public String encrypt(String plainext) {
    if(TextUtils.isEmpty(plainext)){
      return null;
    }
    try {
      if (TextUtils.isEmpty(mSaltCache.get())) {
        mSaltCache.put(saltString(generateSalt()));
      }
      if (mKeys == null) {
        mKeys = AesCbcWithIntegrity
            .generateKeyFromPassword(PASSWORD, mSaltCache.get());
      }
      return AesCbcWithIntegrity.encrypt(plainext, mKeys).toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String decrypt(String encryptedText) {
    if(TextUtils.isEmpty(encryptedText)){
      return null;
    }
    try {
      if (TextUtils.isEmpty(mSaltCache.get())) {
        mSaltCache.put(saltString(generateSalt()));
      }
      if (mKeys == null) {
        mKeys = AesCbcWithIntegrity
            .generateKeyFromPassword(PASSWORD, mSaltCache.get());
      }
      CipherTextIvMac cipherTextIvMac = new CipherTextIvMac(encryptedText);
      return AesCbcWithIntegrity.decryptString(cipherTextIvMac, mKeys);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String decrypt(String encryptedText, String salt) {
    if(TextUtils.isEmpty(encryptedText)){
      return null;
    }
    try {
      SecretKeys keys = AesCbcWithIntegrity.generateKeyFromPassword(PASSWORD, salt);
      CipherTextIvMac cipherTextIvMac = new CipherTextIvMac(encryptedText);
      return AesCbcWithIntegrity.decryptString(cipherTextIvMac, keys);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
