import java.util.Arrays;

class Test {
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] arr1 = {
            {1, 1, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 1},
            {1, 1, 1, 1}
        };

        int[][] arr2 = {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 1, 1, 1, 1},
            {0, 1, 0, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 1, 1, 1, 1}
        };

        System.out.println("예제 1 출력 = " + Arrays.toString(sol.solution(arr1)));
        System.out.println("예제 1 정답 = [4, 9]");
        System.out.println("예제 2 출력 = " + Arrays.toString(sol.solution(arr2)));
        System.out.println("예제 2 정답 = [10, 15]");
    }
}