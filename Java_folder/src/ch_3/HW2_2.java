// 22313526 장지웅

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class HW2_2 {
    public static void main(String[] args) {
        
        ListNode head = null;

        int[] list = {-1, 5, 3, 4, 0};
        // 원본 리스트의 시작점 고정
        head = new ListNode(list[0]);
        ListNode ptr = head; // 노드를 이어 붙일 작업용 포인터로 별도의 리스트 생성

        // 리스트를 ptr로 이동해가며 하나씩 채움
        for (int i = 1; i<list.length; i++) {
            ptr.next = new ListNode(list[i]);
            ptr = ptr.next;
        }
        
        Solution2 sol = new Solution2();
        head = sol.insertionSortList(head);
        printList(head); 
    }

    public static void printList(ListNode head) {
        // head가 null이 될때까지 출력
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print(" -> ");
            }
            head = head.next;
        }
    }

}

class Solution2 {
    public ListNode insertionSortList(ListNode head) {
        // 노드가 없거나 1개밖에 없으면 이미 정렬된 상태
        if (head == null || head.next == null) return head;

        ListNode temp = new ListNode(0); // 새롭게 정렬될 리스트 기준 역할 할 노드 생성

        while (head != null) { // head 포인터를 이동시키며 리스트 순회
            ListNode nextNode = head.next; // 삽입할 때 링크 끊기지 않도록 다음 노드 미리 저장
            ListNode prev = temp; // 탐색용 포인터 prev (항상 앞부터 보도록 초기화 됨)

            // prev 다음 값이 없거나 prev 다음 값이 head 값보다 작으면 prev는 오른쪽으로 이동
            while (prev.next != null && prev.next.val < head.val) {
                prev = prev.next;
            }

            head.next = prev.next; // 삽입할 노드 꼬리를 prev 다음 노드에 연결
            prev.next = head; // 이전의 노드가 head를 가리키게 연결

            head = nextNode; // 아까 저장한 다음 노드로 이동
        }
        return temp.next; // temp의 다음 노드부터가 진짜 데이터의 시작점
    }
}