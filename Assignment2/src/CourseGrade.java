class CourseGrade implements CourseGrade_{
	private String coursetitle;
	private String coursenum;
	private String grade;
	public CourseGrade(String ct,String cn, String g){
		coursetitle = ct;
		coursenum = cn;
		grade = g;
	}
	public String coursetitle() {
		return coursetitle;
	}
	public String coursenum() {
		return coursenum;
	}
	
	public GradeInfo_ grade() {
		GradeInfo_ gr = new GradeInfo(grade);
		return gr;
	}
}

