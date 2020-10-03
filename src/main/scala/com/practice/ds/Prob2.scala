package com.practice.ds

import scala.collection.immutable.Queue
import scala.collection.mutable

object Prob2 extends App {
  val que = Queue().enqueue(1).enqueue(2).enqueue(3).enqueue(4).enqueue(5)
  println(reverseQueue(que))

  def reverseQueue(que: Queue[Int]): mutable.Queue[Int] = {
    if(que.nonEmpty){
      val a = que.dequeue
      val locQue: mutable.Queue[Int] = reverseQueue(que)
      return locQue.enqueue(a)
    }
    mutable.Queue()
  }
}
