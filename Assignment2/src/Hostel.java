import java.util.Iterator;

class Hostel implements Entity_{
	private String name;
	LList<Student_> stud = new LList<Student_>();
	Hostel(String s){
		name = s;
	}
	public String name() {
		return name;
	}
	public void addStud(Student_ s){
		stud.add(s);
	}

	public Iterator<Student_> studentList(){
		Iterator <Student_> pointer = stud.positions1();
		return pointer;
	}
}