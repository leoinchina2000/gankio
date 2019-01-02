package com.ccooy.gankio.utils

import android.widget.Toast

import es.dmoral.toasty.Toasty

object ToastyUtil {

    fun showError(msg: String) {
        Toasty.error(Utils.getContext(), msg, Toast.LENGTH_SHORT, true).show()
    }

    fun showSuccess(msg: String) {
        Toasty.success(Utils.getContext(), msg, Toast.LENGTH_SHORT, true).show()
    }
}
