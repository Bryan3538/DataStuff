package DataReadingAndWritingTests;

import DataReadingAndWriting.Employee;
import DataReadingAndWriting.EmployeeReader;
import DataReadingAndWriting.EmployeeReaderFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Bryan on 11/20/2015.
 */
public class EmployeeJsonObjectParserTest {

    private static final String FILENAME = "jsonparsertestfile.json";

    @Test
    public void testReadEmployees() throws Exception {
        int expectedSize = 3;
        Employee firstEmp = new Employee(1, "Luke", "Skywalker", 1, 1);
        Employee lastEmp = new Employee(3, "Han", "Solo", -1, -1);
        EmployeeReader reader = EmployeeReaderFactory.newInstance(EmployeeReaderFactory.JSONOBJ);
        List<Employee> emps = reader.readEmployees(FILENAME);

        assertEquals(expectedSize, emps.size());
        assertTrue(emps.get(0).equals(firstEmp));
        assertTrue(emps.get(emps.size() - 1).equals(lastEmp));
    }
}