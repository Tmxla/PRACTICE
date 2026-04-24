import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class HW2 {

	public static void main(String[] args) {
		Tree23<String, Integer> st = new Tree23<>();
		Scanner sc = new Scanner(System.in);	
		System.out.print("입력 파일 이름? ");
		String fname = sc.nextLine();
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
			print_tree(st);		
			
			ArrayList<String> keyList = (ArrayList<String>) st.keys();
			Collections.shuffle(keyList, rand);
			int loopCount = (int)(keyList.size() * 0.95);
			for (int i = 0; i < loopCount; i++) {
				st.delete(keyList.get(i));
			}
			System.out.println("\n### 키 삭제 후 트리 정보");		
			print_tree(st);										
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
	private Node root;
	private int size = 0;

	private class Node {
		K k1, k2;
        V v1, v2;
        
        Node left, mid, right;
        
        int n;

        Node(K k, V v) {
            this.k1 = k;
            this.v1 = v;
            this.n = 1;
        }
    }

	private class Split {
		K k; V v; Node r;
		Split(K k, V v, Node r) {
			this.k = k; this.v = v; this.r = r;
		}
	}
	
	private Split addToNode(Node p, K k, V v, Node child) {
		if (p.n== 1) {
			if (k.compareTo(p.k1) < 0) {
				p.k2 = p.k1;
				p.v2 = p.v1;
				p.right = p.mid;

				p.k1 = k;
				p.v1 = v; 
				p.mid = child;
			}
			
			else {
				p.k2 = k;
				p.v2 = v;
				p.right = child;
			}
			p.n = 2;

			return null;
		}
		Node rightNode;
		K midK; V midV;
		
		if (k.compareTo(p.k1) < 0) {
			midK=p.k1; midV=p.v1;
			rightNode = new Node(p.k2, p.v2);
			rightNode.left = p.mid;
			rightNode.mid = p.right;

			p.k1 = k; p.v1 = v; p.mid = child; 
		}
		
		else if (k.compareTo(p.k2) < 0) {
			midK=k; midV=v;
			rightNode = new Node(p.k2, p.v2);
			rightNode.left = child;
			rightNode.mid = p.right;
		}

		else {
			midK=p.k2; midV=p.v2;
			rightNode = new Node(k, v);
			rightNode.left = p.right;
			rightNode.mid = child;
		}

		p.n = 1;
		p.k2 = null;
		p.v2 = null;
		p.right = null;

		return new Split(midK, midV, rightNode);
	}

	public void put(K key, V value) {
		if (root ==  null) {
			root = new Node(key, value);
			size++;
			return;
		}

		Split s = put(root, key, value);

		if (s != null) {
			Node newRoot = new Node(s.k, s.v);
			newRoot.left = root;
			newRoot.mid = s.r;
			root = newRoot;
		}
	}

	private Split put(Node p, K key, V value) {
		if (key.equals(p.k1)) {
			p.v1 = value; 
			return null; 
		}
		if (p.n == 2 && key.equals(p.k2)) {
			p.v2 = value;
			return null;
		}

		if (p.left == null) {
			size++;
			return addToNode(p, key, value, null);
		}

		Split s;
		if (key.compareTo(p.k1) < 0) {
			s = put(p.left, key, value);
		}
		else if (p.n == 1 || key.compareTo(p.k2) < 0) {
			s = put(p.mid, key, value);
		}
		else {
			s = put(p.right, key, value);
		}

		if (s != null) {
			return addToNode(p, s.k, s.v, s.r);
		}
		return null;
	}

	public void delete(K key) {
		if (root == null)
			return;
		if (!contains(key))
			return;
		boolean isShrunk = delete(root, key);
		if (isShrunk && root.n == 0)
			root = root.left;
		size--;
	}

	private boolean delete(Node p, K key) {
		if (p == null)
			return false;

		int keyIndex = 0;

		if (key.equals(p.k1))
			keyIndex = 1;
		else if (p.n == 2 && key.equals(p.k2))
			keyIndex = 2;

		if (keyIndex > 0) {
			if (p.left == null) {
				if (keyIndex == 1) {
					p.k1 = p.k2;
					p.v1 = p.v2;
				}
				p.k2 = null;
				p.v2 = null;
				p.n--;
				return p.n == 0;
			}
			else {
				Node successor = getSuccessor(keyIndex == 1 ? p.mid : p.right);
				
				if (keyIndex == 1) { 
					p.k1 = successor.k1;
					p.v1 = successor.v1;
				}
				else {
					p.k2 = successor.k1;
					p.v2 = successor.v1;
				}
				
				key = successor.k1;
			}
		}

		boolean underflow = false;
		if (key.compareTo(p.k1) < 0) {
			underflow = delete(p.left, key);
			if (underflow)
				underflow = fixUnderflow(p, 1);
		}
		else if (p.n == 1 || key.compareTo(p.k2) < 0) {
			underflow = delete(p.mid, key);
			if (underflow)
				underflow = fixUnderflow(p, 2);
		}
		else {
			underflow = delete(p.right, key);
			if (underflow)
				underflow = fixUnderflow(p, 3);
		}

		return underflow;
	}

	private Node getSuccessor(Node p) {
		while (p.left != null)
			p = p.left;
		return p;
	}

	private boolean fixUnderflow(Node p, int childIndex) {
		Node child, leftSib, rightSib;

		if (childIndex == 1) {
			child = p.left;
			rightSib = p.mid;

			if (rightSib.n == 2) {
				child.k1 = p.k1;
				child.v1 = p.v1;
				child.n = 1;
				child.mid = rightSib.left;

				p.k1 = rightSib.k1;
				p.v1 = rightSib.v1;

				rightSib.k1 = rightSib.k2; rightSib.v1 = rightSib.v2;
				rightSib.left = rightSib.mid; rightSib.mid = rightSib.right;
				rightSib.k2 = null; rightSib.v2 = null; rightSib.right = null;
				rightSib.n = 1;
				return false;
			}
			else {
				child.k1 = p.k1; child.v1 = p.v1;
				child.k2 = rightSib.k1; child.v2 = rightSib.v1;
				child.mid = rightSib.left;
				child.right = rightSib.mid;
				child.n = 2;

				p.k1 = p.k2;
				p.v1 = p.v2;
				p.mid = p.right;
				p.k2 = null; p.v2 = null; p.right = null;
				p.n--;
				return p.n == 0;
			}
		}

		else if (childIndex == 2) {
			child = p.mid;
			leftSib = p.left;
			rightSib = p.right;

			if (leftSib.n == 2) {
				child.k1 = p.k1;
				child.v1 = p.v1;
				child.n = 1;
				child.mid = child.left;
				child.left = leftSib.right;

				p.k1 = leftSib.k2;
				p.v1 = leftSib.v2;
				leftSib.k2 = null; leftSib.v2 = null; leftSib.right = null;
				leftSib.n = 1;
				return false;
			}
			
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
			
			else {
				leftSib.k2 = p.k1;
				leftSib.v2 = p.v1;
				leftSib.right = child.left;
				leftSib.n = 2;

				p.k1 = p.k2;
				p.v1 = p.v2;
				p.mid = p.right;
				p.k2 = null; p.v2 = null; p.right = null;
				p.n--;
				return p.n == 0;
			}
		}
		
		else {
			child = p.right;
			leftSib = p.mid;

			if (leftSib.n == 2) {
				child.k1 = p.k2;
				child.v1 = p.v2;
				child.n = 1;
				child.mid = child.left; child.left = leftSib.right;

				p.k2 = leftSib.k2;
				p.v2 = leftSib.v2;
				leftSib.k2 = null; leftSib.v2 = null; leftSib.right = null;
				leftSib.n = 1;
				return false;
			}
			else {
				leftSib.k2 = p.k2;
				leftSib.v2 = p.v2;
				leftSib.right = child.left;
				leftSib.n = 2;

				p.k2 = null; p.v2 = null; p.right = null;
				p.n--;
				return p.n == 0;
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