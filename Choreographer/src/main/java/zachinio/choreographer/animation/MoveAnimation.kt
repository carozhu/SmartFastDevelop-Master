package zachinio.choreographer.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import io.reactivex.Completable
import java.lang.ref.WeakReference

class MoveAnimation(
    view: View,
    private val x: Int,
    private val y: Int,
    private val duration: Long
) : Animation() {

    private val viewWeak = WeakReference(view)

    override fun animate(): Completable {
        return Completable.create {
            val objectAnimatorX = ObjectAnimator.ofFloat(viewWeak.get(), "x", x.toFloat())
            val objectAnimatorY = ObjectAnimator.ofFloat(viewWeak.get(), "y", y.toFloat())
            val animatorSet = AnimatorSet()
            animatorSet.duration = duration
            animatorSet.removeAllListeners()
            animatorSet.playTogether(objectAnimatorX, objectAnimatorY)
            animatorSet.addListener(object : AnimationListener(){
                override fun onAnimationEnd(p0: Animator?) {
                    it.onComplete()
                }
            })
            animatorSet.start()
        }
    }
}