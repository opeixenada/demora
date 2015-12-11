package ayu.demora

import java.util.concurrent.atomic.AtomicInteger
import com.twitter.finagle.util.DefaultTimer
import com.twitter.util._


class ResponseDelayTimer extends Timer {
  private val underlying = DefaultTimer.twitter
  private val tasksCounter = new AtomicInteger(0)

  def schedule(when: Time)(f: => Unit): TimerTask =
    underlying.schedule(when + newDelay)(wrapped(f))

  def schedule(when: Time, period: Duration)(f: => Unit): TimerTask =
    underlying.schedule(when + newDelay, period)(wrapped(f))

  def stop(): Unit = underlying.stop()

  private def newDelay() = Duration.fromMilliseconds(tasksCounter.getAndIncrement() / 2 * 1000)

  private def wrapped(f: => Unit) = {
    tasksCounter.decrementAndGet()
    f
  }
}
