// 22313526 장지웅

class Solution {
    private int[] answer;

    public int[] solution(int[][] arr) {
        answer = new int[2];
        compress(arr, 0, 0, arr.length);
        return answer;
    }
    private boolean isSameValue(int[][] arr, int row, int col, int size) {
        int value = arr[row][col];

        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (arr[i][j] != value) {
                    return false;
                }
            }
        }
        return true;
    }
    private void compress(int[][] arr, int row, int col, int size) {
        if (isSameValue(arr, row, col, size)) {
            answer[arr[row][col]]++;
            return;
        }

        else {
            int half = size / 2;
            compress(arr, row, col, half);
            compress(arr, row, col + half, half);
            compress(arr, row + half, col, half);
            compress(arr, row + half, col + half, half);
        }
    }

}