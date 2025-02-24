package com.chirag.sportzassignment.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.ActivitySplashMotionBinding

class SplashMotionActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashMotionBinding
    private var isShowName = false


    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_motion)

        mBinding.ivLogo.visibility = View.VISIBLE
        scaleView(mBinding.ivLogo)

    }


    private fun navigateToHomePage() {
        startActivity(
            Intent(
                this, MainActivity::class.java
            )
        )
        finish()
    }

    private fun showName() {
        isShowName = true
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            navigateToHomePage()
        }, 1000)

    }

    private fun scaleView(v: View) {
        val fadeIn = ScaleAnimation(
            0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        fadeIn.duration = 800 // animation duration in milliseconds

        fadeIn.fillAfter =
            true // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

        fadeIn.setAnimationListener(fadeInListener)

        v.startAnimation(fadeIn)
    }

    private val fadeInListener: Animation.AnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            mBinding.ivLogo.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animation?) {
            mBinding.motionLayout.transitionToEnd()

            mBinding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout, startId: Int, endId: Int
                ) {
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float
                ) {
                    if (!isShowName) {
                        showName()
                    }
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {

                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout, triggerId: Int, positive: Boolean, progress: Float
                ) {
                }
            })
        }

        override fun onAnimationRepeat(animation: Animation?) {

        }

    }
}