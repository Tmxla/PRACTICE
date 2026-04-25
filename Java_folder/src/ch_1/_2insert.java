public class _2insert extends AbstractSort {
    public static void sort (Comparable[] a) {
        int N = a.length;
        
        // ① 바깥쪽 루프의 시작점은 0이 아닙니다. 왼쪽(0번)은 이미 정렬되었다고 치니까요. 초기 조건은?
        for (int i = 1; i < N; i++) {
            
            // ② 안쪽 루프: j는 i부터 시작해서 왼쪽으로(j--) 갑니다. 
            // 0번째 인덱스를 넘지 않으면서, '현재 원소(j)가 왼쪽 원소(j-1)보다 작을 때만' 계속 자리를 바꾸며 이동하는 조건식은?
            for (int j = i; j>0 && less(a[j], a[j-1]); j--) {
                
                // ③ 현재 원소와 왼쪽 원소의 자리를 바꾸는 코드는? (아까 Selection에서 배우신 함수)
                exch(a, j, j-1);
            }
        }
        assert isSorted(a);
    }
    public static void main(String[] args) {
        Integer[] a = {10, 4, 5, 2, 1, 8, 3, 6}; // 11페이지 예제 배열
        System.out.print("초기 상태: ");
        show(a);
        System.out.println("--------------------");
        
        _2insert.sort(a);
    }
}