package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadJson {

	private Map<String, Double> prop = new HashMap<String, Double>();
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Color color, fillColor;
	private Point position;

	public ArrayList<Shape> load(String path,
			List<Class<? extends Shape>> classes) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String sCurrentLine;
			String[] total = new String[1000];
			int counter = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				total[counter++] = sCurrentLine;
			}
			for (int i = 1; i < counter - 1; i++) {
				String[] temp = total[i].split(":");
				String Type = "", o = temp[temp.length - 1];
				char[] u = o.toCharArray(), p, w;
				for (int j = 0; j < o.length(); j++) {
					if (u[j] != '"' && u[j] != ',') {
						Type += u[j];
					}
				}
				String t = "", ttt = "", ttttt = "";
				i++;
				temp = total[i].split(",");
				u = temp[0].toCharArray();
				p = temp[1].toCharArray();
				for (int j = temp[0].length() - 1; j > 0; j--) {
					if (u[j] == '[')
						break;
					t += u[j];
				}
				t = new StringBuffer(t).reverse().toString();
				for (int j = 0; j < temp[1].length(); j++) {
					if (p[j] == ']')
						break;
					ttt += p[j];
				}
				int x = Integer.parseInt(t), y = Integer.parseInt(t);
				if (x == -1 && y == -1) {
					position = new Point();
				} else {
					position = new Point(x, y);
				}
				i++;
				t = "";
				ttt = "";
				ttttt = "";
				temp = total[i].split(",");
				u = temp[0].toCharArray();
				p = temp[1].toCharArray();
				w = temp[2].toCharArray();
				for (int j = temp[0].length() - 1; j >= 0; j--) {
					if (u[j] == '[')
						break;
					t += u[j];
				}
				t = new StringBuffer(t).reverse().toString();
				ttt = temp[1];
				for (int j = 0; j < temp[2].length(); j++) {
					if (w[j] == ']')
						break;
					ttttt += w[j];
				}

				int r = Integer.parseInt(t), g = Integer.parseInt(ttt), b = Integer
						.parseInt(ttttt);
				if (r == -1 && g == -1 && b == -1) {
					color = new Color(0, 0, 0, 0);
				} else {
					color = new Color(r, g, b);
				}

				i++;
				t = "";
				ttt = "";
				ttttt = "";
				temp = total[i].split(",");
				u = temp[0].toCharArray();
				p = temp[1].toCharArray();
				w = temp[2].toCharArray();
				for (int j = temp[0].length() - 1; j >= 0; j--) {
					if (u[j] == '[')
						break;
					t += u[j];
				}
				t = new StringBuffer(t).reverse().toString();
				ttt = temp[1];
				for (int j = 0; j < temp[2].length(); j++) {
					if (w[j] == ']')
						break;
					ttttt += w[j];
				}
				r = Integer.parseInt(t);
				g = Integer.parseInt(ttt);
				b = Integer.parseInt(ttttt);
				if (r == -1 && g == -1 && b == -1) {
					fillColor = new Color(0, 0, 0, 0);
				} else {
					fillColor = new Color(r, g, b);
				}
				i++;
				temp = total[i].split(",");
				Shape s = null;
				try {
					s = (Shape) Class.forName(Type).newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Map<String, Double> ooo = new HashMap<String, Double>();
				int coun = 0;
				if (temp.length == 1 && temp[0].contains("null")) {
					prop = new HashMap<String, Double>();
				} else {
					for (String key : s.getProperties().keySet()) {
						String tot = "";
						if (coun == 0) {
							u = temp[0].toCharArray();
							for (int k = temp[0].length() - 1; k >= 0; k--) {
								if (u[k] == '[')
									break;
								tot += u[k];
							}
							// System.out.println(tot);
							tot = new StringBuffer(tot).reverse().toString();
						} else if (coun == s.getProperties().size() - 1) {
							u = temp[coun].toCharArray();
							for (int k = 0; k < temp[coun].length(); k++) {
								if (u[k] == ']')
									break;
								tot += u[k];
							}
						} else {
							tot = temp[coun];
						}
						coun++;
						Double e = null;
						e = Double.valueOf(tot);
						ooo.put(key, e);
						;
					}
					prop = new HashMap<String, Double>(ooo);
				}
				Shape ss = null;
				try {
					ss = (Shape) Class.forName(Type).newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (ss != null) {
					ss.setColor(color);
					ss.setProperties(prop);
					ss.setFillColor(fillColor);
					ss.setPosition(position);
					shapes.add(ss);
				}
			}
		}

		catch (IOException e) {
			throw new RuntimeException();
		}
		return shapes;

	}
}
