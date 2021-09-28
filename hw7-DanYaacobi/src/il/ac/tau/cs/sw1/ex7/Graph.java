package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,...,n-1]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {

        lst.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
            if(o1.weight > o2.weight) return 1;
            if(o1.weight < o2.weight) return -1;
            if(o1.node1 > o2.node1) return 1;
            if(o1.node1 < o2.node1) return -1;
            if(o1.node2 > o2.node2) return 1;
            if(o1.node2 < o2.node2) return -1;
            return 0;
            }
        });
        return lst.iterator();
    }

    private boolean circle_check(List<Edge> candidates_lst, int node1, int node2) // recursion
    {
        boolean check = true;
        if(candidates_lst.size() == 0) // good stop condition -> empty list
            return true;
        for(Edge edge : candidates_lst) // bad stop condition -> element is in list
        {
            if((edge.node1 == node1 && edge.node2 == node2)||edge.node1 == node2 && edge.node2 == node1)
                return false;
        }
        List<Edge> temp = new ArrayList<Edge>(candidates_lst);// create new list to avoid concurrent error

        for(Edge edge : temp)
        {
            if(edge.node1 == node1)
            {
                candidates_lst.remove(edge);
                check = circle_check(candidates_lst,edge.node2,node2);
            }
            else if(edge.node2 == node1)
            {
                candidates_lst.remove(edge);
                check = circle_check(candidates_lst,edge.node1,node2);
            }
        }
        return check;

    }
    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        if (candidates_lst.size() <= n) {
            List<Edge> temp = new ArrayList<Edge>();
            for(Edge edge : candidates_lst)
                temp.add(edge);
            Collections.copy(temp,candidates_lst);
            return circle_check(temp, element.node1,element.node2);
        }
        return false;
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);

    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        boolean check = false;
        if(candidates_lst.size() == n)
        {
            for (int i = 0; i <= n; i++)
            {
                check = false;
                for (Edge edge : candidates_lst)
                {
                    if (edge.node1 == i || edge.node2 == i)
                        check = true;
                }
                if (!check)
                    break;
            }
        }
        return check;
    }
}
