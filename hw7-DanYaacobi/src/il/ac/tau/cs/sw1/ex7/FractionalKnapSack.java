package il.ac.tau.cs.sw1.ex7;
import java.util.*;




public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;
    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value, relation;
        Item(double w, double v) {
            weight = w;
            value = v;
            relation = v/w;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
        lst.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.relation > o2.relation) return -1;
                if (o1.relation < o2.relation) return 1;
                return 0;
            }
        });
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
        Item temp = new Item(capacity - sum(candidates_lst), element.value);
        return ((sum(candidates_lst) + element.weight) <= capacity) || ((sum(candidates_lst) + temp.weight) <= capacity);
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
        Item temp = new Item(capacity - sum(candidates_lst), element.value);
            if(sum(candidates_lst)+element.weight <= capacity) {
                candidates_lst.add(element);
            }
            else if ((sum(candidates_lst) + temp.weight) <= capacity)
                candidates_lst.add(temp);
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        return sum(candidates_lst) == capacity;
    }

    private double sum(List<Item> lst)
    {
        double sum = 0;
        for(Item item : lst)
            sum += item.weight;
        return sum;
    }

}


