
public class BinarySearchTree<K,T> {
	BNode<K,T> root;
    BinarySearchTree() {
		root = null;
	}
	
	public BNode<K,T> getRoot(){
		return root;
	}
	
	public void setRoot(BNode<K,T> n) {
		root = n;
	}
	
	public void inorder(BNode<K,T>n) {
		BNode<K,T> r = n;
		if(n==null) {System.out.print("");}
		else {
			inorder(n.getLeft());
			System.out.print("-"+n.getKey().toString()+"-");
			inorder(n.getRight());
		}
	}
	/**public BNode<K,T> search(K k) {
		BNode<K,T> x = root;
		while((x!=null)&&(k.toString().compareTo(x.getKey().toString())!=0)) {
			if(k.toString().compareTo(x.getKey().toString())<0) {
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
		}
		return x;
	}**/
	
	public BNode<K,T> search(K k) {
		BNode<K,T> y = root;
		String s = k.toString();
		if(y!=null) {
		String n = y.toString();
		String [] arr1 = s.split(" ");
		String [] arr2 = n.split(" ");
		while((y!=null)&&!((arr1[0].compareTo(arr2[0])==0)&&(arr1[1].compareTo(arr2[1])==0))) {
			if(arr1[0].compareTo(arr2[0])<0) {
				y = y.getLeft();
			}
			else if(arr1[0].compareTo(arr2[0])>0) {
				y = y.getRight();
			}
			else {
				y = y.getRight();
			}
			if(y!=null) {
			arr2 = y.getKey().toString().split(" ");
			}
		}
		}
		else {y = null;}
	return y;
	}

	
	
	/**public String searchAddress(K k) throws NotFoundException {
		inorder(root);
		BNode<K,T> x = root;
		String s = "-";
		while((x!=null)&&(k.toString().compareTo(x.getKey().toString())!=0)) {
			
			if(k.toString().compareTo(x.getKey().toString())<0) {
				x = x.getLeft();
				s += "L";
			}
			else {
				x = x.getRight();
				s += "R";
			}	
		}
		if(x!=null) {
		return s;
		}
		else {
			throw new NotFoundException();
		}
	}**/
	
	public String searchAddress(K k) throws NotFoundException {
		//inorder(root);
		String res = "-";
		BNode<K,T> y = root;
		if(y!=null) {
		String s = k.toString();
		String n = y.toString();
		String [] arr1 = s.split(" ");
		String [] arr2 = n.split(" ");
		while((y!=null)&&!((arr1[0].compareTo(arr2[0])==0)&&(arr1[1].compareTo(arr2[1])==0))) {
			if(arr1[0].compareTo(arr2[0])<0) {
				y = y.getLeft();
				res += "L";
			}
			else if(arr1[0].compareTo(arr2[0])>0) {
				y = y.getRight();
				res += "R";
			}
			else {
				y = y.getRight();
				res += "R";
			}
			if(y!=null) {
			arr2 = y.getKey().toString().split(" ");
			}
		}
		}
		if(y!=null) {
		return res;
		}
		else {
			throw new NotFoundException();
		}
	}
	
	
	/**public int insert(BNode<K,T> b) {
		int count = 1;
		BNode<K,T> x = this.root;
		BNode<K,T> y = null;
		while (x != null) {
			y = x;
			if (x.compareTo(b)<=0) {
				x = x.getRight();
			}
			else {
				x = x.getLeft();
			}
			count += 1;
		}
		if (y == null) {
			this.root = b;
		}
		else if(b.compareTo(y)<0) {
			y.setLeft(b);
			b.setParent(y);
		}
		else {
			y.setRight(b);
			b.setParent(y);
		}
		return count;
	}**/
	
	public int insert(BNode<K,T> b) {
		BNode<K,T> c = search(b.getKey());
		if(c==null) {
		int count = 1;
		BNode<K,T> x = this.root;
		BNode<K,T> y = null;
		while (x != null) {
			y = x;
			if (x.compareTo1(b)<=0) {
				x = x.getRight();
			}
			else {
				x = x.getLeft();
			}
			count += 1;
		}
		if (y == null) {
			this.root = b;
		}
		else if(b.compareTo1(y)<0) {
			y.setLeft(b);
			b.setParent(y);
		}
		else {
			y.setRight(b);
			b.setParent(y);
		}
		return count;	
	}
	
	else {
		return -1;
		}
		
	}

	public void Transplant(BNode<K,T> n1,BNode<K,T>n2) {
		if (n1.getParent() == null) {
			this.root = n2;
		}
		else if(n1 == n1.getParent().getLeft()) {
			n1.getParent().setLeft(n2);
		}
		else {
			n1.getParent().setRight(n2);
		}
		if (n2!=null) {
			n2.setParent(n1.getParent());
		}
	}
	
	/**public int delete(K k) {
		int count = 1;
		BNode<K,T> y = root;
		while((y!=null)&&(k.toString().compareTo(y.getKey().toString())!=0)) {
			if(k.toString().compareTo(y.getKey().toString())<0) {
				y = y.getLeft();
			}
			else {
				y = y.getRight();
			}
			count += 1;
		}
		if(y==null) {
			return -1;
		}
		if (y.getLeft()==null) {
			Transplant(y,y.getRight());
			count += 1;
		}
		
		else if(y.getRight()==null) {
			Transplant(y,y.getLeft());
			count += 1;
		}
		
		else {
			BNode<K,T> x = y.getRight();
			count += 1;
			while(x.getLeft()!=null) {
				x = x.getLeft();
				count += 1;
			}
			if (x.getParent() != y) {
				Transplant(x,x.getRight());
				x.setRight(y.getRight());
				x.getRight().setParent(x);
			}
			Transplant(y,x);
			x.setLeft(y.getLeft());
			y.getLeft().setParent(x);
		}
	return count;
	}**/
	
	public int delete(K k) {
		int count = 1;
		BNode<K,T> y = root;
		String s = k.toString();
		if(y!=null) {
		String n = y.toString();
		String [] arr1 = s.split(" ");
		String [] arr2 = n.split(" ");
		while((y!=null)&&!((arr1[0].compareTo(arr2[0])==0)&&(arr1[1].compareTo(arr2[1])==0))) {
			if(arr1[0].compareTo(arr2[0])<0) {
				y = y.getLeft();
			}
			else if(arr1[0].compareTo(arr2[0])>0) {
				y = y.getRight();
			}
			else {
				y = y.getRight();
			}
			if(y!=null) {
			arr2 = y.getKey().toString().split(" ");
			count += 1;
			}
		}
		}
		if(y==null) {
			return -1;
		}
		if (y.getLeft()==null) {
			Transplant(y,y.getRight());
			if(y.getRight() != null)
			count += 1;
		}
		
		else if(y.getRight()==null) {
			Transplant(y,y.getLeft());
			count += 1;
		}
		
		else {
			BNode<K,T> x = y.getRight();
			count += 1;
			while(x.getLeft()!=null) {
				x = x.getLeft();
				count += 1;
			}
			if (x.getParent() != y) {
				if(x.getRight() != null)
					count++;
				Transplant(x,x.getRight());
				x.setRight(y.getRight());
				x.getRight().setParent(x);
			}
			Transplant(y,x);
			x.setLeft(y.getLeft());
			y.getLeft().setParent(x);
		}
	return count;
	}
}
