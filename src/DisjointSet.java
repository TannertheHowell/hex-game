public class DisjointSet {

    // Array initial set up
    private int [] ranks;

    // Constructor using a given size, ranks initially set to -1 for its size
    public DisjointSet(int size){
        ranks = new int[size];
        for (int i = 0; i < size; i++){
            ranks[i] = - 1;
        }
    }

    // Assuming node1 and node2 are not the same tree, negative number is size, positive number is parent
    public void union(int node1, int node2){
        node1 = find(node1);
        node2 = find(node2);

        // node2 is larger, so we add the size from node1 to node2 and make node 2 a new node
        if (ranks[node1] < ranks[node2]){
            ranks[node2] += ranks[node1];
            ranks[node1] = node2;
        } else{
            // node1 is equal or larger
            ranks[node1] += ranks[node2];
            ranks[node2] = node1;
        }
    }

    public int find(int node){
        // A negative value here would mean the node is a root node
        if (ranks[node] < 0){
            return node;
        } else{ // if it's not a root, then find the root, update the parent of the node to point to the root and return it
            ranks[node] = find(ranks[node]);
            return ranks[node];
        }
    }

}
