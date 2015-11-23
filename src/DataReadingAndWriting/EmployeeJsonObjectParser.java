package DataReadingAndWriting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

/**
 * Created by Bryan on 11/19/2015.
 */
public class EmployeeJsonObjectParser implements EmployeeReader{

    @Override
    public List<Employee> readEmployees(String infile) {
        List<Employee> emps = new ArrayList<>();
        Employee emp;

        try (InputStream is = new FileInputStream(infile);
                   JsonReader reader = Json.createReader(is)) {

            JsonObject obj = reader.readObject();
            JsonArray empArr = obj.getJsonArray("Employees");

            for(JsonObject empObj : empArr.getValuesAs(JsonObject.class)) {
                emp = new Employee();
                emp.setId(empObj.getInt("ID", -1));
                emp.setFirstName(empObj.getString("FirstName", null));
                emp.setLastName(empObj.getString("LastName", null));
                emp.setDepartmentNumber(empObj.getInt("DepartmentNumber", -1));
                emp.setLocationId(empObj.getInt("LocationID", -1));

                if(emp.getId() > 0 && emp.getFirstName() != null && emp.getLastName() != null)
                    emps.add(emp);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(JsonParsingException jpe) {
            System.err.println("Json file is malformed.");
            System.err.println(jpe.getMessage());
            System.err.println(jpe.getStackTrace());
        }

        return emps;
    }
}
