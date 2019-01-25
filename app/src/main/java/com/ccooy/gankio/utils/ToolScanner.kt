package com.ccooy.gankio.utils

import android.os.Handler
import android.view.InputDevice
import android.view.KeyEvent


class ToolScanner(private var mOnScanSuccessListener: OnScanSuccessListener?) {

    val salt =
        "m8watfZXDQcNAwDtXCCh4/f61AhZaLudxgroatIy/CiSa49KOmh8QA4fUlQ4DSKxVBVLDqTXdIvVSVdNgU6bROCp/R+199msvNEHiUblYEJfe8rNYmsAMjpbXN5sWXi7TSyDSXaco+4a6j8yGZ5tPh98F6oej2aAnqyiwt4ywA0="
    val deploySign = "deploy://"

    //扫码内容
    private val mStringBufferResult: StringBuffer = StringBuffer()

    //大小写区分
    private var mCaps: Boolean = false

    private var isDeploy: Boolean = false

    private val mHandler: Handler = Handler()
    //private final BluetoothAdapter mBluetoothAdapter;
    private val mScanningFishedRunnable: Runnable
    private val mScanningDeployFishedRunnable: Runnable
    private val mScanningStartRunnable: Runnable
    private val mScanningDeployRunnable: Runnable
    private val mDeviceName: String? = null

    init {
        mScanningFishedRunnable = Runnable { performScanSuccess() }

        mScanningDeployFishedRunnable = Runnable { performDeploySuccess() }

        mScanningStartRunnable = Runnable { performScanStart() }

        mScanningDeployRunnable = Runnable { performScanDeploy() }
    }

    /**
     * 返回扫部署码成功后的结果
     */
    private fun performDeploySuccess() {
        val barcode = mStringBufferResult.toString()
        if (mOnScanSuccessListener != null)
            mOnScanSuccessListener!!.onScanDeploySuccess(barcode.substring(deploySign.length))
        mStringBufferResult.setLength(0)
        isDeploy = false
    }

    /**
     * 返回扫码成功后的结果
     */
    private fun performScanSuccess() {
        val barcode = mStringBufferResult.toString()
        if (mOnScanSuccessListener != null)
            mOnScanSuccessListener!!.onScanSuccess(barcode)
        mStringBufferResult.setLength(0)
        isDeploy = false
    }


    /**
     * 返回扫码开始
     */
    private fun performScanStart() {
        if (mOnScanSuccessListener != null)
            mOnScanSuccessListener!!.onScanStart()
    }


    /**
     * 返回识别到部署码
     */
    private fun performScanDeploy() {
        if (mOnScanSuccessListener != null)
            mOnScanSuccessListener!!.onScanDeploy()
        isDeploy = true
    }


