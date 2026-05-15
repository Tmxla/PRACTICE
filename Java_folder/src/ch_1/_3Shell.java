public class _3Shell extends AbstractSort{
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        // ① h 간격의 초깃값을 결정하는 공식은? (교수님 PPT 기준)
        while (h < N/3) {
            h = 3*h+1; 
        }
        
        while (h >= 1) { 
            
            // ② 1칸씩 가던 삽입 정렬의 i=1이 i=h로 바뀌었습니다.
            for (int i = h; i < N; i++) {
                
                // ③ 안쪽 루프: j는 i부터 시작해서 왼쪽으로 가되, 1칸씩이 아니라 h칸씩 점프해야 합니다.
                // j가 범위(h)를 넘지 않고, 현재 원소(j)가 왼쪽 원소(j-h)보다 작을 때 교환합니다.
                for (int j = i; j>=h && less(a[j], a[j-h]); j -= h) {
                    
                    // ④ 현재 원소와 h칸 앞의 원소 자리를 바꿈
                    exch(a, j, j-h); 
                }
            }
            
            // ⑤ 다음 간격으로 축소하는 코드는?
            h = h/3; 
        }
    }
}
