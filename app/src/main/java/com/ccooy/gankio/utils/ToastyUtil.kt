package com.ccooy.gankio.utils

import android.widget.Toast

import es.dmoral.toasty.Toasty

/**
 * Author:
 *
 * Date: 2017-04-24  15:33
 */

object ToastyUtil {

    fun showError(msg: String) {
        Toasty.error(Utils.getContext(), msg, Toast.LENGTH_SHORT, true).show()
    }

    fun showSuccess(msg: String) {
        Toasty.success(Utils.getContext(), msg, Toast.LENGTH_SHORT, true).show()
    }
}
