package com.example.baseandroidproject.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.baseandroidproject.App
import com.example.baseandroidproject.R
import com.example.baseandroidproject.constants.TIME_DELAY
import com.example.baseandroidproject.utils.AppUserManager
import com.example.baseandroidproject.utils.KeyboardUtils
import com.example.baseandroidproject.utils.custom_view.ProgressBarDialog
import com.example.baseandroidproject.utils.hideStatusBar
import com.example.baseandroidproject.utils.updateLocale
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Locale

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: B
    lateinit var progressBarDialog: ProgressBarDialog

    protected abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        window.navigationBarColor = resources.getColor(R.color.app_background, null)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LOW_PROFILE
                )
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            // Hide the status bar
            hide(WindowInsetsCompat.Type.statusBars())
            // Allow showing the status bar with swiping from top to bottom
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        progressBarDialog = ProgressBarDialog(this)
        hideNavigationBarsAutomatically()
        initData()
    }

    override fun attachBaseContext(newBase: Context) {
        lifecycleScope.launch {
            async {
                val localeToSwitchTo = AppUserManager.languageApp
                App.instance.changeLang(localeToSwitchTo)
                val localeUpdatedContext: ContextWrapper =
                    updateLocale(newBase, Locale(localeToSwitchTo))
                super.attachBaseContext(localeUpdatedContext)
            }.await()
        }
    }

    override fun onStop() {
        super.onStop()
        if (progressBarDialog.isShowing) {
            progressBarDialog.dismiss()
        }
    }

    private fun hideNavigationBarsAutomatically() {
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            val isSystemUIVisible = visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0
            if (isSystemUIVisible) {
                window.decorView.postDelayed({
                    hideStatusBar()
                }, TIME_DELAY)
            }
        }
    }

    protected fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarDialog.show()
        } else {
            progressBarDialog.dismiss()
        }
    }

    protected fun isLoading(): Boolean = progressBarDialog.isShowing

    protected abstract val layoutId: Int

    fun setScreenNoLimit() {
        val flags = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        window.decorView.systemUiVisibility = flags
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            KeyboardUtils.hideSoftKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        const val PERMISSIONS_REQUEST_CODE = 123

        fun setWindowFlag(
            activity: Activity,
            bits: Int,
            on: Boolean,
        ) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }
}