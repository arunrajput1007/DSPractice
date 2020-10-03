package com.practice.ds

import java.io.{BufferedReader, FileReader}
import java.nio.file.{Files, Paths}

object Test2 extends App {
  val br = new BufferedReader(new FileReader("hello.txt"))
  val fileSize = Files.size(Paths.get("hello.txt"))
  System.gc()
  var freeMemory = Runtime.getRuntime.freeMemory()
  if(fileSize < freeMemory) {
    val sortedValue = MergeSort.mergeSort(br.readLine.split(",").map(_.toInt))
    Files.writeString(Paths.get("output.txt"),sortedValue.mkString(","))
  } else{
    freeMemory = Runtime.getRuntime.freeMemory()
    val targetMemory = freeMemory * 0.2
    val charBuffer = Array.ofDim[Char](1024*1024)
    val accumulator: StringBuilder = new StringBuilder
    while(Runtime.getRuntime.freeMemory() > targetMemory){
      br.read(charBuffer)
      accumulator.append(charBuffer.toString.split(","))
    }
    br.read()
  }
  br.close()
}
