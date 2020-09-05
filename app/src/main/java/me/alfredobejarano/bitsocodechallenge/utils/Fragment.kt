package me.alfredobejarano.bitsocodechallenge.utils

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewBinding(initialize: (inflater: LayoutInflater) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        init {
            this@viewBinding
                .viewLifecycleOwnerLiveData
                .observe(this@viewBinding, { owner: LifecycleOwner ->
                    owner.lifecycle.addObserver(this)
                })
        }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            return this.binding
                ?: if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED)
                    error("Called before onCreateView or after onDestroyView.")
                else initialize(LayoutInflater.from(requireContext())).also {
                    this.binding = it
                }
        }
    }

fun <T> Fragment.observe(liveData: LiveData<T>, onChanged: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer {
        it?.run(onChanged) ?: EventManager.reportLoading(false)
    })
}