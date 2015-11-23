package DataReadingAndWriting;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Bryan on 11/20/2015.
 */
public interface EmployeeWriter {
    public void writeEmployees(Collection<Employee> emps, String outfile, boolean append) throws IOException;
    public void writeEmployee(Employee emp, String outfile, boolean append) throws IOException;
}
