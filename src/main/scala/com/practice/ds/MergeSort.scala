package com.practice.ds

object MergeSort{

  def mergeSort(arr: Array[Int]): Array[Int] = arr.length match {
    case 1 => arr
    case 2 =>
      if(arr(0) < arr(1)) arr
      else arr.reverse
    case _ =>
      val halfSplit = arr.splitAt(arr.length/2)
      val arr1 = mergeSort(halfSplit._1)
      val arr2 = mergeSort(halfSplit._2)
      merge(arr1,arr2)
  }

  def merge(arr1: Array[Int], arr2: Array[Int]): Array[Int] = {
    val res = Array.ofDim[Int](arr1.length+arr2.length)
    var l,r,k = 0
    while(k!=res.length){
      if(r == arr2.length){
        res(k) = arr1(l)
        l += 1
      } else if(l == arr1.length){
        res(k) = arr2(r)
        r+=1
      } else if(arr1(l) < arr2(r)){
        res(k) = arr1(l)
        l += 1
      } else {
        res(k) = arr2(r)
        r+=1
      }
      k+=1
    }
    res
  }
}
