import java.util.Iterator;

class Student implements Student_{
	private String name;
	private String entrynumber;
	private String hostel;
	private String department;
	private int completedCredits=0;
	private int totalCredits = 0;
	private int cumulative = 0;
	private LList<CourseGrade_> courses = new LList<CourseGrade_>();

	Student(String n,String e,String h,String d){
		name = n;
		entrynumber = e;
		hostel = h;
		department = d;
	}
	
	public String name() {
		return name;
	}
	
	public String entryNo() {
		return entrynumber;
	}
	
	public String hostel() {
		return hostel;
	}
	
	public String department() {
		return department;
	}
	
	public String completedCredits() {
		return Integer.toString(completedCredits);
	}
	
	public void addCourse(CourseGrade c){
		courses.add(c);
		GradeInfo g = (GradeInfo)c.grade();
		if(g.grade() != GradeInfo_.LetterGrade.I) {
			totalCredits += 3;
			if((g.grade() != GradeInfo_.LetterGrade.E)||(g.grade()!= GradeInfo_.LetterGrade.F)) {
			completedCredits += 3;
			}
			cumulative += GradeInfo_.gradepoint(g.grade())*3;
		}
	}
	
	public LList<CourseGrade_> getLL(){
		return courses;
	}
	
	public String cgpa() {
		String fs;
		if(totalCredits != 0) {
	    fs = String.format("%.2f",cumulative*1.0/totalCredits);
		}
		else {
		 fs = "0.00";
		}
		return fs;
	}
	
	public Iterator<CourseGrade_> courseList(){
		Iterator <CourseGrade_> pointer = courses.positions1();
		return pointer;
	}
}