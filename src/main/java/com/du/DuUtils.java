//package com.du;
//
//import java.io.UnsupportedEncodingException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class DuUtils {
//
//    public static int calculateMinSwordSlash(int[] arr) {
//        int n = arr.length;
//
//        int min = Integer.MAX_VALUE;
//        int minIndex = -1;
//        int minNei = Integer.MAX_VALUE;
//        Character flag = 'L';
//        for (int i = 0; i < n; i++) {
//            int x = arr[i];
//            if (i == 0)
//                x = arr[i];
//            else
//                x = (int) Math.ceil(arr[i] / 2.0) ;
//
//            int left = Integer.MAX_VALUE, right = Integer.MAX_VALUE, curNei = -1;
//            if (i != 0)
//                left = arr[i-1];
//            if (i != n-1)
//                right = (int) Math.ceil(arr[i + 1] / 2.0);
//
//            if (left <= right) {
//                curNei = left;
//            } else {
//                curNei = right;
//            }
//
//
//            if (x < min) {
//                if (left <= right) {
//                    curNei = left;
//                    flag = 'L';
//                } else {
//                    curNei = right;
//                    flag = 'R';
//                }
//                curNei =
//            }
//        }
//        int cha = Math.min(arr[minIndex], (int) Math.ceil(arr[minIndex+1] / 2.0));
//        arr[minIndex] = 0; arr[minIndex + 1] -= cha * 2;
//
//        int sum = 1;
//        if (arr[minIndex + 1] <= 0) sum++;
//        if (sum >= 2) return cha;
//
//
//
//
//
//        min = Integer.MAX_VALUE;
//        minIndex = -1;
//        for (int i = 0; i < n; i++) {
//            int x = arr[i];
//            if (i == 0) {
//                x = arr[i];
//            } else {
//                x = (int) Math.ceil(arr[i] / 2.0) ;
//            }
//            if (x < min && x != 0) {
//                min = x;
//                if (i != 0) {
//                    minIndex = i - 1;
//                } else {
//                    minIndex = i;
//                }
//            }
//        }
//        int cha2 = arr[minIndex] == 0 ? (int) Math.ceil(arr[minIndex+1] / 2.0) : arr[minIndex];
//        arr[minIndex] = 0; arr[minIndex + 1] -= cha2 * 2;
//        return cha + cha2;
//
//    }
//
//    public static void main(String[] args) {
//        int[] monsterHealth = {5,10,1,6,3,2,3,3};
//        int minSwordSlash = calculateMinSwordSlash(monsterHealth);
//
//        System.out.println("Minimum sword slash required: " + minSwordSlash);
//    }
//}