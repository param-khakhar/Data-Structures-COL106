import java.util.Iterator;

class Course implements Entity_{
	private String name;
	private String title;
	LList<Student_> stud = new LList<Student_>();
	Course(String s,String t){
		name = s;
		title = t;
	}
	public String name() {
		return name;
	}
	public void addStud(Student_ s){
		stud.add(s);
	}
	
	public String title() {
		return title;
	}

	public Iterator<Student_> studentList(){
		Iterator <Student_> pointer =  stud.positions1();
		return pointer;
	}
}