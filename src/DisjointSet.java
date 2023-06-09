public class DisjointSet {

    // Array initial set up
    private int [] parentOrSize;

    // Constructor using a given size, ranks initially set to -1 for its size
    public DisjointSet(int size){
        parentOrSize = new int[size];
        for (int i = 0; i < size; i++){
            parentOrSize[i] = - 1;
        }
    }

    public void union(int node1, int node2){
        node1 = find(node1);
        node2 = find(node2);

        if(node1 == node2){
            return;
        }

        // node2 is larger, so we add the size from node1 to node2 and make node 2 a new node
        if (parentOrSize[node1] < parentOrSize[node2]){
            parentOrSize[node2] += parentOrSize[node1];
            parentOrSize[node1] = node2;
        } else{
            // node1 is equal or larger
            parentOrSize[node1] += parentOrSize[node2];
            parentOrSize[node2] = node1;
        }
    }

    public int find(int node){
        // A negative value here would mean the node is a root node
        if (parentOrSize[node] < 0){
            return node;
        } else{ // if it's not a root, then find the root, update the parent of the node to point to the root and return it
            parentOrSize[node] = find(parentOrSize[node]);
            return parentOrSize[node];
        }
    }

}
