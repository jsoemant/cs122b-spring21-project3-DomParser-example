import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomParserExample {

    List<Employee> employees = new ArrayList<>();
    Document dom;

    public void runExample() {

        //parse the xml file and get the dom object
        parseXmlFile();

        //get each employee element and create a Employee object
        parseDocument();

        //Iterate through the list and print the data
        printData();

    }

    private void parseXmlFile() {
        //get the factory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = documentBuilder.parse("employees.xml");

        } catch (ParserConfigurationException | SAXException | IOException error) {
            error.printStackTrace();
        }
    }

    private void parseDocument() {
        //get the root element
        Element docEle = dom.getDocumentElement();

        //get a nodelist of <employee> elements
        NodeList nl = docEle.getElementsByTagName("Employee");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element element = (Element) nl.item(i);

                //get the Employee object
                Employee employee = getEmployee(element);

                //add it to list
                employees.add(employee);
            }
        }
    }

    /**
     * It takes an employee element and read the values in, create
     * an Employee object and return it
     */
    private Employee getEmployee(Element element) {

        //for each <employee> element get text or int values of 
        //name ,id, age and name
        String name = getTextValue(element, "Name");
        int id = getIntValue(element, "Id");
        int age = getIntValue(element, "Age");
        String type = element.getAttribute("type");

        //Create a new Employee with the value read from the xml nodes
        return new Employee(name, id, age, type);
    }

    /**
     * It takes an XML element and the tag name, look for the tag and get
     * the text content
     * i.e for <employee><name>John</name></employee> xml snippet if
     * the Element points to employee node and tagName is name I will return John
     */
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    /**
     * Calls getTextValue and returns a int value
     */
    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    /**
     * Iterate through the list and print the
     * content to console
     */
    private void printData() {

        System.out.println("Total parsed " + employees.size() + " employees");

        for (Employee employee : employees) {
            System.out.println("\t" + employee.toString());
        }
    }

    public static void main(String[] args) {
        //create an instance
        DomParserExample domParserExample = new DomParserExample();

        //call run example
        domParserExample.runExample();
    }

}
