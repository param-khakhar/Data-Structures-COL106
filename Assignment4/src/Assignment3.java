import java.io.*;

public class Assignment3 {

	public static void main(String[] args) throws IOException{

	if(args[1].equals("DH")) {
		DoubleHashing DH = new DoubleHashing(Integer.parseInt(args[0]));
		
		File file1 = new File(args[2]); 
		BufferedReader br = new BufferedReader(new FileReader(file1));
		String st; 
		 
		while ((st = br.readLine()) != null) {
			String [] arr = st.split(" ");
			if(arr[0].equals("insert")) {
				Student s = new Student(arr[1],arr[2],arr[3],arr[4],arr[5]);
				System.out.println(DH.insert(s.getKeyDH(),s));
				
			}
			
			else if (arr[0].equals("update")) {
				Student s = new Student(arr[1],arr[2],arr[3],arr[4],arr[5]);
				int n = DH.update(s.getKeyDH(),s);
				if(n==-1) {
					System.out.println("E");
				}
				else {
					System.out.println(n);
				}
			}
			
			else if (arr[0].equals("delete")){
				int result = DH.delete(new Pair<>(arr[1],arr[2]));
				if(result==-1) {
				System.out.println("E");
			}
				else {
					System.out.println(result);
				}
			}
			else if(arr[0].equals("get")) {
				try {
					System.out.println(DH.get(new Pair<>(arr[1],arr[2])));
				}
				catch(NotFoundException e) {
					System.out.println("E");
				}
			}
			
			else if(arr[0].equals("contains")) {
				if(DH.contains(new Pair<>(arr[1],arr[2]))) {
					System.out.println("T");
				}
				else {
					System.out.println("F");
				}
			}
			
			else if(arr[0].equals("address")) {
				try {
					System.out.println(DH.address(new Pair<>(arr[1],arr[2])));
				}
				catch(NotFoundException n) {System.out.println("E");}
			}
		}
		}
		
	else if(args[1].equals("SCBST")) {
		SeparateChaining SCBST = new SeparateChaining(Integer.parseInt(args[0]));
		
		File file1 = new File(args[2]); 
		BufferedReader br = new BufferedReader(new FileReader(file1));
		String st; 
		while ((st = br.readLine()) != null) {
			String [] arr = st.split(" ");
			
			if(arr[0].equals("insert")) {
				Student s = new Student(arr[1],arr[2],arr[3],arr[4],arr[5]);
				//System.out.println(SCBST.insert(s.getKeySCBST(),s));
				int n = SCBST.insert(s.getKeySCBST(),s);
				if(n==-1) {
					System.out.println("E");
				}
				else {
					System.out.println(n);
				}
			}
			
			else if (arr[0].equals("update")) {
				Student s = new Student(arr[1],arr[2],arr[3],arr[4],arr[5]);
				int n = SCBST.update(s.getKeySCBST(),s);
				if(n==-1) {
					System.out.println("E");
				}
				else {
					System.out.println(n);
				}
			}
			
			else if (arr[0].equals("delete")){
				//int n= SCBST.delete(new Pair<>(arr[1]+" ",arr[2]));
				int n= SCBST.delete(new Pair<>(arr[1]+" ",arr[2]));
				if(n==-1) {
					System.out.println("E");
			}
			else {
				System.out.println(n);
			}
		}
			else if(arr[0].equals("get")) {
				try {
					//SCBST.get(new Pair<>(arr[1],arr[2]));
					System.out.println(SCBST.get(new Pair<>(arr[1]+" ",arr[2])));
				}
				catch(NotFoundException e) {
					System.out.println("E");
				}
			}
			
			else if(arr[0].equals("contains")) {
				if(SCBST.contains(new Pair<>(arr[1]+" ",arr[2]))) {
					System.out.println("T");
				}
				else {
					System.out.println("F");
				}
			}
			
			else if(arr[0].equals("address")) {
				try {
					System.out.println(SCBST.address(new Pair<>(arr[1]+" ",arr[2])));
				}
				catch(NotFoundException n) {System.out.println("E");}
			}						
		}			
		}
	}
}
