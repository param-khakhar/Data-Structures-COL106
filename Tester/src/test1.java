import java.util.ArrayList;


public class test1{
	

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> u, Integer l,Integer r){
    	if(l == r) {
    		ArrayList<Integer> use = new ArrayList<>();
    		use.add(u.get(l));
    		return use;
    	}
    	Integer mid = (l+r)/2;
    	ArrayList<Integer> left = mergeSort(u,l,mid);
    	ArrayList<Integer> right = mergeSort(u,mid+1,r);
    	return  merge(left,right);
    }
    
    public static ArrayList<Integer> merge(ArrayList<Integer> left,ArrayList<Integer> right) {
    	ArrayList<Integer> res = new ArrayList<>();
    	Integer i = 0;
    	Integer j= 0;
    	Integer m = left.size();
    	Integer n = right.size();
    	
    	while(i+j<m+n) {
    		if(i==m) {
    			res.add(right.get(j));
    			j += 1;
    		}
    		else if(j==n) {
    			res.add(left.get(i));
    			i += 1;
    		}
    		else if(right.get(j).compareTo(left.get(i))<0) {
    			res.add(right.get(j));
    			j += 1;
    		}
    		else {
    			res.add(left.get(i));
    			i += 1;
    		}
    	}
    	return res;
    }

	public static void main(String args[]) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(8);
		arr.add(3);
		arr.add(6);
		arr.add(4);
		arr.add(9);
		arr.add(2);
		arr.add(7);
		arr.add(1);
		arr.add(5);
		ArrayList<Integer> arr1 = mergeSort(arr,0,arr.size()-1);
		for(int i=0;i<arr1.size();i++) {
			System.out.println(arr1.get(i));
		}
	}
}