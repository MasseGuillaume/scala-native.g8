import scalanative.native._

object posixh {
  type time_t      = CLong
  type suseconds_t = CLong
  type timeval     = CStruct2[time_t, suseconds_t]
  type timezone    = CStruct0

  implicit class timevalOps(val ptr: Ptr[timeval]) extends AnyVal {
    def sec: time_t       = !(ptr._1)
    def usec: suseconds_t = !(ptr._2)
  }
}

@extern
object posix {
  import posixh._
  def gettimeofday(tv: Ptr[timeval], tz: Ptr[timezone]): Unit = extern
}

import posix._, posixh._

object Main {
  def main(args: Array[String]): Unit = {
    val tv = stackalloc[timeval]
    gettimeofday(tv, null)

    println("Hello, world!")
    println("Time: " + tv.sec)
  } 
}