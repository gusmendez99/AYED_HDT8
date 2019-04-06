/**
 * @author: Gustavo Mendez
 * @author: Luis Urbina
 * @version 1.0
 * @since 01/04/2019
 *
 * Reference:
 * Bailey, D. A. (2002).
 * Java structures: Data structures in Java for the principled programmer.
 * McGraw-Hill.
 */
public class Patient implements Comparable<Patient>{
    private String name;
    private String description;
    private String code;

    public Patient(String name, String description, String code) {
        super();
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(Patient arg0) {
        return this.code.compareTo(arg0.code);
    }

    @Override
    public String toString() {
        return name + ", " + description + ", " + code;
    }
}