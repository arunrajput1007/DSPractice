package com.practice.ds

object Client extends App{
  findLyndonWords(Array(0,1,2),2).foreach(x => println(x.foreach(print)))

  def findLyndonWords(data: Array[Int], size: Int): Array[Array[Int]] =
    permutationsWithLimit(data,size).groupBy(_.sum).map(entry => toArray(entry._2.map(arr => arr.mkString("").toInt).min)).toArray

  def toArray(num: Int): Array[Int] = num match {
    case _ if num<10 => Array(num)
    case _ => toArray(num/10) ++ Array(num % 10)
  }

  def permutationsWithLimit(arr: Array[Int], limit: Int): Array[Array[Int]] = arr match {
      case _ if arr.length < 2 => Array(arr)
      case _ => limit match {
        case 1 => arr.map(Array(_))
        case _ => arr.flatMap(x => permutationsWithLimit(arr.filterNot(_==x),limit-1)
            .map(p => Array(x) ++ p))
      }
  }

  //try permutation with repeated elements
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


  }

object Practice extends App {
  val L = Array(1,  4, 3, 1)
  val R = Array(15, 8, 5, 4)
  println(maxOccurred(L,R,15))

  def maxOccurred(L: Array[Int],R: Array[Int], max: Int): Int = {

    val zippedFreq = (low: Array[Int], high: Array[Int]) => {
      val temp = new Array[Int](max + 1)
      for(i <- L.indices){
        val range = (low(i),high(i))
        temp(range._1) += 1
        temp(range._2) -= 1
      }
      temp
    }

    val getHighestOccurredInt = (zipFreqArr:Array[Int]) => {
      var maxInt: (Int,Int) = (0,0)
      for(i <- 1 until zipFreqArr.length){
        zipFreqArr(i) += zipFreqArr(i-1)
        if(maxInt._2 < zipFreqArr(i)) maxInt = (i,zipFreqArr(i))
      }
      maxInt._1
    }
    getHighestOccurredInt(zippedFreq(L,R))
  }
}
