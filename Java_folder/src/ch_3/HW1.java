// 22313526 장지웅

import java.util.*;

public class HW1 {
    public static void main(String[] args) {
        Solution1 S = new Solution1();
        int[] numbers = {5, 0, 2, 7};
        System.out.println("입력 =" + Arrays.toString(numbers));
        System.out.println("출력 =" + Arrays.toString(S.Solution(numbers)));
    }
}

class Solution1 {
    public int[] Solution(int[] numbers) {
        // 중복을 허용하지 않고 오름차순으로 정렬하는 자료구조 TreeSet 사용
        Set<Integer> set = new TreeSet<>();
        
        // 서로 다른 인덱스에 있는 2개를 뽑아 더함
        for (int i = 0; i<numbers.length; i++) {
            for (int j = i+1; j<numbers.length; j++) {
                set.add(numbers[i]+numbers[j]);
            }
        }
        // set에 담긴 결과를 int 배열로 변환해야함. numbers가 int 배열이니까
        List<Integer> list = new ArrayList<>(set);
        int[] result = new int[list.size()]; // list 크기만큼 배열 생성
        for (int i = 0; i<list.size(); i++)
            result[i] = list.get(i);

        return result;
    }
}