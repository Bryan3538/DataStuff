package DataReadingAndWriting;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bryan on 11/20/2015.
 */
public class EmployeeJsonStreamParser implements EmployeeReader {

    @Override
    public List<Employee> readEmployees(String infile) {
        List<Employee> emps = null;
        Employee emp = null;


        try (InputStream is = new FileInputStream(infile);
             JsonParser parser = Json.createParser(is)) {

            while (parser.hasNext()) {
                JsonParser.Event e = parser.next();

                if (e == JsonParser.Event.KEY_NAME) {
                    switch (parser.getString()) {
                        case "Employees":
                            //if emps is null, then this is fresh and we get reading
                            if (emps == null) emps = new ArrayList<>();
                            else
                                return emps; //otherwise, we finished a list and return (there should only be one list / file)
                            break;
                        case "ID": //this is a fresh employee
                            if (isValidEmployeeObject(emp))
                                emps.add(emp);
                            emp = new Employee();
                            parser.next(); //get the id value into parser
                            if (parser.getString().matches("\\d+"))
                                emp.setId(Integer.parseInt(parser.getString()));
                            else
                                emp.setId(-1);
                            break;
                        case "FirstName":
                            parser.next();
                            if (emp != null) //it should never be null, but just to be safe
                                emp.setFirstName(parser.getString());
                            break;
                        case "LastName":
                            parser.next();
                            if (emp != null)
                                emp.setLastName(parser.getString());
                            break;
                        case "DepartmentNumber":
                            parser.next();
                            if (emp != null) {
                                if (parser.getString().matches("-?\\d+"))
                                    emp.setDepartmentNumber(Integer.parseInt(parser.getString()));
                                else
                                    emp.setDepartmentNumber(-1);
                            }
                            break;
                        case "LocationID":
                            parser.next();
                            if (emp != null) {
                                if (parser.getString().matches("-?\\d+"))
                                    emp.setLocationId(Integer.parseInt(parser.getString()));
                                else
                                    emp.setLocationId(-1);
                            }
                            break;
                        default:
                            parser.next(); //to skip values for any unexpected keys
                    }
                }


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return emps;
    }

    private boolean isValidEmployeeObject(Employee emp) {
        return (emp != null && emp.getId() > 0 &&
                !emp.getFirstName().equals(null) && !emp.getFirstName().trim().isEmpty() &&
                !emp.getLastName().equals(null) && !emp.getLastName().trim().isEmpty());
    }
}
