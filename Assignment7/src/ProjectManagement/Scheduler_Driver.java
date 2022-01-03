package ProjectManagement;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import PriorityQueue.Auxiliary;
import PriorityQueue.MaxHeap;
import RedBlack.RBTree;
import Trie.Trie;
import Trie.TrieNode;

public class Scheduler_Driver extends Thread implements SchedulerInterface {

    public static void main(String[] args) throws IOException {
    	
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }
    
    Trie<Project> projectTrie = new Trie<Project>();
    MaxHeap<Job> jobs = new MaxHeap<Job>();
    Trie<Job> jobsTrie = new Trie<Job>(); 
    Trie<User> users = new Trie<User>();
    ArrayList<User> userHeap = new ArrayList<User>();
    int globalTime;
    ArrayList<Job> completed = new ArrayList<Job>();
    ArrayList<Job> notCompleted = new ArrayList<Job>();
    LinkedList<Auxiliary<Job>> NotReady = new LinkedList<Auxiliary<Job>>();
    MaxHeap<Project> projectHeap = new MaxHeap<Project>();
    String budgPro;

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }
    /** TO BE IMPLEMENTED
     * ASSIGNMENT 5 **/
    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
    	ArrayList<User> use = mergeSort(userHeap,0,userHeap.size()-1);
    	ArrayList<UserReport_> use1 = new ArrayList<>();
        int i=0;
        
        while(use.size()>i && top>0) {
        	use1.add(use.get(i));
        	top -= 1;
        	i += 1;
        }
        
        return use1;
    }

    @Override
    public void timed_flush(int waittime) {
    	ArrayList<Auxiliary<Job>> notExecuted = new ArrayList<Auxiliary<Job>>();
    	Auxiliary<Job> currAux;
    	Job currJob;
    	int wait;
    	int epoch = globalTime;
    	
    	while(jobs.getSize()>0) {
    		
    		currAux = jobs.extractMax2();
    		currJob = currAux.getSecond();
    		wait = epoch-currJob.arrival_time();
    		if((wait>=waittime)&&(currJob.budget()<=currJob.getProject().getBudget())) {
//    			System.out.println("FOUND JOB");
				currJob.setStatus("COMPLETED");
				currJob.getProject().insertFinishedJob(currJob);
//				System.out.println("FOUND JOB1");
				currJob.getUser().insertFinishedJob(currJob);
//				System.out.println("FOUND JOB2");
				currJob.getUser().incrementConsumption(currJob.budget());
//				System.out.println("FOUND JOB3");
				globalTime += currJob.budget();
				currJob.setCompletionTime(globalTime);
				currJob.getProject().setBudget(currJob.getProject().getBudget()-currJob.budget());
				completed.add(currJob);
				
    		}
    		else {
    				notExecuted.add(currAux);
    		}
    	}
    	
    	for(int i=0;i<notExecuted.size();i++) {
    		jobs.getHeap().add(notExecuted.get(i));
    	}
    	jobs.BuildMaxHeap();
    }
    
    public void timed_handle_user(String name) {
    	
    	User u = new User(name);
    	users.insert(name, u);
    	userHeap.add(u);
    }
    
    public void timed_handle_job(String[] cmd) {
    	
    	//System.out.println("Creating job");
    	String s = cmd[2];
    	TrieNode<Project> t = projectTrie.search(s);
    	TrieNode<User> u = users.search(cmd[3]);
    	if(u == null) {
    		//System.out.println("No such user exists: "+cmd[3]);
    	}
    	if(t == null) {
    		//System.out.println("No such project exists. "+cmd[2]);
    	}
    	else if((t!=null)&&(u!=null)) 
    	{
    		int n = t.getValue().getPriority();
    		Job j = new Job(cmd[1],cmd[2],cmd[3],cmd[4]);
    		j.setPriority(n);
    		j.setArrivalTime(globalTime);
    		jobs.insert(j);
    		j.setProject(t.getValue());
    		j.setUser(u.getValue());
    		t.getValue().insertUnFinishedJob(j);
    		u.getValue().insertUnFinishedJob(j);
    		jobsTrie.insert(j.getName(), j);
    	}
    	
    }
    public void timed_handle_project(String[] cmd) {
    	
    	Project p = new Project(cmd[1],cmd[2],cmd[3]);
    	projectTrie.insert(p.getName(),p);
    	projectHeap.insert(p);
    	
    }
    
    public void timed_run_to_completion() {
    	
    	while(jobs.getSize()>0) {
        	Auxiliary<Job> currAux;
        	Job currJob;
        	boolean executed = false;
        	while(!executed) {
        		
        		currAux = jobs.extractMax2();
        		if(currAux!=null) {
        			currJob = currAux.getSecond();
        			Project currProject = projectTrie.search(currJob.project_name()).getValue();
        		
        			if(currJob.getRunTime()<=currProject.getBudget()) {
        				currJob.setStatus("COMPLETED");
        				globalTime += currJob.getRunTime();
        				currJob.setCompletionTime(globalTime);
        				currProject.setBudget(currProject.getBudget()-currJob.getRunTime());
        				currProject.insertFinishedJob(currJob);
        				currJob.getUser().insertFinishedJob(currJob);
        				currJob.getUser().incrementConsumption(currJob.budget());
        				completed.add(currJob);
        				executed = true;
        			}
        	
        			else {
        				currJob.setStatus("REQUESTED");
        				NotReady.add(currAux);
        				}
        		}
        		else {
        			executed = true;
        			}
        		}
    		}
        	Iterator<Auxiliary<Job>> l = NotReady.iterator();
        	Auxiliary<Job> preAux;
        	
        	while(l.hasNext()) {
        		
    			preAux = l.next();
    			notCompleted.add(preAux.getSecond());
    			l.remove();
        	}   	
    }
    
    private ArrayList<JobReport_> handle_new_priority(String s) {
    	
    	int size = NotReady.size();
    	int priority = Integer.parseInt(s);
    	ArrayList<JobReport_> res = new ArrayList<JobReport_>();
    	Iterator<Auxiliary<Job>> itr = NotReady.iterator();
    	while(itr.hasNext()) {
    		Job j = itr.next().getSecond();
    		if(j.getPriority()>=priority) {
    			res.add(j);
    		}
    	}
    	size = jobs.getSize();
    	for(int i=1;i<=size;i++) {
    		Job j = jobs.getHeap().get(i).getSecond();
    		if(j.getPriority()>=priority) {
    			res.add(j);
    		}
    	}
        return res;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
    	
    	String projectName = cmd[1];
    	String userName = cmd[2];
    	TrieNode<Project> p = projectTrie.search(projectName);
    	TrieNode<User> u = users.search(userName);
    	ArrayList<JobReport_> res = new ArrayList<JobReport_>();
    	if(p!=null && u!=null) {
    		ArrayList<Job> jobs = p.getValue().projectUser(userName);
    		int lower = Integer.parseInt(cmd[3]);
    		int upper = Integer.parseInt(cmd[4]);
    	
    	ArrayList<Integer> limits = BSearch(jobs,lower,upper);
    	if(limits.size()>0) {
    	int resLow = limits.get(0);
    	int resUp = limits.get(1);
    	for(int i=resLow;i<=resUp;i++) {
    		Job j = jobs.get(i);
    		res.add(j);
    	}
    	}
    }
    //System.out.println("Project user");
    //printArray(res);
    return res;	
    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
        
    	String name = cmd[1];
    	TrieNode<User> u1 = users.search(name);
    	ArrayList<JobReport_> res = new ArrayList<JobReport_>();
    	if(u1!=null) {
    	User u = u1.getValue();
    	ArrayList<Job> jobs = u.getUnFinished();
    	int lower = Integer.parseInt(cmd[2]);
    	int upper = Integer.parseInt(cmd[3]);
    	ArrayList<Integer> limits = BSearch(jobs,lower,upper);
    	int resLow = limits.get(0);
    	int resUp = limits.get(1);
    	for(int i=resLow;i<=resUp;i++) {
    		res.add(jobs.get(i));
    	}
    	}
    	return res;
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
    	

    	String name = cmd[1];
    	int lower = Integer.parseInt(cmd[2]);
    	int upper = Integer.parseInt(cmd[3]);
    	TrieNode<Project> TNProj = projectTrie.search(name);
    	ArrayList<JobReport_> res = new ArrayList<JobReport_>();
    	if(TNProj != null) {
    	ArrayList<Job> jobs = TNProj.getValue().getJobs();
    	ArrayList<Integer> limits = BSearch(jobs,lower,upper);
    	if(limits.size()>0) {
    	int resLow = limits.get(0);
    	int resUp = limits.get(1);
    	for(int i=resLow;i<=resUp;i++) {
    		res.add(jobs.get(i));
    	}
    	}
    	}
    	return res;
    }


    // END OF ASSIGNMENT 5
    public void schedule() {
            execute_a_job();
    }

    public void run_to_completion() {
    	
    	while(jobs.getSize()>0) {
        	Auxiliary<Job> currAux;
        	Job currJob;
        	boolean executed = false;
        	System.out.println("Running code");
        	System.out.println("Remaining jobs: "+jobs.getSize());
        	while(!executed) {
        		
        		currAux = jobs.extractMax2();
        		if(currAux!=null) {
        			currJob = currAux.getSecond();
        			Project currProject = projectTrie.search(currJob.project_name()).getValue();
        			System.out.println("Executing: "+currJob.getName()+" from: "+currJob.project_name());
        		
        			if(currJob.getRunTime()<=currProject.getBudget()) {
        				currJob.setStatus("COMPLETED");
        				globalTime += currJob.getRunTime();
        				currJob.setCompletionTime(globalTime);
        				currProject.setBudget(currProject.getBudget()-currJob.getRunTime());
        				currProject.insertFinishedJob(currJob);
        				currJob.getUser().insertFinishedJob(currJob);
        				currJob.getUser().incrementConsumption(currJob.budget());
        				System.out.println("Project: "+currProject.getName()+" budget remaining: "+currProject.getBudget());
        				completed.add(currJob);
        				executed = true;
        				System.out.println("System execution completed");
        			}
        	
        			else {
        				currJob.setStatus("REQUESTED");
        				System.out.println("Un-sufficient budget.");
        				NotReady.add(currAux);
        				}
        		}
        		else {
        			executed = true;
        			}
        		}
    		}
        	Iterator<Auxiliary<Job>> l = NotReady.iterator();
        	Auxiliary<Job> preAux;
        	
        	while(l.hasNext()) {
        		
    			preAux = l.next();
    			notCompleted.add(preAux.getSecond());
    			l.remove();
        	}
    	}

    public void print_stats() {
    	
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+completed.size());
    	for(int i=0;i<completed.size();i++) {
    		Job j = completed.get(i);
    		System.out.println(j);
    	}
    	
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	while(projectHeap.getSize()>0) {
    		Project p = projectHeap.extractMax();
    		ArrayList<Job> jobs = p.getJobs();
    		for(int i=0;i<jobs.size();i++) {
    			Job j = jobs.get(i);
    			if(j.getStatus().equals("REQUESTED")) {
    				System.out.println(j);
    			}
    		}
    	}
       	System.out.println("Total unfinished jobs: "+notCompleted.size());
       	System.out.println("--------------STATS DONE---------------");
       	
    }

    public void handle_add(String[] cmd) {
    	
    	System.out.println("ADDING Budget");
    	String find = cmd[1];
    	int n = Integer.parseInt(cmd[2]);
    	TrieNode<Project> p = projectTrie.search(find);
    	if(p!=null) {
    	p.getValue().setBudget(p.getValue().getBudget()+n);
    	budgPro = cmd[1]; 
    	Auxiliary<Job> preAux;
		Iterator<Auxiliary<Job>> l = NotReady.iterator();
		while(l.hasNext()) {
			preAux = l.next();
			if(preAux.getSecond().project_name().equals(budgPro)) {
				//jobs.insert2(preAux);
				jobs.getHeap().add(preAux);
				l.remove();
			}
		}
		jobs.BuildMaxHeap();
    	}
    	else {
    	System.out.println("No such project exists. "+cmd[2]);
    	}
    }

    public void handle_empty_line() {
       schedule();
    }


    public void handle_query(String key) {
    	
    	System.out.println("Querying");
    	TrieNode<Job> enquiry3 = jobsTrie.search(key);
    	if(enquiry3 != null) {
    		String s = enquiry3.getValue().getStatus();
    		if(s.equals("REQUESTED")) {
    		System.out.println(key+": "+"NOT FINISHED");
    		}
    		else {
    			System.out.println(key+": "+"COMPLETED");
    		}
    	}
    	else {
    		System.out.println(key+": NO SUCH JOB");
    	}
    }

    public void handle_user(String name) {
    	
    	System.out.println("Creating user");
    	User u = new User(name);
    	users.insert(name, u);
    	userHeap.add(u);
    }

    public void handle_job(String[] cmd) {
    	
    	System.out.println("Creating job");
    	String s = cmd[2];
    	TrieNode<Project> t = projectTrie.search(s);
    	TrieNode<User> u = users.search(cmd[3]);

    	if(t == null) {
    		System.out.println("No such project exists. "+cmd[2]);
    	}
    	
    	else if(u == null) {
    		System.out.println("No such user exists: "+cmd[3]);
    	}
    	else if((t!=null)&&(u!=null)) 
    	{
    		int n = t.getValue().getPriority();
    		Job j = new Job(cmd[1],cmd[2],cmd[3],cmd[4]);
    		j.setPriority(n);
    		j.setArrivalTime(globalTime);
    		jobs.insert(j);
    		j.setProject(t.getValue());
    		j.setUser(u.getValue());
    		t.getValue().insertUnFinishedJob(j);
    		u.getValue().insertUnFinishedJob(j);
    		jobsTrie.insert(j.getName(), j);
    	}
    }

    public void handle_project(String[] cmd) {
    	
    	System.out.println("Creating project");
    	Project p = new Project(cmd[1],cmd[2],cmd[3]);
    	projectTrie.insert(p.getName(),p);
    	projectHeap.insert(p);
    	//projectList.add(p);
    }

    public void execute_a_job() {
    	
    	System.out.println("Running code");
    	Auxiliary<Job> currAux;
    	Job currJob;
    	boolean executed = false;
    		System.out.println("Remaining jobs: "+jobs.getSize());
    		while((!executed)&&(jobs.getSize()>0)) {
    			
    			currAux = jobs.extractMax2();
    			currJob = currAux.getSecond();
    			Project currProject = currJob.getProject();
    			
    			System.out.println("Executing: "+currJob.getName()+" from: "+currJob.project_name());
    		
    			if(currJob.getRunTime()<=currProject.getBudget()) {
    				currJob.setStatus("COMPLETED");
    				currProject.insertFinishedJob(currJob);
    				currJob.getUser().insertFinishedJob(currJob);
    				currJob.getUser().incrementConsumption(currJob.budget());
    				globalTime += currJob.budget();
    				currJob.setCompletionTime(globalTime);
    				currProject.setBudget(currProject.getBudget()-currJob.budget());
    				System.out.println("Project: "+currProject.getName()+" budget remaining: "+currProject.getBudget());
    				completed.add(currJob);
    				executed = true;

    			}
   
    			else {
    				System.out.println("Un-sufficient budget.");
    				NotReady.add(currAux);
    			}
    		}
    		//userHeap.BuildMaxHeap();
    		System.out.println("Execution cycle completed");
    	}
    
    
    public void printArray(ArrayList arr) {
    	for(int i=0;i<arr.size();i++) {
    		System.out.println(arr.get(i));
    	}
    }
    
    public ArrayList<Integer> BSearch(ArrayList<Job> jobs,int lower,int upper) {
    	
    	ArrayList<Integer> res = new ArrayList<Integer>();
    	if(jobs.size()>0) {
    	
    	int size = jobs.size();
    	int mid = (size-1)/2;
    	Job currJob = jobs.get(mid);
    	int high = size-1;
    	int currTime = currJob.arrival_time();
    	int low = 0;
    	int resLow = 0;
    	int resUp = 0;
    	while(low<high) {
    		if(currTime==lower) {
    			if((mid==0)||(mid!=0 && jobs.get(mid-1).arrival_time()<currTime)){
    				resLow = mid;
    				break;
    			}
    			else{
    				high = mid-1;
    				mid = (high+low)/2;
    			}
    		}
    		else if(currTime < lower) {
    			low = mid + 1;
    			mid = (low+high)/2;
    		}
    		else {
    			high = mid-1;
    			mid = (low+high)/2;
    		}
    		currJob = jobs.get(mid);
			currTime = currJob.arrival_time();
    	}
    	if(low==high) {
    		if(currTime<lower) {
    			resLow = mid+1;
    		}
    		else {
    			resLow = mid;
    		}
    	}
    	
    	 mid = (size-1)/2;
    	 currJob = jobs.get(mid);
    	 high = size-1;
    	 currTime = currJob.arrival_time();
    	 low = 0;
    	
    	while(low<high) {
    		if(currTime==upper) {
				if((mid==size-1)||(mid!=size-1 && jobs.get(mid+1).arrival_time()>currTime)){
    				resUp = mid;
    				break;
    			}
    			else{
    				low = mid+1;
    				mid = (high+low)/2;
    			}
    		}
    		else if(currTime < upper) {
    			low = mid + 1;
    			mid = (low+high)/2;
    		}
    		else {
    			high = mid-1;
    			mid = (low+high)/2;
    		}
    		currJob = jobs.get(mid);
			currTime = currJob.arrival_time();
    	}
    	if(low==high) {
    		if(currTime>upper) {
    			resUp = mid-1;
    		}
    		else {
    			resUp = mid;
    		}
    	}
    	res.add(resLow);
    	res.add(resUp);
    	}
    	return res;
    }
    
    public ArrayList<User> mergeSort(ArrayList<User> u, int l,int r){
    	if(u.size()>0 && l==r){
    		ArrayList<User> use = new ArrayList<>();
    		use.add(u.get(l));
    		return use;
    	}
    	else if(u.size()==0) {
    		return new ArrayList<>();
    	}
    	int mid = (l+r)/2;
    	ArrayList<User> left = mergeSort(u,l,mid);
    	ArrayList<User> right = mergeSort(u,mid+1,r);
    	return  merge(left,right);
    }
    
    public ArrayList<User> merge(ArrayList<User> left,ArrayList<User> right) {
    	ArrayList<User> res = new ArrayList<>();
    	int i = 0;
    	int j= 0;
    	int m = left.size();
    	int n = right.size();
    	
    	while(i+j<m+n) {
    		if(i==m) {
    			res.add(right.get(j));
    			j += 1;
    		}
    		else if(j==n) {
    			res.add(left.get(i));
    			i += 1;
    		}
    		else if(right.get(j).compareTo(left.get(i))>0) {
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
}
