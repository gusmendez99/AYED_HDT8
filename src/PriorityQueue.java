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
public interface PriorityQueue<E extends Comparable<E>>
{
    public E getFirst();
    // pre: !isEmpty()
    // post: returns the minimum value in priority queue

    public E remove();
    // pre: !isEmpty()
    // post: returns and removes minimum value from queue

    public void add(E value);
    // pre: value is non-null comparable
    // post: value is added to priority queue

    public boolean isEmpty();
    // post: returns true iff no elements are in queue

    public int size();
    // post: returns number of elements within queue

    public void clear();
    // post: removes all elements from queue
}
