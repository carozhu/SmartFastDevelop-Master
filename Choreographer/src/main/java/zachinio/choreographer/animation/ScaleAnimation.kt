package zachinio.choreographer.animation

import android.animation.Animator
import android.view.View
import io.reactivex.Completable
import java.lang.ref.WeakReference

class ScaleAnimation(
    view: View,
    private val xScale: Float,
    private val yScale: Float,
    private val duration: Long
) : Animation() {

    private var viewWeak: WeakReference<View> = WeakReference(view)

    override fun animate(): Completable {
        return Completable.create {
            viewWeak.get()
                ?.animate()
                ?.setDuration(duration)
                ?.scaleX(xScale)
                ?.scaleY(yScale)
                ?.setListener(object : AnimationListener(){
                    override fun onAnimationEnd(p0: Animator?) {
                        it.onComplete()
                    }
                })
        }
    }
}