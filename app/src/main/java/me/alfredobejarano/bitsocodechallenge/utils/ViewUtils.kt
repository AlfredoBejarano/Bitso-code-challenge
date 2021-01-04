package me.alfredobejarano.bitsocodechallenge.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import me.alfredobejarano.bitsocodechallenge.R
import java.lang.Exception
import java.util.Locale

/**
 * Changes a view set of properties depending on the View type, a common view
 * will change its alpha value.
 * @param isLoading If the view is loading or not loading.
 *
 * @see LottieAnimationView.setLoadingState
 */
fun View.setLoadingState(isLoading: Boolean) = when (this) {
    is LottieAnimationView -> setLoadingState(isLoading)
    is SwipeRefreshLayout -> {
        isRefreshing = isLoading
        alpha = if (isLoading) 0.26f else 1f
    }
    is ViewGroup -> setLoadingState(isLoading)
    else -> alpha = if (isLoading) 0.26f else 1f
}

/**
 * Plays or pauses an animation in the given view and shows or hides the view.
 * @param isLoading If the view is loading or not loading.
 */
private fun LottieAnimationView.setLoadingState(isLoading: Boolean) = if (isLoading) {
    visibility = View.VISIBLE
    playAnimation()
} else {
    visibility = View.GONE
    pauseAnimation()
}

/**
 * Sets the loading state of individual views contained within a [ViewGroup].
 * @param isLoading If the view is loading or not loading.
 *
 * @see View.setLoadingState
 */
private fun ViewGroup.setLoadingState(isLoading: Boolean) {
    forEach { it.setLoadingState(isLoading) }
}

fun Snackbar?.showException(e: Exception) {
    val ctx = this?.view?.context
    val string = ctx?.getString(R.string.generic_error_message).orEmpty()
    this?.setText(String.format(Locale.getDefault(), string, e.javaClass.name))?.show()
}