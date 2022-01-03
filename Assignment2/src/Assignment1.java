import java.io.*;
import java.util.Iterator;

public class Assignment1 {
	
	private static void getData(String f1,String f2,LList<Student> s1,LList<Course> c1,LList<Hostel> h1, LList<Dept> d1) throws IOException {
		LList<Student> allStudents = s1;
		LList<Course>  allCourses = c1;
		LList<Hostel> allHostels = h1; 
		LList<Dept> allDept = d1;
		Student curr = new Student("P","A","R","A");
		
		File file1 = new File(f1); 
		BufferedReader br = new BufferedReader(new FileReader(file1));
		
		String st; 
		 
		while ((st = br.readLine()) != null){
			
		  String [] arr = st.split(" ");
		  
		  Student s = new Student(arr[1], arr[0], arr[2], arr[3]);
		  allStudents.add(s);
		  Iterator<Hostel> itrh = allHostels.positions1();
		  
		  boolean addedh = false;
		  while((itrh.hasNext())&& !(addedh)){
			  Hostel h = itrh.next();
			  if(h.name().equals(arr[3])) {
				  h.addStud(s);
				  addedh = true;
			  }
		  }
		  if (!(addedh)){
			  Hostel h = new Hostel(arr[3]);
			  h.addStud(s);
			  allHostels.add(h);
		  }
		 	  
		  Iterator<Dept> itrd = allDept.positions1();
		  boolean addedd = false;
		  while((itrd.hasNext())&& !(addedd)){
			  Dept d = itrd.next();
			  if(d.name().equals(arr[2])) {
				  d.addStud(s);
				  addedd = true;
			  }
		  }
		  if (!(addedd)){
			  Dept d = new Dept(arr[2]);
			  d.addStud(s);
			  allDept.add(d);
		  }
		}
		br.close();
		
		File file2 = new File(f2); 
		BufferedReader br1 = new BufferedReader(new FileReader(file2));
		
		String st1;
		
		while ((st1 = br1.readLine()) != null){
			
			String [] arr1 = st1.split(" ");
			String e = arr1[0];
			String ct = arr1[1];
			String g = arr1[2];
			String cn = "";
			cn += arr1[3];
			for(int i=4;i<arr1.length;i++) {
				cn += " " + arr1[i];
			}
			
			CourseGrade c = new CourseGrade(ct,cn,g);
			
			Iterator<Student> itr1 = allStudents.positions1();
			boolean addedc = false;
			while((itr1.hasNext())&&!(addedc)) {
				Student stud = itr1.next();
				if(stud.entryNo().equals(e)) {
					stud.addCourse(c);
					curr = stud;
					addedc = true;
				}
			}
			Student_ curr2 = curr;
			Iterator<Course> itr2 = allCourses.positions1();
			boolean addeds = false;
			while(itr2.hasNext() && !(addeds)) {
				Course coc = itr2.next();
				if(coc.name().equals(cn)) {
					coc.addStud(curr2);
					addeds = true;
					}
			}
				if (!(addeds)){
					Course course = new Course(cn,ct);
					allCourses.add(course);
					course.addStud(curr2);
					}
				}
		br1.close();
			}
			
