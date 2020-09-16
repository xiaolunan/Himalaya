package com.renchunlin.himalaya.utils;

/*
 * class title: 小于100000的不转换，大于或等于100000的转换为10万，以此类推，110000转为11万，112000为11.2万
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2020/9/14.
 * PS: Not easy to write code, please indicate.
 */
public class Wan {
    public static String numToWan(long num) {
        if (num < 100000) {
            return (int) num + "次";
        } else {
            return (double) num / 10000 + "万次";
        }
    }

    public static void main(String[] args) {
        System.out.println(numToWan(1000001));
    }
}
