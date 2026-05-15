// 22313526 장지웅

import java.util.Scanner;

public class HW2 {
    static int n, k;
    static int[] pick;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("정수 n과 k를 입력? ");
        n = sc.nextInt();
        k = sc.nextInt();
        pick = new int[k];
        combine(1, 0);
        System.out.println();
        sc.close();
    }

    static void combine(int start, int depth) {
        if (depth == k) {
            System.out.print("[");
            for (int i = 0; i < k; i++) {
                System.out.print(pick[i]);
                if (i < k - 1) System.out.print(", ");
            }
            System.out.print("] ");
            return;
        }
        // 남은 자리(k-depth)를 채울 수 있을 만큼만 반복 (가지치기)
        for (int i = start; i <= n - (k - depth) + 1; i++) {
            pick[depth] = i;
            combine(i + 1, depth + 1);
        }
    }
}
