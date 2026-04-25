public abstract class AbstractSort {
    // 각 정렬 알고리즘에서 구현할 실제 정렬 메서드
    public static void sort(Comparable[] a) { };

    // ① 두 원소를 비교하는 메서드 (v < w 이면 true)
    protected static boolean less(Comparable v, Comparable w) { 
        return v.compareTo(w) < 0; 
    }

    // ② 두 원소의 위치를 서로 바꾸는 메서드
    protected static void exch(Comparable[] a, int i, int j) { 
        Comparable t = a[i]; 
        a[i] = a[j];
        a[j] = t; 
    }

    // ③ 배열의 모든 원소를 화면에 출력하는 메서드
    protected static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    // ④ 배열이 실제로 정렬되었는지 검증하는 메서드
    protected static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }
}