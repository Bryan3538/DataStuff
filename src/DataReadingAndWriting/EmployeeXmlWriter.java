package DataReadingAndWriting;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bryan on 11/18/2015.
 */
public class EmployeeXmlWriter implements EmployeeWriter {

    public void writeEmployee(Employee emp, String outfile, boolean append) throws IOException {
        ArrayList<Employee> emps = new ArrayList<Employee>(1);
        emps.add(emp);
        writeEmployees(emps, outfile, append);
    }

    public void writeEmployees(Collection<Employee> emps, String outfile, boolean append) throws IOException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;
            Node root;
            Element newEmployee, firstName, lastName, deptno, locId;

            //create document from existing and get root of where employees are inserted
            if(new File(outfile).exists() && append) {
                doc = docBuilder.parse(new BufferedInputStream(new FileInputStream(outfile)));
                NodeList employeesElements = doc.getElementsByTagName("Employees");
                if(employeesElements.getLength() > 0)
                    root = employeesElements.item(0);
                else { //if the employees element doesn't exist, then create it
                    Element employees = doc.createElement("Employees");
                    root = doc.getFirstChild().appendChild(employees);
                }
            } else { //create a new document and add a fresh employees element to it
                doc = docBuilder.newDocument();
                Element employees = doc.createElement("Employees");
                root = doc.appendChild(employees);
            }

            for(Employee emp : emps) {
                //add the employee element to employees
                newEmployee = doc.createElement("Employee");
                newEmployee.setAttribute("id", Integer.toString(emp.getId()));
                root.appendChild(newEmployee);

                //add the employee's properties to its newly created element as child elements
                firstName = doc.createElement("FirstName");
                firstName.appendChild(doc.createTextNode(emp.getFirstName()));
                newEmployee.appendChild(firstName);

                lastName = doc.createElement("LastName");
                lastName.appendChild(doc.createTextNode(emp.getLastName()));
                newEmployee.appendChild(lastName);

                //these next two should probably be different, but I wanted to keep them simple
                //ideally you would validate this, set the ID's as attributes, and fill the element with some useful text
                deptno = doc.createElement("DepartmentNumber");
                deptno.appendChild(doc.createTextNode(Integer.toString(emp.getDepartmentNumber())));
                newEmployee.appendChild(deptno);

                locId = doc.createElement("LocationID");
                locId.appendChild(doc.createTextNode(Integer.toString(emp.getLocationId())));
                newEmployee.appendChild(locId);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outfile));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
