package com.example.baseandroidproject.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseandroidproject.activity.main.MainActivity
import com.example.baseandroidproject.activity.main.MainViewModel
import com.example.baseandroidproject.common.CustomAlertDialog
import com.example.baseandroidproject.utils.custom_view.ProgressBarDialog
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

abstract class BaseFragment<B : ViewDataBinding, VM : AndroidViewModel> : Fragment() {
    protected lateinit var binding: B
        private set
    private var mainViewModel: MainViewModel? = null
    private var progressBarDialog: ProgressBarDialog? = null
    private val dialogs = ConcurrentHashMap<String, Dialog>()
    lateinit var viewModel: VM
        private set

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected fun getMainViewModel(): MainViewModel? {
        if (activity !is MainActivity) {
            throw RuntimeException("MainViewModel can only use in MainActivity")
        }
        return mainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        progressBarDialog = ProgressBarDialog(requireContext())
        if (activity is MainActivity) {
            mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        }
//        viewModel?.loadingLiveData?.observe(viewLifecycleOwner) { loading ->
//            showLoading(
//                loading != null && loading
//            )
//        }
        /** ViewModel initialize */
        val vmClass = this.javaClass.findGenericWithType<VM>(AndroidViewModel::class.java)
        if (vmClass != null) {
            viewModel = ViewModelProvider(this)[vmClass]
        } else {
            Log.e("BaseFragment", "could not find ViewModel class for $this")
        }
        initData()
    }

    private fun <CLS> Class<*>.findGenericWithType(targetClass: Class<*>): Class<out CLS>? {
        var currentType: Type? = this

        while (true) {
            val answerClass =
                (currentType as? ParameterizedType)
                    ?.actualTypeArguments // get current arguments
                    ?.mapNotNull { it as? Class<*> } // cast them to class
                    ?.findLast { targetClass.isAssignableFrom(it) } // check if it is a target (ViewModel for example)

            // We found a target (ViewModel)
            if (answerClass != null) {
                @Suppress("UNCHECKED_CAST")
                return answerClass as Class<out CLS>?
            }

            currentType =
                when {
                    currentType is Class<*> -> currentType.genericSuperclass // Not a ParameterizedType so go to parent
                    currentType is ParameterizedType -> currentType.rawType // a parameterized type which we could't find any ViewModel yet, so the raw type (parent) should have it
                    else -> return null // or throw an exception
                }
        }
    }

    override fun onStop() {
        super.onStop()
        if (progressBarDialog != null && progressBarDialog!!.isShowing) {
            progressBarDialog!!.dismiss()
        }
        val keyList = ArrayList<String>()
        for (key in dialogs.keys) {
            val dialog = dialogs[key]
            if (dialog != null && dialog.isShowing) {
                dialog.dismiss()
                keyList.add(key)
            }
        }
        for (key in keyList) {
            dialogs.remove(key)
        }
    }

    // Listen status of live when click notification live
    protected abstract fun initData()

    protected fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarDialog!!.show()
        } else {
            progressBarDialog!!.dismiss()
        }
    }

    /**
     * return true if consume back pressed event
     */
    fun onBackPressed(): Boolean = false

    /**
     * Show message dialog with one button
     *
     * @param title
     * @param message
     * @param buttonText
     */
    protected fun showMessage(
        title: String?,
        message: String,
        buttonText: Int,
        clickListener: DialogInterface.OnClickListener?,
    ) {
        val old = dialogs[message]
        if (old != null && old.isShowing) {
            old.dismiss()
            dialogs.remove(message)
        }
        if (activity != null) {
            val builder: CustomAlertDialog.Builder =
                CustomAlertDialog.Builder(requireActivity())
            if (!TextUtils.isEmpty(title)) {
                if (title != null) {
                    builder.setTitle(title)
                }
            }
            val dialog: AlertDialog =
                builder
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(buttonText, clickListener)
                    .create()
            dialog.setOnDismissListener { dialog1: DialogInterface? ->
                dialogs.remove(
                    message,
                )
            }
            dialogs[message] = dialog
            dialog.show()
        }
    }

    protected fun pushFragment(
        targetFragment: Fragment,
        addToBackStack: Boolean = true,
    ) {
//        requireActivity()
//            .supportFragmentManager
//            .beginTransaction()
//            .also {
//                it.replace(R.id.fragmentContainer, targetFragment)
//                if (addToBackStack) {
//                    it.addToBackStack(targetFragment::class.java.simpleName)
//                }
//            }.commit()
    }

    protected fun popBackStackUntil(backStackName: String) {
        val fm =
            requireActivity()
                .supportFragmentManager
        for (i in fm.backStackEntryCount - 1 downTo 1) {
            if (!fm.getBackStackEntryAt(i).name.equals(backStackName, ignoreCase = true)) {
                fm.popBackStack()
            } else {
                break
            }
        }
    }

    protected fun popAllFragment() {
        requireActivity()
            .supportFragmentManager
            .popBackStack()
    }

    protected fun popEntireFragments() {
        val fm =
            requireActivity()
                .supportFragmentManager

        for (i in fm.backStackEntryCount - 1 downTo 1) {
            if (fm.backStackEntryCount > 1) {
                fm.popBackStack()
            } else {
                break
            }
        }
    }
}