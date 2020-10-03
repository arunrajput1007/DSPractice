package com.practice.ds;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws IOException {
        /*Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<1024*50;i++){
            for(int j=0;j<1024;j++){
                stringBuilder.append(random.nextInt(Integer.MAX_VALUE)).append(",");
            }
        }
        Files.writeString(Paths.get("hello.txt"),stringBuilder.toString());*/

        long availMemory = Runtime.getRuntime().freeMemory();
        long fileSize = Files.size(Paths.get("hello.txt"));
        //splitter
        try (BufferedReader br = new BufferedReader(new FileReader("hello.txt"))){
            if(fileSize < availMemory){
                int[] arr = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
                MergeSort.mergeSort(arr);
                Files.writeString(Paths.get("output.txt"), Arrays.stream(arr)
                        .mapToObj(Integer::toString).collect(Collectors.joining(",")));
            } else{
                //char[] charBuffer = new char[1024*1024*10];
                int counter = 53;
                /*String numBuffer = "";
                while(br.read(charBuffer) != -1){
                    String input = numBuffer + new String(charBuffer);
                    int index = input.lastIndexOf(",");
                    numBuffer = input.substring(index+1);
                    int[] res = MergeSort.mergeSort(Arrays.stream(input.split(","))
                            .mapToInt(Integer::parseInt).toArray());
                    Files.writeString(Paths.get("output"+counter+".txt"),
                            Arrays.stream(res).boxed().map(Object::toString).collect(Collectors.joining(",")));
                    counter++;
                }*/
                mergeFiles(counter,"output");
            }
        }
    }

    private static List<Integer> mergeSort(List<Integer> list) {
        return compare(list.subList(0, list.size() / 2), list.subList(list.size() / 2, list.size()));
    }

    private static List<Integer> compare(List<Integer> list1, List<Integer> list2) {
        if (list1.size() > 1 || list2.size() > 1) {
            List<Integer> mer1 = compare(list1.subList(0, list1.size() / 2), list2.subList(0, list2.size() / 2));
            List<Integer> mer2 = compare(list1.subList(list1.size() / 2, list1.size()), list2.subList(list2.size() / 2, list2.size()));
            List<Integer> res = new ArrayList<>(mer1.size()+mer2.size());
            int counter1 = 0;
            int counter2 = 0;
            while(counter1!=mer1.size() && counter2!=mer2.size()){
                int a = mer1.get(counter1);
                int b = mer2.get(counter2);
                if(a<b) {
                    res.add(a);
                    counter1++;
                }else{
                    res.add(b);
                    counter2++;
                }
                if(counter2 == mer2.size() && counter1<mer1.size()){
                    res.add(a);
                    counter1++;
                } else if(counter1 == mer1.size() && counter2<mer2.size()){
                    res.add(b);
                    counter2++;
                }
            }
            return res;
        } else if (list1.size() == 1 && list2.size() == 1) {
            if (list1.get(0) < list2.get(0))
                return Arrays.stream(new int[]{list1.get(0), list2.get(0)}).boxed().collect(Collectors.toList());
            else
                return new ArrayList<>(Arrays.asList(list2.get(0), list1.get(0)));
        }
        return null;
    }

    private static void mergeFiles(int count,String filePrefix){
        char[] charBuffer1 = new char[1024*1024*10];
        char[] charBuffer2 = new char[1024*1024*10];
        int mergeCounter = 1;
        for(int i=1;i<count;i+=2){
            try(
                    BufferedReader br1 = new BufferedReader(new FileReader(filePrefix+i+".txt"));
                    BufferedReader br2 = new BufferedReader(new FileReader(filePrefix+(i+1)+".txt"));
                    BufferedWriter brw = new BufferedWriter(new FileWriter(filePrefix+i+".txt"))
            ) {
                while(true){
                    int res1 = br1.read(charBuffer1);
                    int res2 = br1.read(charBuffer2);
                    Files.delete(Paths.get(filePrefix+i+".txt"));
                    Files.delete(Paths.get(filePrefix+(i+1)+".txt"));
                    if(res1 == -1 && res2 == -1){
                        int[] arr1 = Arrays.stream(new String(charBuffer1).split(",")).mapToInt(Integer::parseInt).toArray();
                        int[] arr2 = Arrays.stream(new String(charBuffer2).split(",")).mapToInt(Integer::parseInt).toArray();
                        String res = Arrays.stream(MergeSort.merge(arr1,arr2)).mapToObj(Integer::toString).collect(Collectors.joining(","));
                        Files.writeString(Paths.get(filePrefix+mergeCounter+".txt"), res);
                        mergeCounter++;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(mergeCounter > 1)
            mergeFiles(mergeCounter,filePrefix+"0");
    }
}
