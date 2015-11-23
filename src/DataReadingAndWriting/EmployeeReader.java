package DataReadingAndWriting;

import java.util.List;

/**
 * Created by Bryan on 11/18/2015.
 */
public interface EmployeeReader {
    public List<Employee> readEmployees(String infile);
}
