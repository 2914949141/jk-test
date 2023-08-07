package com.du.controller;

import java.util.*;

public class Test {
    static Map<Character, int[]> map = new HashMap<>();
    static Deque<int[]> body = new LinkedList<>();
    static List<int[]> appList = new LinkedList<>();
    static int len = 1;

    public static void main(String[] args) {
        map.put('W', new int[]{0, 1});
        map.put('S', new int[]{0, -1});
        map.put('D', new int[]{1, 0});
        map.put('A', new int[]{-1, 0});

        body.add(new int[]{0, 0});

//        appList.add(new int[]{0, 1});
//        appList.add(new int[]{-1, 1});
//        appList.add(new int[]{-1, 2});
//        appList.add(new int[]{2, 0});
//        appList.add(new int[]{1, 1});

        appList.add(new int[]{2, 0});
        appList.add(new int[]{-1, 1});
        appList.add(new int[]{1, -1});

        System.out.println(f("WASDDSAASD"));
    }


    static int f(String stepStr) {
        char[] steps = stepStr.toCharArray();
        for (char step : steps) {
            int[] ints = map.get(step);
            int[] peek = body.peek();
            int x = peek[0] + ints[0], y = peek[1] + ints[1];
            if (contain(body, new int[]{x, y})) {
                return len;
            } else if (contain(appList, new int[]{x, y})) {
                len++;
                body.addFirst(new int[]{x, y});
            } else {
                body.addFirst(new int[]{x, y} );
                body.pollLast();
            }
        }
        return len;
    }

    static boolean contain(List<int[]> arr, int[] dept) {
        for (int[] peek : arr) {
            if (peek[0] == dept[0] && peek[1] == dept[1]) {
                arr.remove(peek);
                return true;
            }
        }
        return false;
    }

    static boolean contain(Deque<int[]> arr, int[] dept) {
        if (dept[0] == arr.getLast()[0] && dept[1] == arr.getLast()[1])
            return false;
        for (int[] peek : arr) {
            if (peek[0] == dept[0] && peek[1] == dept[1])
                return true;
        }

        return false;
    }
}
