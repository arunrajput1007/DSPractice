package com.practice.ds

object Problem2 extends App {
  //repeated permiutation
  def permutations(list: List[Int]): List[List[Int]] = {
    if (list.size == 1) List(list)
    else for {
      x <- list
      y <- permutations(list.filterNot(_ == x))
    } yield x :: y
  }

  def zxw(arr: Array[Int], limit: Int): Array[Array[Int]] = arr match {
    case _ if arr.length < 2 => Array(arr)
    case _ => limit match {
      case 1 => arr.map(Array(_))
      case _ => arr.flatMap(x => zxw(arr, limit - 1)
        .map(p => Array(x) ++ p))
    }
  }

  def rePermutation(list: List[Int], limit: Int): List[List[Int]] = list match {
    case _ if list.size < 2 => List(list)
    case _ => limit match {
      case 1 => list.map(List(_))
      case _ => for{
        x <- list
        y <- rePermutation(list,limit-1)
      } yield x :: y
    }
  }

  /*val dui = zxw(Array(1, 2, 3), 3).map(_.toList).toList
  println(dui)*/
  val dus = rePermutation(List(1, 2), 3)
  println(dus)
}
