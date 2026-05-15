// 22313526 장지웅

class Solution3 {
    public int solution3(int[][] triangle) {
        int n = triangle.length;
        int[] dp = new int[n];

        dp[0] = triangle[0][0];

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + triangle[i][i];

            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.max(dp[j - 1], dp[j]) + triangle[i][j];
            }

            dp[0] += triangle[i][0];
        }

        int answer = 0;
        for (int value : dp) {
            answer = Math.max(answer, value);
        }

        return answer;
    }
}