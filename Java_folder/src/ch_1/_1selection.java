public class _1selection extends AbstractSort {
    public static void sort (Comparable[] a) {
        int N = a.length;
        
        for (int i = 0; i < N - 1; i++) {
            int min = i;
            
            // ① 안쪽 루프의 시작과 끝 조건은?
            for (int j = i+1; j<N; j++) {
                
                // ② a[j]와 a[min]을 비교하여 min을 갱신하는 코드는? (AbstractSort의 메서드 사용)
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            
            // ③ 최소값을 찾은 후, 현재 위치(i)와 교환하는 코드는? (AbstractSort의 메서드 사용)
            exch(a, i, min);
        }
        assert isSorted(a);
    }
    // ... 생략 ...
}