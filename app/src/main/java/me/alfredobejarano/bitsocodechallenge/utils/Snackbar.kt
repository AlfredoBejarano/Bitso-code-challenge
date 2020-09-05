package me.alfredobejarano.bitsocodechallenge.utils

import com.google.android.material.snackbar.Snackbar

/**
 * Snackbar
 */

fun Snackbar?.showSafely() {
    if (this?.isShown == false) {
        show()
    }
}