    /**
     * 扫码枪事件解析
     * @param event
     */
    fun analysisKeyEvent(event: KeyEvent) {
        mHandler.removeCallbacks(mScanningStartRunnable)
        mHandler.post(mScanningStartRunnable)
        val keyCode = event.keyCode


        //字母大小写判断
        checkLetterStatus(event)

        if (event.action == KeyEvent.ACTION_DOWN) {

            val aChar = getInputCode(event)

            if (aChar.toInt() != 0) {
                mStringBufferResult.append(aChar)
                if (mStringBufferResult.toString().length == deploySign.length && deploySign.equals(mStringBufferResult.toString())) {
                    mHandler.removeCallbacks(mScanningDeployRunnable)
                    mHandler.post(mScanningDeployRunnable)
                }
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //若为回车键，直接返回
                //                mStringBufferResult.append("&");
                mHandler.removeCallbacks(mScanningDeployFishedRunnable)
                mHandler.removeCallbacks(mScanningFishedRunnable)
                if (isDeploy) {
                    mHandler.postDelayed(mScanningDeployFishedRunnable, ENTER_MESSAGE_DELAY)
                } else {
                    mHandler.postDelayed(mScanningFishedRunnable, ENTER_MESSAGE_DELAY)
                }
            } else {
                mHandler.removeCallbacks(mScanningFishedRunnable)
                mHandler.removeCallbacks(mScanningDeployFishedRunnable)
                //延迟post，若500ms内，有其他事件
                if (isDeploy) {
                    mHandler.postDelayed(mScanningDeployFishedRunnable, MESSAGE_DELAY)
                } else {
                    mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY)
                }
            }

        }
    }

    /**
     * shift键检查
     * @param event
     */
    private fun checkLetterStatus(event: KeyEvent) {
        val keyCode = event.keyCode
        if (keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            //按着shift键，表示大写，松开shift键，表示小写
            mCaps = (event.action == KeyEvent.ACTION_DOWN)
        }
    }


    //获取扫描内容
    private fun getInputCode(event: KeyEvent): Char {

        val keyCode = event.keyCode

        val aChar: Char

        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            //字母
            aChar = ((if (mCaps) 'A' else 'a').toInt() + keyCode - KeyEvent.KEYCODE_A).toChar()
        } else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            //数字
            if (mCaps) {
                aChar = when (keyCode) {
                    KeyEvent.KEYCODE_1 -> '!'
                    KeyEvent.KEYCODE_2 -> '@'
                    KeyEvent.KEYCODE_3 -> '#'
                    KeyEvent.KEYCODE_4 -> '$'
                    KeyEvent.KEYCODE_5 -> '%'
                    KeyEvent.KEYCODE_6 -> '^'
                    KeyEvent.KEYCODE_7 -> '&'
                    KeyEvent.KEYCODE_8 -> '*'
                    KeyEvent.KEYCODE_9 -> '('
                    KeyEvent.KEYCODE_0 -> ')'
                    else -> 0.toChar()
                }
            } else {
                aChar = ('0'.toInt() + keyCode - KeyEvent.KEYCODE_0).toChar()
            }
        } else {
            //其他符号
            aChar = when (keyCode) {
                KeyEvent.KEYCODE_COMMA -> if (mCaps) '<' else ','
                KeyEvent.KEYCODE_PERIOD -> if (mCaps) '>' else '.'
                KeyEvent.KEYCODE_MINUS -> if (mCaps) '_' else '-'
                KeyEvent.KEYCODE_SLASH -> if (mCaps) '?' else '/'
                KeyEvent.KEYCODE_EQUALS -> if (mCaps) '+' else '='
                KeyEvent.KEYCODE_BACKSLASH -> if (mCaps) '|' else '\\'
                KeyEvent.KEYCODE_SEMICOLON -> if (mCaps) ':' else ';'
                KeyEvent.KEYCODE_APOSTROPHE -> if (mCaps) '\"' else '\''
                KeyEvent.KEYCODE_LEFT_BRACKET -> if (mCaps) '{' else '['
                KeyEvent.KEYCODE_RIGHT_BRACKET -> if (mCaps) '}' else ']'
                KeyEvent.KEYCODE_GRAVE -> if (mCaps) '~' else '`'
                else -> 0.toChar()
            }
        }

        return aChar
    }

    interface OnScanSuccessListener {
        fun onScanSuccess(barcode: String)
        fun onScanStart()
        fun onScanDeploySuccess(barcode: String)
        fun onScanDeploy()
    }


    fun onDestroy() {
        mHandler.removeCallbacks(mScanningDeployFishedRunnable)
        mHandler.removeCallbacks(mScanningFishedRunnable)
        mHandler.removeCallbacks(mScanningStartRunnable)
        mOnScanSuccessListener = null
    }


    //部分手机如三星，无法使用该方法
    //    private void hasScanGun() {
    //        Configuration cfg = getResources().getConfiguration();
    //        return cfg.keyboard != Configuration.KEYBOARD_NOKEYS;
    //    }


    /**
     * 输入设备是否存在
     * @param deviceName
     * @return
     */
    @Deprecated("")
    private fun isInputDeviceExist(deviceName: String): Boolean {
        val deviceIds = InputDevice.getDeviceIds()

        for (id in deviceIds) {
            if (InputDevice.getDevice(id).name == deviceName) {
                return true
            }
        }
        return false
    }


    /**
     * 是否为扫码枪事件(部分机型KeyEvent获取的名字错误)
     * @param event
     * @return
     */
    @Deprecated("")
    fun isScanGunEvent(event: KeyEvent): Boolean {
        return event.device.name == mDeviceName
    }

    companion object {
        private const val MESSAGE_DELAY: Long = 500             //延迟500ms，判断扫码是否完成。
        private const val ENTER_MESSAGE_DELAY: Long = 0             //延迟500ms，判断扫码是否完成。
    }
}
