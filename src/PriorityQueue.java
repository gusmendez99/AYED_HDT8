/**
 * @author: Gustavo Mendez
 * @author: Luis Urbina
 *
 * Bailey, D. A. (2002).
 * Java structures: Data structures in Java for the principled programmer.
 * McGraw-Hill.
 */
public interface PriorityQueue<E extends Comparable<E>>
{
    public E getFirst();

    public E remove();

    public void add(E value);

    public boolean isEmpty();

    public int size();

    public void clear();
}
