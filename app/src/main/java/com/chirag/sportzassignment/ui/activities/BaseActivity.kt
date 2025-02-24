package com.chirag.sportzassignment.ui.activities

import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.chirag.sportzassignment.R

abstract class BaseActivity : AppCompatActivity {
    private var shouldPerformDispatchTouch = true

    constructor()

    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        var ret = false
        return try {
            val view = currentFocus
            ret = super.dispatchTouchEvent(event)
            if (shouldPerformDispatchTouch) {
                if (view is EditText) {
                    val w = currentFocus
                    val scrCords = IntArray(2)
                    if (w != null) {
                        w.getLocationOnScreen(scrCords)
                        val x = event.rawX + w.left - scrCords[0]
                        val y = event.rawY + w.top - scrCords[1]
                        if (event.action == MotionEvent.ACTION_UP
                            && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
                        ) {
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        }
                    }
                }
            }
            ret
        } catch (e: Exception) {
            ret
        }
    }
}