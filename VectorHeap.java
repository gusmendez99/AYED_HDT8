/**
 * @author: Gustavo Mendez
 * @author: Luis Urbina
 * @version 1.0
 * @since 01/04/2019
 *
 * Bailey, D. A. (2002).
 * Java structures: Data structures in Java for the principled programmer.
 * McGraw-Hill.
 */

import java.util.Vector;

/**
 * The VectorHeap class stores all the E-type objects in ascendant order with the Priority Queue implementation
 * one can easily remove the smaller E-type object. Also, the operation of adding is merely from this class
 * @param <E> the Object that the VectorHeap data will store
 */
public class VectorHeap<E extends Comparable<E>> implements PriorityQueue<E>
{

    protected Vector<E> data; // the data, kept in heap order

    /**
     * Constructs an empty VectorHeap
     */
    public VectorHeap(){
        data = new Vector<>();
    }

    /**
     * Constructs a new VectorHeap object with an especific vector given to store the data
     * @param v the vector to use as data
     */
    public VectorHeap(Vector<E> v)
    {
        int i;
        data = new Vector<E>(v.size());
        for (i = 0; i < v.size(); i++){
            add(v.get(i));
        }
    }

    /**
     * Gets the parent of a given position in the data vector
     * @param i the index of the current heap
     * @return the integer of the index of the parent
     */
    protected static int parent(int i){
        return (i-1)/2;
    }

    /**
     * Gets the left child of the specific node according to a given index in the data vector
     * @param i the index of the heap
     * @return the index of the left child of the heap
     */
    protected static int left(int i){
        return 2*i+1;
    }

    /**
     * Gets the right child of the specific node according to a given index in the data vector
     * @param i the index in the data vector of the heap to get the child from
     * @return the right child of that specific node
     */
    protected static int right(int i){
        return (2*i+1) + 1;
    }

    /**
     * Used to move a new added leaf up until it reaches the apropiate position to match the heaps rules
     * @param leaf the index of the new leaf
     */
    protected void percolateUp(int leaf){
        int parent = parent(leaf);
        E value = data.get(leaf);
        while (leaf > 0 &&
                (value.compareTo(data.get(parent)) < 0))
        {
            data.set(leaf,data.get(parent));
            leaf = parent;
            parent = parent(leaf);
        }
        data.set(leaf,value);
    }

    /**
     * Adds a new E-type value to the vectorHeap
     * @param value the E-type object  that will be added to the vectorHeap
     */
    public void add(E value){
        data.add(value);
        percolateUp(data.size()-1);
    }

    /**
     * Function to push the root down until the root is located in the correct position to make the vectorHeap oredered
     * @param root the index in of the vectorHeap of the root
     */
    protected void pushDownRoot(int root){
        int heapSize = data.size();
        E value = data.get(root);
        while (root < heapSize) {
            int childpos = left(root);
            if (childpos < heapSize)
            {
                if ((right(root) < heapSize) &&
                        ((data.get(childpos+1)).compareTo
                                (data.get(childpos)) < 0))
                {
                    childpos++;
                }
                // Assert: childpos indexes smaller of two children
                if ((data.get(childpos)).compareTo
                        (value) < 0)
                {
                    data.set(root,data.get(childpos));
                    root = childpos;
                } else {
                    data.set(root,value);
                    return;
                }
            } else { // at a leaf! insert and halt
                data.set(root,value);
                return;
            }
        }
    }

    /**
     * Removes the smallest object in the vectorHeap, and uses the pushDownRoot method to rearrange the splay tree and
     * thus make the vectorHeap be in order again
     * @return the minimum value in the heap if it does exist, according to the priority queue implementation
     * otherwise returns null
     */
    public E remove(){
        if (isEmpty()==false){
            E minVal = getFirst();
            data.set(0,data.get(data.size()-1));
            data.setSize(data.size()-1);
            if (data.size() > 1) pushDownRoot(0);
            return minVal;
        }
        else{
            return null;
        }

    }

    /**
     * Gets the minimum value in the vectorHeap but without rearranging the data in the SplayTree
     * @return the patient with the maximum priority
     */
    @Override
    public E getFirst() {
        return data.get(0);
    }

    /**
     * Verifies if the vectorHeap is empty or not
     * @return true if it is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Gets the size of the vectorHeap
     * @return an int representing the total size of the tree
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Removes all the elements form the vectorHeap
     */
    @Override
    public void clear() {
        int i=0;
        while(data.isEmpty()==false){
            data.remove(i);
            i++;
        }
    }
}