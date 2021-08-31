import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.transform.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
          if (args.length != 2) {
              System.out.println("Нужно 2 параметра!");
                return;
           }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File f = new File(args[0]);
            InputStream is = new FileInputStream(f);
            Document doc = builder.parse(is);
            Element studentElement = doc.getDocumentElement();
            System.out.println(studentElement.getTagName());
            Student student = new Student(studentElement.getAttribute("lastname"));
            System.out.println(studentElement.getAttribute("lastname"));

            ArrayList<Subject> subjects = new ArrayList<Subject>();
            NodeList subNodeList = studentElement.getElementsByTagName("subject");
            for (int i = 0; i < subNodeList.getLength(); i++) {
                Element subElem = (Element) subNodeList.item(i);
                System.out.println(subElem.getAttribute("title") + "=" + Integer.parseInt(subElem.getAttribute("mark")));
                Subject subject = new Subject(subElem.getAttribute("title"), Integer.parseInt(subElem.getAttribute("mark")));
                subjects.add(subject);
            }
            student.setSubjects(subjects);

            NodeList avgNL = studentElement.getElementsByTagName("average");
            Node avgN = avgNL.item(0);
            double avgXML = Double.parseDouble(avgN.getTextContent());
            System.out.println(avgXML);
            student.checkAverage();

            if (avgXML != student.getAverage()) {
                System.out.println(student.getAverage());
                Document doc1 = builder.newDocument();
                Element studentEl1 = doc1.createElement("student");
                doc1.appendChild(studentEl1);
                studentEl1.setAttribute("lastname",student.getLastname());
                for (int i = 0; i < student.getSubjects().size(); i++)
                {
                    Element subEl1 = doc1.createElement("subject");
                    subEl1.setAttribute("title",student.getSubjects().get(i).getTitle());
                    subEl1.setAttribute("mark",String.valueOf(student.getSubjects().get(i).getMark()));
                studentEl1.appendChild(subEl1);
                }
                Element avgEl1 = doc1.createElement("average");
                Text avgText = doc1.createTextNode(String.valueOf(student.getAverage()));
                studentEl1.appendChild(avgEl1);
                avgEl1.appendChild(avgText);

                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.METHOD,"xml");
                t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
                t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"student.dtd");
                t.transform(new DOMSource(doc1),new StreamResult(new FileOutputStream(args[1])));
            }
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace(); }
        catch (SAXException | IOException e) {
            e.printStackTrace(); }
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
