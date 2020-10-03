package com.practice.ds

import java.nio.file.{Files, Paths}

import scala.collection.immutable.ListMap

object Office extends App{
  val contents = Files.readString(Paths.get("sample.txt"))
  val arr = contents.split("\n")

  val paragraph = arr(0)
  val questions = 1 until arr.size-1 map arr
  val answers = arr(arr.size-1).split(";").map(_.trim)
  val commonWords = Array("which", "are", "what", "is", "the", "of", "some", "their", "do", "and", "to")

  val keyWords = questions(1).split(" ").map(_.toLowerCase.replace("?","").trim)
    .filterNot(commonWords.contains)

  val freqs = for{
    keyword <- keyWords;
    sentence <- paragraph.split("\\.").map(_.toLowerCase)
    if sentence.contains(keyword)
  } yield{
    (keyword, sentence, sentence.split(" ").map(_.toLowerCase).count(_.equalsIgnoreCase(keyword)))
  }

  val map = freqs.groupBy(_._1).map(tup => (tup._1, tup._2.length))
  val sorted = ListMap(map.toSeq.sortBy(_._2):_*)
  val foundSentence = freqs.filter(_._1 == sorted.head._1)

  val ans = for{
    answer <- answers
    found = foundSentence(0)._2.trim.toLowerCase
    if found.indexOf(answer.toLowerCase) != -1
  } yield answer
  println(ans.head)
}