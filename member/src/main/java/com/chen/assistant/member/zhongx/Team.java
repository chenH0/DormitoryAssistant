package com.chen.assistant.member.zhongx;

import java.util.Arrays;
import java.util.Scanner;

public class Team {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();
        String input = scanner.nextLine();
        int len = input.length();
        if(n<1||n>len||m<1||m>len){
            System.out.println("参数错误");
            return;
        }
        if(len==2){
            System.out.println(input);
            return;
        }
        String newInput = input.substring(1, len-1);
        String[] teamOrder = newInput.split(",");

        reverseArray(teamOrder, 0, n - 1);
        int nChu3 = n / 3 + (n % 3 != 0 ? 1 : 0);

        //int nChu3 = (int) Math.ceil(n / 3);
        reverseArray(teamOrder, 0, nChu3 - 1);

        // 反转后m个队员
        reverseArray(teamOrder, teamOrder.length - m, teamOrder.length - 1);

        // 反转后m/3个队员（向上取整）
        int mChu3 = m / 3 + (m % 3 != 0 ? 1 : 0);
        reverseArray(teamOrder, teamOrder.length - mChu3, teamOrder.length - 1);

        // 输出最终队列编号
        System.out.println(Arrays.toString(teamOrder));
    }

    private static void reverseArray(String[] arr, int start, int end) {
        while (start < end) {
            String tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
            start++;
            end--;
        }
    }
}
