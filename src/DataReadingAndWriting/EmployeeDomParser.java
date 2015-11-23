package DataReadingAndWriting;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bryan on 11/18/2015.
 */
public class EmployeeDomParser implements EmployeeReader {

    public List<Employee> readEmployees(String infile) {
        LinkedList<Employee> emps = new LinkedList<>();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(infile);
            NodeList employees;

            if (doc.getElementsByTagName("Employees").getLength() > 0)
                employees = doc.getElementsByTagName("Employees").item(0).getChildNodes();
            else
                return null;

            for (int i = 0; i < employees.getLength(); i++) {
                Node employeeNode = employees.item(i);

                if (employeeNode instanceof Element) {
                    Employee emp = new Employee();
                    String id = employeeNode.getAttributes().getNamedItem("id").getNodeValue();
                    if (id.matches("\\d+"))
                        emp.setId(Integer.parseInt(id));
                    else
                        continue; //I would normally do some error handling here depending on the application, but I want this to be simple

                    NodeList employeeProperties = employeeNode.getChildNodes();

                    for (int j = 0; j < employeeProperties.getLength(); j++) {
                        Node property = employeeProperties.item(j);

                        if (property instanceof Element) {
                            String text = property.getLastChild().getTextContent().trim();

                            switch (property.getNodeName()) {
                                case "FirstName":
                                    emp.setFirstName(text);
                                    break;
                                case "LastName":
                                    emp.setLastName(text);
                                    break;
                                case "DepartmentNumber":
                                    if (text.matches("-?\\d+"))
                                        emp.setDepartmentNumber(Integer.parseInt(text));
                                    else
                                        emp.setDepartmentNumber(-1);
                                    break;
                                case "LocationID":
                                    if (text.matches("-?\\d+"))
                                        emp.setLocationId(Integer.parseInt(text));
                                    else
                                        emp.setLocationId(-1);
                                    break;
                            }
                        }
                    }
                    emps.add(emp);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emps;
    }
}
