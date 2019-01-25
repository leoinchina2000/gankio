package com.ccooy.gankio.utils.encrypt

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSharedPreferenceUtil @Inject
constructor(mContext: Context) {
    private val mSharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
    private val mSharedPreferencesEditor: SharedPreferences.Editor?

    init {
        mSharedPreferencesEditor = mSharedPreferences.edit()
    }

    /**
     * Stores String value in preference
     *
     * @param key key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: String) {
        mSharedPreferencesEditor!!.putString(key, value)
        mSharedPreferencesEditor.apply()
    }

    /**
     * Stores int value in preference
     *
     * @param key key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Int) {
        mSharedPreferencesEditor!!.putInt(key, value)
        mSharedPreferencesEditor.apply()
    }

    /**
     * Stores Double value in String format in preference
     *
     * @param key key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Double) {
        setValue(key, java.lang.Double.toString(value))
    }

    /**
     * Stores long value in preference
     *
     * @param key key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Long) {
        mSharedPreferencesEditor!!.putLong(key, value)
        mSharedPreferencesEditor.apply()
    }

    /**
     * Stores boolean value in preference
     *
     * @param key key of preference
     * @param value value for that key
     */
    fun setValue(key: String, value: Boolean) {
        mSharedPreferencesEditor!!.putBoolean(key, value)
        mSharedPreferencesEditor.apply()
    }

    /**
     * Retrieves String value from preference
     *
     * @param key key of preference
     * @param defaultValue default value if no key found
     */
    @JvmOverloads
    fun getStringValue(key: String, defaultValue: String = ""): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    /**
     * Retrieves int value from preference
     *
     * @param key key of preference
     * @param defaultValue default value if no key found
     */
    fun getIntValue(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    /**
     * Retrieves long value from preference
     *
     * @param key key of preference
     * @param defaultValue default value if no key found
     */
    fun getLongValue(key: String, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    /**
     * Retrieves boolean value from preference
     *
     * @param keyFlag key of preference
     * @param defaultValue default value if no key found
     */
    fun getBoolanValue(keyFlag: String, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    /**
     * Removes key from preference
     *
     * @param key key of preference that is to be deleted
     */
    fun removeKey(key: String) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor.remove(key)
            mSharedPreferencesEditor.apply()
        }
    }


    /**
     * Clears all the preferences stored
     */
    fun clear() {
        mSharedPreferencesEditor!!.clear().apply()
    }
}