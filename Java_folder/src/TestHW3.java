import java.util.*;

public class TestHW3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("입력: ");
        String line = sc.nextLine();
        int[][] triangle = parse(line);
        System.out.println("출력: " + new Solution().solution(triangle));
        sc.close();
    }

    static int[][] parse(String s) {
        List<int[]> rows = new ArrayList<>();
        int i = 0, n = s.length();
        while (i < n) {
            while (i < n && s.charAt(i) != '[') i++;
            if (i >= n) break;
            i++; // '[' 다음
            // 안쪽이 또 '['면 바깥 대괄호니까 skip
            int j = i;
            while (j < n && s.charAt(j) != ']' && s.charAt(j) != '[') j++;
            if (j >= n) break;
            if (s.charAt(j) == '[') { continue; } // 바깥 '['였음
            String inner = s.substring(i, j).trim();
            if (inner.isEmpty()) { i = j + 1; continue; }
            String[] tok = inner.split("\\s*,\\s*");
            int[] row = new int[tok.length];
            for (int k = 0; k < tok.length; k++) row[k] = Integer.parseInt(tok[k]);
            rows.add(row);
            i = j + 1;
        }
        return rows.toArray(new int[0][]);
    }
}