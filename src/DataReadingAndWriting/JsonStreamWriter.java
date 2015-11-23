package DataReadingAndWriting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bryan on 11/20/2015.
 */
public class JsonStreamWriter implements EmployeeWriter {
    
    @Override
    public void writeEmployees(Collection<Employee> emps, String outfile, boolean append) throws IOException {
        //I'll get around to it!
    }

    @Override
    public void writeEmployee(Employee emp, String outfile, boolean append) throws IOException {
        ArrayList<Employee> emps = new ArrayList<>(1);
        emps.add(emp);
        writeEmployees(emps, outfile, append);
    }
}
