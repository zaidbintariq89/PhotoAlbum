package com.android.photoalbum.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.photoalbum.viewmodel.BaseViewModel
import com.android.photoalbum.viewmodel.ViewModelFactory
import com.android.photoalbum.viewstate.ViewState

abstract class AppBaseActivity : AppCompatActivity() {

    private var mViewModel: BaseViewModel? = null
    private var viewModelFactory: ViewModelFactory? = null

    /**
     * @property viewModelProvider
     *
     * initialize the required view model with viewModelProvider
     */
    abstract fun initViewModel(viewModelProvider: ViewModelProvider): BaseViewModel?

    /**
     * Used for render states to view
     * @property state
     */
    abstract fun render(state: ViewState)

    /**
     * stateObserver to notify ViewModel states to View
     */
    private val stateObserver = Observer<ViewState> { state ->
        state?.let {
            render(state)
        }
    }

    /**
     * Bind XML Layout to View
     */
    abstract fun getLayoutFile(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize view
        setContentView(getLayoutFile())

        // initialize viewModel
        viewModelFactory = ViewModelFactory()
        this.mViewModel = if (mViewModel == null)
            initViewModel(ViewModelProviders.of(this, viewModelFactory))
        else mViewModel

        observeViewModel(this)
    }

    private fun observeViewModel(context: LifecycleOwner) {
        if (mViewModel != null) {
            mViewModel?.getStateLiveData()?.observe(context, stateObserver)
        }
    }
}