	private static void answerQueries(String f3,LList<Student> s1,LList<Course> c1,LList<Hostel> h1, LList<Dept> d1) throws IOException  {
		LList<Student> allStudents = s1;
		LList<Course>  allCourses = c1;
		LList<Hostel> allHostels = h1; 
		LList<Dept> allDept = d1;
		LList<String> results = new LList<String>();
		
		File file2 = new File(f3); 
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		
		String st2;
		while ((st2 = br2.readLine()) != null) {
			String [] arr2 = st2.split(" ");
			
			if(arr2[0].equals("COURSETITLE")){
				Iterator<Course> itr1 = allCourses.positions1();
				boolean found = false;
				while((itr1.hasNext())&&!(found)) {
					Course c = itr1.next();
					if(c.title().equals(arr2[1])){
						results.add(c.name());
						found = true;
					}
				}
				if(found == false) {
					results.add("Course Unavailable");
				}
			}
			
			else if(arr2[0].equals("INFO")) {
				Iterator<Student> itr2 = allStudents.positions1();
				boolean found = false;
				Student cur = new Student("P","A","R","A");
				while(itr2.hasNext()&&!(found)) {
					Student s = itr2.next();
					if(s.entryNo().equals(arr2[1])||(s.name().contentEquals(arr2[1]))) {
						found = true;
						cur = s;
					}
				}
				String result = "";
				if(found == false) {
					result = "Student Unavailable";
				}
				else {
			    result = cur.entryNo()+" "+cur.name()+" "+cur.hostel()+" "+cur.department()+" "+cur.cgpa()+" ";
				}
				int numCourses = cur.getLL().count();
				CourseGrade_ [] arr = new CourseGrade_[numCourses];
				Iterator<CourseGrade_> itr3 = cur.courseList();
				int index = 0;
				while(itr3.hasNext()) {
					CourseGrade_ cg = itr3.next();
					arr[index] = cg;
					index += 1;
				}
				
				for(int i=0;i<numCourses;i++) {
					for(int j=0;j<numCourses-1-i;j++) {
						CourseGrade_ temp;
						int res = arr[j].coursetitle().compareTo(arr[j+1].coursetitle());
						if(res>0) {
							temp = arr[j+1];
							arr[j+1] = arr[j];
							arr[j] = temp;
						}
					}
				}
				
				for(int i=0;i<numCourses;i++) {
					GradeInfo gr = (GradeInfo)arr[i].grade();
					result += arr[i].coursetitle() + " "+gr.grade();
					if(!(i==numCourses-1)){
					result +=" ";
					}
				}
			results.add(result);
			}
			
			else {
				
				String entity = arr2[2];
				String entry = arr2[1];
				String result = "";
				Iterator<Course> itr1 = allCourses.positions1();
				Iterator<Dept> itr2 = allDept.positions1();
				Iterator<Hostel> itr3 = allHostels.positions1();
				
				boolean found = false;
				while(itr1.hasNext() && !(found)) {
					Course c = itr1.next();
					if(c.title().equals(entity)) {
						found = true;
						Iterator<Student_> itr4 = c.studentList();
						while(itr4.hasNext()) {
							Student_ s = itr4.next();
							if(!s.entryNo().equals(entry)) {
							result += s.entryNo();
							if(itr4.hasNext())
								result += " ";
							}
						}
					}
				}
				
				while(itr2.hasNext() && !(found)) {
					Dept d = itr2.next();
					if(d.name().equals(entity)) {
						found = true;
						Iterator<Student_> itr4 = d.studentList();
						while(itr4.hasNext()) {
							Student_ s = itr4.next();
							if(!s.entryNo().equals(entry)) {
							result += s.entryNo();
							if(itr4.hasNext())
								result += " ";
							}
						}
					}
				}
				
				while(itr3.hasNext() && !(found)) {
					Hostel h = itr3.next();
					if(h.name().equals(entity)) {
						found = true;
						Iterator<Student_> itr4 = h.studentList();
						while(itr4.hasNext()) {
							Student_ s = itr4.next();
							if(!s.entryNo().equals(entry)) {
							result += s.entryNo();
							if(itr4.hasNext())
								result += " ";
							}
						}
					}
				}
				String [] arr4 = result.split(" ");
				String uresult = "";
				for(int i=0;i<arr4.length;i++) {
					for(int j=0;j<arr4.length-1-i;j++) {
						String temp = "";
						int res = arr4[j].compareTo(arr4[j+1]);
						if(res>0) {
							temp = arr4[j+1];
							arr4[j+1] = arr4[j];
							arr4[j] = temp;
						}
					}
				}
				for(int i=0;i<arr4.length;i++) {
					uresult += arr4[i];
					if(i != arr4.length-1)
						uresult += " ";
				}
				results.add(uresult);
				}
			
		}
		br2.close();
		String [] arr3 = new String[results.count()];
		Iterator<String> itr5 = results.positions1();
		int index = 0;
		while(itr5.hasNext()) {
			String s = itr5.next();
			arr3[index] = s;
			index += 1;
		}
		
		for(int i=arr3.length-1;i>=0;i--) {
			System.out.println(arr3[i]);
		}
	}
	
	
	public static void main(String[] args) throws IOException{

		  LList<Hostel> allHostels = new LList<Hostel>();
		  LList<Dept> allDept = new LList<Dept>();
		  LList <Course> allCourses = new LList<Course>();
		  LList<Student> allStudents = new LList<Student>();
		  
		  getData(args[0],args[1],allStudents,allCourses,allHostels,allDept);
		  answerQueries(args[2],allStudents,allCourses,allHostels,allDept);
		  
			}	
	}

