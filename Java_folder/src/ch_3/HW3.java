// 22313526 장지웅

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class HW3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] A = new int[n];
        for (int i = 0; i<n; i++)
            A[i] = sc.nextInt();
        sc.close();

        // 배열 정렬용 배열 생성
        int[] sortedA = A.clone();
        Arrays.sort(sortedA); // 나중에는 원본 순으로 출력해야 하므로 A는 놔두고 복사본 정렬
        
        // 각 숫자의 순위 기록할 HashMap 생성
        HashMap<Integer, Integer> score = new HashMap<>();
        int rank = 0; // 가장 작은 숫자가 0등(1등)

        // 정렬된 배열을 돌며 HashMap에 숫자와 등수 기록
        for (int i=0; i<sortedA.length; i++) {
            int num = sortedA[i];

            // 중복된 숫자는 같은 등수를 가져야하니 처음 등장 했을 때 등수 부여
            if (!score.containsKey(num)) {
                score.put(num, rank);
                rank++; // 다음 등수 매기기 위해 1증가
            }
        }
        // A를 순차적으로 돌면서 HashMap에 기록된 등수를 출력
        for (int i = 0; i<A.length; i++) {
            int orignum = A[i];
            System.out.print(score.get(orignum) + " ");
        }
    }
}