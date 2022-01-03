public static void merge(Node root1 , Node root2)
    {
            //add code here.
        Stack<Node> s1 = new Stack<Node>();
        Stack<Node> s2 = new Stack<Node>();
        ArrayList<Integer> t1 = new ArrayList<Integer>();
        ArrayList<Integer> t2 = new ArrayList<Integer>();
        ArrayList<Integer> res = new ArrayList<Integer>();
        
        Node curr1 = root1;
        while(curr1.left!=null){
            s1.push(curr1);
        }
        while(!s1.empty()){
            curr1 = s1.pop();
            t1.add(curr1.data);
            curr1 = s1.peek();
            if(curr1.right==null){
                while(s1.peek().right==null){
                    t1.add(s1.pop().data);
                }
            }
            curr1 = s1.peek().right;
            s1.push(curr1);
            while(curr1.left!=null){
                curr1 = curr1.left;
                s1.push(curr1);
            }
        }
        
        Node curr2 = root1;
        while(curr2.left!=null){
            s2.push(curr2);
        }
        while(!s2.empty()){
            curr2 = s2.pop();
            t2.add(curr2.data);
            curr2 = s2.peek();
            if(curr2.right==null){
                while(s2.peek().right==null){
                    t2.add(s2.pop().data);
                }
            }
            curr2 = s2.peek().right;
            s2.push(curr2);
            while(curr2.left!=null){
                curr2 = curr2.left;
                s2.push(curr2);
            }
        }
        
        int m = t1.size();
        int n = t2.size();
        int i = 0;
        int j = 0;
        while(!(i==m && j==n)){
            if(j == n){
                i += 1;
            }
            else if(i == m){
                res.add(t2.get(n));
                j += 1;
            }
            else if(t1.get(i)>t2.get(j)){
                res.add(t2.get(j));
                j += 1;
            }
            else{
                res.add(t1.get(i));
                i += 1;
            }
        }
    for(int w=0;w<res.size();w++){
        System.out.print(res.get(w)+" ");
        }
        System.out.println();
    }