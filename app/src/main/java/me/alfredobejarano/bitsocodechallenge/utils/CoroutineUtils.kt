package me.alfredobejarano.bitsocodechallenge.utils

import kotlinx.coroutines.Job

/**
 * Finishes a nullable Job if it is active.
 */
fun Job?.cancelSafely() {
    if (this?.isActive == true) {
        this.cancel()
    }
}