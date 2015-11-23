package DataReadingAndWriting;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bryan on 11/18/2015.
 */
public class EmployeeStaxParser implements EmployeeReader {
    @Override
    public List<Employee> readEmployees(String infile) {
        List<Employee> emps = null;
        Employee emp = null;
        String content = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try {
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(infile));

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (reader.getLocalName().equals("Employees")) {
                            emps = new ArrayList<>();
                        } else if (reader.getLocalName().equals("Employee")) {
                            if (isNumber(reader.getAttributeValue(0), false)) {
                                emp = new Employee();
                                emp.setId(Integer.parseInt(reader.getAttributeValue(0)));
                            } else {
                                emp = null;
                            }
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        content = reader.getText().trim();
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if (emp != null)
                            switch (reader.getLocalName()) {
                                case "FirstName":
                                    emp.setFirstName(content);
                                    break;
                                case "LastName":
                                    emp.setLastName(content);
                                    break;
                                case "DepartmentNumber":
                                    if (isNumber(content, true))
                                        emp.setDepartmentNumber(Integer.parseInt(content));
                                    else
                                        emp.setDepartmentNumber(-1);
                                    break;
                                case "LocationID":
                                    if (isNumber(content, true))
                                        emp.setLocationId(Integer.parseInt(content));
                                    else
                                        emp.setLocationId(-1);
                                    break;
                                case "Employee":
                                    emps.add(emp);
                                    break;
                            }
                        break;
                }
            }


        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return emps;
    }

    private boolean isNumber(String str, boolean allowNegatives) {

        if (allowNegatives) {
            return str.matches("-?\\d+");
        } else {
            return str.matches("\\d+");
        }
    }
}
