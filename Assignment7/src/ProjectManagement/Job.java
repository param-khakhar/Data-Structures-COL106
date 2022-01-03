package ProjectManagement;

public class Job implements JobReport_,Comparable<Job> {
	
	private String user;
	private String name;
	private int runTime;
	private String project;
	private String Status;
	private int Priority;
	private int completionTime;
	private int arrivalTime;
	private Project Project;
	private User User;
	
	Job(String s,String t,String u1,String p){
		project = t;
		user = u1;
		name = s;
		runTime = Integer.parseInt(p);
		Status = "REQUESTED";
	}
	
	
	public void setProject(Project p) {
		Project = p;
	}
	
	public Project getProject() {
		return Project;
	}
	
	public void setUser(User u) {
		User = u;
	}
	
	public User getUser() {
		return User;
	}
	
	public void setArrivalTime(int arr) {
		arrivalTime = arr;
	}
	
	public int arrival_time() {
		return arrivalTime;
	}
	
	public String user() {
		return user;
	}
	
	public void setCompletionTime(int t) {
		completionTime = t;
	}
	
	public int completion_time() {
		return completionTime;
	}
	
	public String project_name() {
		return project;
	}
	
	public int getPriority() {
		return Priority;
	}
	
	public void setPriority(int s) {
		Priority = s;
	}
	
	public void setStatus(String s) {
		Status = s;
	}
	
	public String getStatus() {
		return Status;
	}
	
	public String toString() {
		if(completionTime==0) {
		return "Job{user='"+user+"', project='"+project+"', jobstatus="+Status+", execution_time="+runTime+", end_time="+"null"+", name='"+name+"'}";}
		return "Job{user='"+user+"', project='"+project+"', jobstatus="+Status+", execution_time="+runTime+", end_time="+completionTime+", name='"+name+"'}";
	}
	
	public String getName() {
		return name;
	}
	
	public int getRunTime() {
		return runTime;
	}
	
	public int budget() {
		return runTime;
	}

    @Override
    public int compareTo(Job job) {
        if(Priority < job.getPriority()) {
        	return -1;
        }
        else if(Priority > job.getPriority()) {
        	return 1;
        }
        else {
        	return 0;
        }
    }
}
