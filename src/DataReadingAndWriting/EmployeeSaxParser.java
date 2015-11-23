package DataReadingAndWriting;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bryan on 11/18/2015.
 */
public class EmployeeSaxParser implements EmployeeReader {

    @Override
    public List<Employee> readEmployees(String infile) {
        SAXHandler handler = new SAXHandler();
        SAXParser parser;
        SAXParserFactory parserFactor;

        try {
            parserFactor = SAXParserFactory.newInstance();
            parser = parserFactor.newSAXParser();
            parser.parse(infile, handler);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handler.emps;
    }

    private class SAXHandler extends DefaultHandler {

        private List<Employee> emps;
        private Employee emp;
        private String content;

        public SAXHandler() {
            emps = new ArrayList<>();
            emp = null;
            content = null;
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //using the switch statement to leave options open for later
            switch (qName) {
                //Create a new DataReadingAndWriting.Employee object when the start tag is found
                case "Employee":
                    emp = new Employee();
                    if (isNumber(attributes.getValue("id"), false))
                        emp.setId(Integer.parseInt(attributes.getValue("id")));
                    else
                        emp = null;
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            content = String.copyValueOf(ch, start, length).trim();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            //Letting emp be null allows for the skipping of malformed employees in the xml
            //Simple way to keep things running for these tests
            if(emp != null)
                switch (qName) {
                    case "Employee":
                        emps.add(emp);
                        break;
                    case "FirstName":
                        emp.setFirstName(content);
                    case "LastName":
                        emp.setLastName(content);
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
                }
        }

        private boolean isNumber(String str, boolean allowNegatives) {

            if (allowNegatives) {
                return str.matches("-?\\d+");
            } else {
                return str.matches("\\d+");
            }
        }
    }
}
