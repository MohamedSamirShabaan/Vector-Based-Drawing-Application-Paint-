package eg.edu.alexu.csd.oop.draw;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SaveXml {

	@SuppressWarnings("unused")
	public void save(String path, ArrayList<Shape> shapes) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("paint");
			Element staff, firstname;
			Attr attr;
			doc.appendChild(rootElement);
			for (int i = 0; i < shapes.size(); i++) {
				Shape s = shapes.get(i);
				staff = doc.createElement("shape");
				rootElement.appendChild(staff);
				attr = doc.createAttribute("id");
				attr.setValue(s.getClass().toString()
						.substring(6, s.getClass().toString().length()));
				staff.setAttributeNode(attr);

				if (s.getPosition() == null) {
					Element temp = doc.createElement("Position");
					temp.appendChild(doc.createTextNode("null"));
					staff.appendChild(temp);
				} else {
					Element temp = doc.createElement("Position");
					firstname = doc.createElement("x");
					firstname.appendChild(doc.createTextNode(s.getPosition().x
							+ ""));
					temp.appendChild(firstname);
					firstname = doc.createElement("y");
					firstname.appendChild(doc.createTextNode(s.getPosition().y
							+ ""));
					temp.appendChild(firstname);
					staff.appendChild(temp);
				}
				if (s.getColor() == null) {
					Element temp = doc.createElement("Color");
					temp.appendChild(doc.createTextNode("null"));
					staff.appendChild(temp);
				} else {
					Element temp = doc.createElement("Color");
					firstname = doc.createElement("r");
					firstname.appendChild(doc.createTextNode(s.getColor()
							.getRed() + ""));
					temp.appendChild(firstname);
					firstname = doc.createElement("b");
					firstname.appendChild(doc.createTextNode(s.getColor()
							.getBlue() + ""));
					temp.appendChild(firstname);
					firstname = doc.createElement("g");
					firstname.appendChild(doc.createTextNode(s.getColor()
							.getGreen() + ""));
					temp.appendChild(firstname);
					staff.appendChild(temp);
				}
				if (s.getFillColor() == null) {
					Element temp = doc.createElement("FillColor");
					temp.appendChild(doc.createTextNode("null"));
					staff.appendChild(temp);
				} else {
					Element temp = doc.createElement("FillColor");
					firstname = doc.createElement("rr");
					firstname.appendChild(doc.createTextNode(s.getFillColor()
							.getRed() + ""));
					temp.appendChild(firstname);
					firstname = doc.createElement("bb");
					firstname.appendChild(doc.createTextNode(s.getFillColor()
							.getBlue() + ""));
					temp.appendChild(firstname);
					firstname = doc.createElement("gg");
					firstname.appendChild(doc.createTextNode(s.getFillColor()
							.getGreen() + ""));
					temp.appendChild(firstname);
					staff.appendChild(temp);
				}

				Element temp = doc.createElement("Prop");
				boolean get = true;
				try {
					for (String key : s.getProperties().keySet()) {
						try {

						} catch (Exception e) {
							get = false;
							break;
						}
					}
				} catch (Exception e) {
					get = false;
				}
				if (!get) {
					temp.appendChild(doc.createTextNode("null"));
				} else {
					for (String key : s.getProperties().keySet()) {
						firstname = doc.createElement(key);
						firstname.appendChild(doc.createTextNode(String
								.valueOf(s.getProperties().get(key) + "")));
						temp.appendChild(firstname);
					}
				}
				staff.appendChild(temp);
			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			throw new RuntimeException(pce);
		} catch (TransformerException tfe) {
			throw new RuntimeException(tfe);
		}
	}
}
