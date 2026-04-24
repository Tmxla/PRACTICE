import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class HW222 {

	public static void main(String[] args) {
		Tree23<String, Integer> st = new Tree23<>();
		Scanner sc = new Scanner(System.in);	
		System.out.print("입력 파일 이름? ");
		String fname = sc.nextLine();	// 파일 이름을 입력
		System.out.print("난수 생성을 위한 seed 값? ");
		Random rand = new Random(sc.nextLong());
		sc.close();
		try {
			sc = new Scanner(new File(fname));
			long start = System.currentTimeMillis();
			while (sc.hasNext()) {
				String word = sc.next();
				if (!st.contains(word))
					st.put(word, 1);
				else	st.put(word, st.get(word) + 1);
			}
			long end = System.currentTimeMillis();
			System.out.println("입력 완료: 소요 시간 = " + (end-start) + "ms");
			
			System.out.println("### 생성 시점의 트리 정보");
			print_tree(st);		// 정상적으로 출력되면 50점
			
			ArrayList<String> keyList = (ArrayList<String>) st.keys();
			Collections.shuffle(keyList, rand);
			int loopCount = (int)(keyList.size() * 0.95);
			for (int i = 0; i < loopCount; i++) {
				st.delete(keyList.get(i));						// 주석 처리 가능
			}
			System.out.println("\n### 키 삭제 후 트리 정보");		// 주석 처리 가능
			print_tree(st);										// 주석 처리 가능. 여기까지 정상적으로 출력되면 100점
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		if (sc != null)
			sc.close();
	}

	private static void print_tree(Tree23<String, Integer> st) {
		System.out.println("등록된 단어 수 = " + st.size());		
		System.out.println("트리의 깊이 = " + st.depth());		
		
		String maxKey = "";
		int maxValue = 0;
		for (String word : st.keys())
			if (st.get(word) > maxValue) {
				maxValue = st.get(word);
				maxKey = word;
			}
		System.out.println("가장 빈번히 나타난 단어와 빈도수: " + maxKey + " " + maxValue);
	}
}
class Tree23<K extends Comparable<K>, V> {
	private Node root; // 트리의 시작점(뿌리)
	private int size = 0;

    // 이 상자(Node) 구조만 먼저 눈에 익혀보세요.
	private class Node {
		K k1, k2; // 데이터(키)가 들어갈 빈 공간 2개
        V v1, v2; // 값(Value)이 들어갈 빈 공간 2개
        
        Node left, mid, right; // 밑으로 뻗어나갈 가지(자식) 3개
        
        int n; // ★핵심★: 지금 이 상자에 데이터가 몇 개(1개? 2개?) 들었는지 표시

        // 처음 상자를 뿅! 하고 만들 때 (처음엔 무조건 데이터 1개짜리로 시작)
        Node(K k, V v) {
            this.k1 = k;
            this.v1 = v;
            this.n = 1; // "나 지금 빈 공간 2개 중에 1개만 채웠어!"
        }
    }

	private class Split {
		K k; V v; Node r;
		Split(K k, V v, Node r) {
			this.k = k; this.v = v; this.r = r;
		}
	}
	
	// left < k1 < mid < k2 < right 순으로 정렬
	// p는 지금 키 집어넣을 대상 노드, k는 넣을 키, v는 넣을 값 child는 k와 함께 딸린 오른쪽 자식 노드
	private Split addToNode(Node p, K k, V v, Node child) {
		if (p.n== 1) { // 2노드
			if (k.compareTo(p.k1) < 0) { // k < p.k1 이니까 k를 앞에 넣음 (왼쪽)
				p.k2 = p.k1; // 원래 k1을 k2 자리로
				p.v2 = p.v1;
				p.right = p.mid; // 기존 mid를 right로

				p.k1 = k; // 새로운 k를 k1에
				p.v1 = v; 
				p.mid = child; // 새로운 child를 mid로
			}

			else { // k > p.k1
				p.k2 = k; // 걍 k2 뒤에 꽂음
				p.v2 = v;
				p.right = child;
			}
			p.n = 2; // 둘 다 3노드로 됨

			return null; // split 없음
		}
		Node rightNode;
		K midK; V midV;
		// p.n == 2 3노드
		if (k.compareTo(p.k1) < 0) { // k < k1 < k2
			midK=p.k1; midV=p.v1;
			rightNode = new Node(p.k2, p.v2); // p.k1이 올라가면서 오른쪽 노드가 됨
			rightNode.left = p.mid; // 기존 mid랑 right가 rightnode 자식으로
			rightNode.mid = p.right;

			p.k1 = k; p.v1 = v; p.mid = child; 
			//     k1(위로 올라감)
       		//    /            \
      		//   [k]          [k2]
      		//  /   \         /   \
    	    // left  child   mid  right
     		//   (p)        (rightNode)
		}
		// k1 < k < k2로 k가 위로 올라가는 경우
		else if (k.compareTo(p.k2) < 0) {
			midK=k; midV=v;
			rightNode = new Node(p.k2, p.v2); // k가 올라가면서 rightnode 됨
			rightNode.left = child; //
			rightNode.mid = p.right; // 기존 right가 rightnode로
		}

		else { // k1 < k2 < k k2가 올라감
			midK=p.k2; midV=p.v2;
			rightNode = new Node(k, v); // k가 오른 노드 됨
			rightNode.left = p.right; // 기존 right가 rightnode로
			rightNode.mid = child;
		}

		p.n = 1;
		p.k2 = null;
		p.v2 = null;
		p.right = null; // p를 2노드로 정리

		return new Split(midK, midV, rightNode); // 올라갈 것들 split에 담아서 변환
	}

	public void put(K key, V value) { // 외부에서 호출
		if (root ==  null) {
			root = new Node(key, value);
			size++;
			return;
		}

		Split s = put(root, key, value);

		if (s != null) { // split가 루트까지 왔을 때
			Node newRoot = new Node(s.k, s.v);
			newRoot.left = root;
			newRoot.mid = s.r;
			root = newRoot; // 트리 높이 1 증가
		}
	}

	private Split put(Node p, K key, V value) { // 재귀 버전이고 실제 작업은 여기서
		if (key.equals(p.k1)) { // 이미 있는 키면 값만 업데이트
			p.v1 = value; 
			return null; 
		}
		if (p.n == 2 && key.equals(p.k2)) {
			p.v2 = value;
			return null;
		}

		if (p.left == null) { // 리프면 바로 삽입
			size++;
			return addToNode(p, key, value, null);
		}

		Split s;
		if (key.compareTo(p.k1) < 0) { // k1보다 작으니까 왼쪽으로
			s = put(p.left, key, value);
		}
		else if (p.n == 1 || key.compareTo(p.k2) < 0) {
			s = put(p.mid, key, value);
		}
		else {
			s = put(p.right, key, value);
		}

		if (s != null) { // split 일어났으면
			return addToNode(p, s.k, s.v, s.r);
		}
		return null;
	}

	public void delete(K key) {
		if (root == null)
			return;

		// 재귀적으로 지우고, 트리 높이가 낮아졌는지(루트가 비었는지) 확인
		boolean isShrunk = delete(root, key);
		if (isShrunk && root.n == 0)
			root = root.left; // 루트가 텅 비었으면 왼쪽 자식이 새 루트가 됨 (트리 높이 감소)
		if (contains(key) == false) // 완벽히 지워졌다면 size 감소
			size--;
	}

	// p: 현재 노드, key: 지울(또는 대타의) 키
	// 반환값: 내 방이 텅 비었는가? (Underflow 발생 여부 true/false)
	private boolean delete(Node p, K key) {
		if (p == null)
			return false;

		boolean found = false;
		int keyIndex = 0; // 지울 키가 k1이면 1, k2면 2

		if (key.equals(p.k1)) { 
			found = true;
			keyIndex = 1;
		}
		else if (p.n == 2 && key.equals(p.k2)) {
			found = true;
			keyIndex = 2;
		}

		// 1. 지울 놈을 찾았다!
		if (found) {
			if (p.left == null) { // [Case A] 바닥(Leaf)에서 지울 때
				if (keyIndex == 1) {
					p.k1 = p.k2;
					p.v1 = p.v2; // k2를 k1 자리로 땡김 (k2가 없었으면 null이 됨)
				}
				p.k2 = null;
				p.v2 = null;
				p.n--;
				return p.n == 0; // 방이 비었으면 true 반환 (위로 SOS!)
			} else {              // [Case B] 내부 노드에서 지울 때
				// 오른쪽 가지로 한 칸 간 뒤, 계속 왼쪽으로 파고들어 대타(Successor) 찾기
				Node successor = getSuccessor(keyIndex == 1 ? p.mid : p.right);
				
				// 대타의 값을 내 방으로 덮어쓰기 복사
				if (keyIndex == 1) { 
					p.k1 = successor.k1;
					p.v1 = successor.v1;
				}
				else {
					p.k2 = successor.k1;
					p.v2 = successor.v1;
				}
				
				// 타겟 변경: 이제 바닥에 있는 '진짜 대타'를 지우러 밑으로 내려가도록 세팅
				key = successor.k1;
				found = false; 
			}
		}

		boolean underflow = false;
		// 2. 길 찾아서 내려가기 (방금 복사해온 Successor 키를 지우러 가는 과정 포함)
		if (key.compareTo(p.k1) < 0) {
			underflow = delete(p.left, key);
			if (underflow)
				underflow = fixUnderflow(p, 1); // 1: 왼쪽 자식 살려내기!
		} else if (p.n == 1 || key.compareTo(p.k2) < 0) {
			underflow = delete(p.mid, key);
			if (underflow)
				underflow = fixUnderflow(p, 2); // 2: 가운데 자식 살려내기!
		} else {
			underflow = delete(p.right, key);
			if (underflow)
				underflow = fixUnderflow(p, 3); // 3: 오른쪽 자식 살려내기!
		}

		return underflow; // 내가 심폐소생을 했는데도 내 방마저 비었으면 또 위로 SOS (true)
	}

	// 대타(Successor)를 찾아오는 도우미 함수
	private Node getSuccessor(Node p) {
		while (p.left != null)
			p = p.left;
		return p;
	}

	// 텅 빈 자식을 살려내는 심폐소생술 뼈대 (다음 단계에서 여기에 살을 붙일 겁니다!)
	private boolean fixUnderflow(Node p, int childIndex) {
		Node child, leftSib, rightSib;

		// ---------------------------------------------------------
		// [Case 1] 첫 번째(왼쪽) 자식이 텅 비었을 때
		// ---------------------------------------------------------
		if (childIndex == 1) {
			child = p.left;
			rightSib = p.mid; // 왼쪽 자식은 무조건 오른쪽 형제(mid)밖에 없음

			if (rightSib.n == 2) { // 작전 A: 오른쪽 형제한테 빌려오기 (회전)
				child.k1 = p.k1;
				child.v1 = p.v1;
				child.n = 1;
				child.mid = rightSib.left; // 형제의 왼쪽 가지를 뺏어옴

				p.k1 = rightSib.k1;
				p.v1 = rightSib.v1; // 부모는 형제의 첫 번째 키를 가져감

				rightSib.k1 = rightSib.k2; rightSib.v1 = rightSib.v2;
				rightSib.left = rightSib.mid; rightSib.mid = rightSib.right;
				rightSib.k2 = null; rightSib.v2 = null; rightSib.right = null;
				rightSib.n = 1;
				return false;
			} else { // 작전 B: 형제(mid)와 합치기 (Combine)
				child.k1 = p.k1; child.v1 = p.v1;
				child.k2 = rightSib.k1; child.v2 = rightSib.v1;
				child.mid = rightSib.left;
				child.right = rightSib.mid;
				child.n = 2;

				p.k1 = p.k2;
				p.v1 = p.v2; // 부모 자리 땡기기
				p.mid = p.right;
				p.k2 = null; p.v2 = null; p.right = null;
				p.n--;
				return p.n == 0; // 부모까지 비었으면 위로 SOS
			}
		} 
		// ---------------------------------------------------------
		// [Case 2] 두 번째(가운데) 자식이 텅 비었을 때 (여기서 '왼쪽 우선' 규칙 적용!)
		// ---------------------------------------------------------
		else if (childIndex == 2) {
			child = p.mid;
			leftSib = p.left;
			rightSib = p.right; // 부모가 2-node면 null일 수 있음

			// 작전 A-1: ★왼쪽 형제한테 먼저 빌려오기 (과제 최우선 조건)
			if (leftSib.n == 2) {
				child.k1 = p.k1;
				child.v1 = p.v1;
				child.n = 1;
				child.mid = child.left; // 텅 빈 자식의 유일한 가지는 left에 남아있음
				child.left = leftSib.right; // 왼쪽 형제의 오른쪽 가지를 뺏어옴

				p.k1 = leftSib.k2;
				p.v1 = leftSib.v2;
				leftSib.k2 = null; leftSib.v2 = null; leftSib.right = null;
				leftSib.n = 1;
				return false;
			}
			// 작전 A-2: 왼쪽이 가난하면, 오른쪽 형제한테 빌려오기
			else if (p.n == 2 && rightSib.n == 2) {
				child.k1 = p.k2;
				child.v1 = p.v2;
				child.n = 1;
				child.mid = rightSib.left;

				p.k2 = rightSib.k1;
				p.v2 = rightSib.v1;

				rightSib.k1 = rightSib.k2;
				rightSib.v1 = rightSib.v2;
				rightSib.left = rightSib.mid; rightSib.mid = rightSib.right;
				rightSib.k2 = null; rightSib.v2 = null; rightSib.right = null;
				rightSib.n = 1;
				return false;
			}
			// 작전 B: 양쪽 다 가난하면 ★왼쪽 형제와 합치기 (과제 조건)
			else {
				leftSib.k2 = p.k1;
				leftSib.v2 = p.v1;
				leftSib.right = child.left; // 텅 빈 놈의 남은 가지 흡수
				leftSib.n = 2;

				p.k1 = p.k2;
				p.v1 = p.v2;
				p.mid = p.right;
				p.k2 = null; p.v2 = null; p.right = null;
				p.n--;
				return p.n == 0;
			}
		} 
		// ---------------------------------------------------------
		// [Case 3] 세 번째(오른쪽) 자식이 텅 비었을 때 (부모가 3-node일 때만 발생)
		// ---------------------------------------------------------
		else {
			child = p.right;
			leftSib = p.mid; // 오른쪽 자식은 무조건 왼쪽 형제(mid)밖에 없음

			if (leftSib.n == 2) { // 작전 A: 왼쪽 형제한테 빌려오기
				child.k1 = p.k2;
				child.v1 = p.v2;
				child.n = 1;
				child.mid = child.left; child.left = leftSib.right;

				p.k2 = leftSib.k2;
				p.v2 = leftSib.v2;
				leftSib.k2 = null; leftSib.v2 = null; leftSib.right = null;
				leftSib.n = 1;
				return false;
			} else { // 작전 B: 형제(mid)와 합치기
				leftSib.k2 = p.k2;
				leftSib.v2 = p.v2;
				leftSib.right = child.left;
				leftSib.n = 2;

				p.k2 = null; p.v2 = null; p.right = null;
				p.n--;
				return false; // 부모가 3-node에서 2-node로 내려앉은 거라 절대 0이 될 수 없음! (false 리턴)
			}
		}
	}
	public Iterable<K> keys() {
		ArrayList<K> list = new ArrayList<>();
		inorder(root, list);
		return list;
	}

	private void inorder(Node p, ArrayList<K> list) {
		if (p == null)
			return;

		inorder(p.left, list);
		list.add(p.k1);
		inorder(p.mid, list);

		if (p.n == 2) {
			list.add(p.k2);
			inorder(p.right, list);
		}
	}

	public boolean contains(K key) {
		return get(key) != null;
	}

	public V get(K key) {
		Node p = root;

		while (p != null) {
			if (key.equals(p.k1))
				return p.v1;
			if (p.n == 2 && key.equals(p.k2))
				return p.v2;

			if (key.compareTo(p.k1) < 0)
				p = p.left;
			else if (p.n == 1 || key.compareTo(p.k2) < 0)
				p = p.mid;
			else
				p = p.right;
		}

		return null;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int depth() {
		int d = 0;
		Node curr = root;
		while (curr != null) {
			d++;
			curr = curr.left;
		}
		return d;
	}
}