package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadXml {

	private Map<String, Double> prop = new HashMap<String, Double>();
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Color color, fillColor;
	private Point position;

	public ArrayList<Shape> load(String path, List<Class<? extends Shape>> classes) {
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			doc.getDocumentElement().getNodeName();
			boolean pos = false, col = false, fillcol = false, pp = false;
			NodeList nList = doc.getElementsByTagName("shape");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				pos = false;
				col = false;
				fillcol = false;
				pp = false;
				Node nNode = nList.item(temp);
				nNode.getNodeName();
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String TypeS = eElement.getAttribute("id");
					String tem = eElement.getElementsByTagName("Position")
							.item(0).getTextContent();
					if (tem.equals("null")) {
						pos = true;
					} else {
						position = new Point();
						String ttt = eElement.getElementsByTagName("x").item(0)
								.getTextContent();
						position.x = Integer.parseInt(ttt);
						ttt = eElement.getElementsByTagName("y").item(0)
								.getTextContent();
						position.y = Integer.parseInt(ttt);
					}
					tem = eElement.getElementsByTagName("Color").item(0)
							.getTextContent();
					if (tem.equals("null")) {
						col = true;
					} else {
						String ttt = eElement.getElementsByTagName("r").item(0)
								.getTextContent();
						int r = Integer.parseInt(ttt);
						ttt = eElement.getElementsByTagName("b").item(0)
								.getTextContent();
						int b = Integer.parseInt(ttt);
						ttt = eElement.getElementsByTagName("g").item(0)
								.getTextContent();
						int g = Integer.parseInt(ttt);
						color = new Color(r, g, b);
					}
					tem = eElement.getElementsByTagName("FillColor").item(0)
							.getTextContent();
					if (tem.equals("null")) {
						fillcol = true;
					} else {
						String ttt = eElement.getElementsByTagName("rr")
								.item(0).getTextContent();
						int r = Integer.parseInt(ttt);
						ttt = eElement.getElementsByTagName("bb").item(0)
								.getTextContent();
						int b = Integer.parseInt(ttt);
						ttt = eElement.getElementsByTagName("gg").item(0)
								.getTextContent();
						int g = Integer.parseInt(ttt);
						fillColor = new Color(r, g, b);
					}

					tem = eElement.getElementsByTagName("Prop").item(0)
							.getTextContent();
					Shape s = (Shape)Class.forName(TypeS).newInstance();
					Map<String, Double> ooo = new HashMap<String, Double>();
					String tot = eElement.getElementsByTagName("Prop")
							.item(0).getTextContent();
					if (tot.contains("null")) {
						pp = true;
					} else {
						for (String key : s.getProperties().keySet()) {
							tot = eElement.getElementsByTagName(key)
									.item(0).getTextContent();
							if (!tot.equals("null")) {
								Double o = null;
								o = Double.valueOf(tot);
								ooo.put(key, o);
							} else {
								Double o = null;
								ooo.put(key, o);
							}
						}
						prop = new HashMap<String, Double>(ooo);
					}
					s = (Shape)Class.forName(TypeS).newInstance();
					if (!pos)
						s.setPosition(position);
					if (!col)
						s.setColor(color);
					if (!pp)
						s.setProperties(prop);
					if (!fillcol)
						s.setFillColor(fillColor);
					shapes.add(s);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return shapes;
	}
}
