
public class Student implements Student_ {
	
	private String first,last,hostel,dept,cgpa;
	private Pair <String,String> keyDH;
	private Pair <String,String> keySCBST;
	Student(String f,String l,String h,String d,String c){
		this.first = f;
		this.last = l;
		this.hostel = h;
		this.dept = d;
		this.cgpa = c;	
		this.keyDH = new Pair<String,String>(first,last);
		this.keySCBST = new Pair<String,String>(first+" ",last);
	}
	
	public String fname() {
		return first;
	}
	
	public String lname() {
		return last;
	}
	
	public String hostel() {
		return hostel;
	}
	
	public String department() {
		return dept;
	}
	
	public String cgpa() {
		return cgpa;
	}
	
	public Pair<String,String>getKeyDH(){
		return keyDH;
	}
	
	public Pair<String,String>getKeySCBST(){
		return keySCBST;
	}
	
	public void setFname(String s) {
		first = s;
	}
	
	public void setLname(String l) {
		 last=l;
	}
	
	public void setHostel(String h) {
		hostel = h;
	}
	
	public void setDepartment(String d) {
		dept=d;
	}
	
	public void setCgpa(String c) {
		cgpa=c;
	}
	
	public String toString() {
		return first+" "+last+" "+hostel+" "+dept+" "+cgpa;
	}
}
