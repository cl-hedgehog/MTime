package com.dreamzone.mtime.algorithmtest;

/**
 * @author bohe
 * @ClassName: SortComposer
 * @Description:
 * @date 2018/3/4 下午10:43
 */
public class SortComposer {


    public static void main(String args[]){
        // 1. 打印指定数字之间的所有素数
        // printPrimeNumber(100, 1);
        // 2. 计算n-m之间1出现的次数

    }

    public static void printCountOfOne(int start, int end){

    }


    public static void printPrimeNumber(int start, int end){
        // ensure start <= end
        if(start>end){
            int tmp = end;
            end = start;
            start = tmp;
        }
        for (int i = start; i < end; i++) {
            if(isPrimeNumber(i)){
                System.out.println(i + ", ");
            }
        }
    }


    public static boolean isPrimeNumber(int number){
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }


}
