import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorHeapTest {

    @Test
    public void add() {
        VectorHeap<Patient> patientVectorHeap = new VectorHeap<>();
        patientVectorHeap.clear();
        patientVectorHeap.add(new Patient("Luis", "Brazo Roto", "C"));
        patientVectorHeap.add(new Patient("Alejandro", "Cortada", "D"));
        patientVectorHeap.add(new Patient("Pablo", "Apendicitis", "A"));
        assertEquals(false, patientVectorHeap.isEmpty());

    }

    @Test
    public void remove() {
        VectorHeap<Patient> patientVectorHeap = new VectorHeap<>();
        patientVectorHeap.clear();
        patientVectorHeap.add(new Patient("Luis", "Brazo Roto", "C"));
        patientVectorHeap.add(new Patient("Alejandro", "Cortada", "D"));
        patientVectorHeap.add(new Patient("Pablo", "Apendicitis", "A"));
        assertEquals("A", patientVectorHeap.remove().getCode());
    }
}