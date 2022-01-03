
public class Node {
	int key;
	String value;
	Node left;
	Node right;
	Node parent;
	
	Node(int n,String s){
		key = n;
		value = s;
		left = null;
		right = null;
		parent = null;
	}
}
