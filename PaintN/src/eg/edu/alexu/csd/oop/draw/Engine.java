package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Engine extends JPanel implements DrawingEngine,
		MouseMotionListener, MouseListener {

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<ArrayList<Shape>> stack = new ArrayList<ArrayList<Shape>>();
	private Stack<ArrayList<Shape>> undo = new Stack<ArrayList<Shape>>();
	@SuppressWarnings("rawtypes")
	private List<Class> used = new LinkedList<Class>();
	private List<Class<? extends Shape>> classes = new LinkedList<Class<? extends Shape>>();
	private List<Class<? extends Shape>> plugins = new LinkedList<Class<? extends Shape>>();
	private ShapesFactory factory = new ShapesFactory();
	private Map<String, Double> prop = new HashMap<String, Double>();
	private Color color = Color.BLACK, fillColor = Color.cyan;
	private Point position;
	private int sIndex = -2, selectIndex = -2;
	private boolean setClear = false, loadedyet = false, selectedMode = false,
			clicked = false;
	private boolean paintSelected = false;
	private ClassPathFinder classPathFinder = new ClassPathFinder();
	private static Engine engine;

	private Engine() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public static Engine create() {

		if (engine == null)
			engine = new Engine();
		return engine;
	}

	public static void destoryInstance() {
		engine = null;
	}

	protected void initialize() {
		classes = getSupportedShapes();
		try {
			used.add(Class.forName("eg.edu.alexu.csd.oop.draw.Line"));
			used.add(Class.forName("eg.edu.alexu.csd.oop.draw.Circle"));
			used.add(Class.forName("eg.edu.alexu.csd.oop.draw.Ellipse"));
			used.add(Class.forName("eg.edu.alexu.csd.oop.draw.Rectangle"));
			used.add(Class.forName("eg.edu.alexu.csd.oop.draw.Square"));
			used.add(Class.forName("eg.edu.alexu.csd.oop.draw.Triangle"));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class Not Found !!!" + e);
		}
	}

	@Override
	public void refresh(Graphics canvas) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shapes.size(); i++) {
			paintSelected = false;
			if (canvas instanceof Graphics2D) {
				((Graphics2D) canvas).setStroke(new BasicStroke(2));
				if (selectIndex == i && selectedMode && clicked) {
					Graphics2D g = ((Graphics2D) canvas);
					g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_ROUND, 2f, new float[] { 3f }, 3f));
				}
			}
			if (sIndex == i && selectedMode)
				paintSelected = true;
			shapes.get(i).draw((canvas));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addShape(Shape shape) {
		// TODO Auto-generated method stub

		shapes.add(shape);
		stack.add((ArrayList<Shape>) shapes.clone());
		undo.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub
		int index = shapes.indexOf(shape);
		if (index != -1) {
			shapes.remove(index);
			stack.add((ArrayList<Shape>) shapes.clone());
		}
		undo.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub
		if (shapes.size() > 0) {
			int index = shapes.indexOf(oldShape);
			if (index != -1) {
				shapes.set(index, newShape);
				stack.add((ArrayList<Shape>) shapes.clone());
			} else
				throw new RuntimeException();
		}
		undo.clear();
	}

	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
		Shape[] backShapes = new Shape[shapes.size()];
		for (int i = 0; i < shapes.size(); i++) {
			backShapes[i] = shapes.get(i);
		}
		return backShapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		java.util.List<Class<? extends Shape>> list = new LinkedList<Class<? extends Shape>>();
		list = classPathFinder.getFiles(System.getProperty("java.class.path"));
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		if (stack.size() > 0 && stack.size() <= 20 && !loadedyet) {
			shapes.clear();
			undo.push((ArrayList<Shape>) stack.get(stack.size() - 1).clone());
			stack.remove(stack.size() - 1);
			if (stack.size() > 0) {
				shapes = (ArrayList<Shape>) stack.get(stack.size() - 1).clone();
			} else
				shapes.clear();
			repaint();
		}
		loadedyet = false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		if (undo.size() > 0 && stack.size() <= 20) {
			shapes.clear();
			stack.add((ArrayList<Shape>) undo.pop().clone());
			shapes = (ArrayList<Shape>) stack.get(stack.size() - 1).clone();
			repaint();
		}
	}

	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		String[] str = path.split(Pattern.quote("."));
		String type = str[str.length - 1];
		type = type.toLowerCase();
		if (type.equals("xml")) {
			SaveXml save = new SaveXml();
			save.save(path, shapes);
		} else if (type.equals("json")) {
			SaveJson save = new SaveJson();
			save.save(path, shapes);
		} else {
			throw new RuntimeException("UnExpected type not XML or JSON !!!");
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		String[] str = path.split(Pattern.quote("."));
		String type = str[str.length - 1];
		type = type.toLowerCase();
		if (type.equals("xml")) {
			shapes.clear();
			undo.clear();
			stack.clear();
			LoadXml load = new LoadXml();
			shapes = load.load(path, classes);
			stack.add((ArrayList<Shape>) shapes.clone());
		} else if (type.equals("json")) {
			shapes.clear();
			undo.clear();
			stack.clear();
			LoadJson load = new LoadJson();
			shapes = load.load(path, classes);
			stack.add((ArrayList<Shape>) shapes.clone());
		} else {
			throw new RuntimeException("UnExpected type not XML or JSON !!!");
		}
		repaint();
		loadedyet = true;
	}

	protected void delete() {
		if (selectedMode && selectIndex >= 0) {
			removeShape(shapes.get(selectIndex));
			repaint();
		}
	}

	protected void setColor(Color col) {
		this.color = col;
		if (selectedMode && clicked) {
			Shape s;
			try {
				s = (Shape) shapes.get(selectIndex).clone();
				s.setColor(color);
				updateShape(shapes.get(selectIndex), s);
			} catch (CloneNotSupportedException e) {
			}
			repaint();
		}

	}

	protected void setFillColor(Color col) {
		this.fillColor = col;
		if (selectedMode && clicked) {
			Shape s;
			try {
				s = (Shape) shapes.get(selectIndex).clone();
				s.setFillColor(fillColor);
				updateShape(shapes.get(selectIndex), s);
			} catch (CloneNotSupportedException e) {
			}
			repaint();
		}
	}

	protected Shape getSelectedShape() {
		if (selectedMode && clicked && selectIndex >= 0) {
			return shapes.get(selectIndex);
		}
		return null;
	}

	protected void updateSelected(Shape old) {

		Shape newShape = null;
		try {
			newShape = (Shape) shapes.get(selectIndex).clone();
			newShape.setProperties(prop);
			updateShape(shapes.get(selectIndex), newShape);
		} catch (CloneNotSupportedException e) {
		}

		repaint();
	}

	protected void newShape(String shapeName) {
		Shape s = factory.makeShape(shapeName, classes);
		if (s != null) {
			s.setColor(color);
			s.setProperties(prop);
			s.setFillColor(fillColor);
			s.setPosition(position);
			addShape(s);
		}
		repaint();
	}

	@Override
	public void paint(Graphics g2d) {
		super.paintComponent(g2d);
		refresh(g2d);
	}

	protected void clearAll() {
		setClear = true;
		selectedMode = false;
		clicked = false;
		undo.clear();
		stack = new ArrayList<ArrayList<Shape>>();
		shapes.clear();
		repaint();
	}

	protected void getPlugins() {
		for (int i = 0; i < classes.size(); i++) {
			boolean found = false;
			for (int j = 0; j < used.size(); j++) {
				if (classes.get(i).getName().equals(used.get(j).getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				plugins.add(classes.get(i));
			}
		}
	}

	@SuppressWarnings({ "resource", "rawtypes" })
	protected List<Class<? extends Shape>> dynamicLoad(String filePath) {

		List<Class<? extends Shape>> clazz = new LinkedList<Class<? extends Shape>>();
		try {
			JarFile jarFile = new JarFile(filePath);
			Enumeration e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + filePath + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			while (e.hasMoreElements()) {
				JarEntry je = (JarEntry) e.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(".class")) {
					continue;
				}
				String className = je.getName().substring(0,
						je.getName().length() - 6);
				className = className.replace('/', '.');
				Shape c = (Shape) Class.forName(className, true, cl)
						.newInstance();
				clazz.add(c.getClass());
				classes.add(c.getClass());
				String destdir = "bin/";
				java.io.File fl = new java.io.File(destdir, je.getName());
				if (!fl.exists()) {
					fl.getParentFile().mkdirs();
					fl = new java.io.File(destdir, je.getName());
				}
				if (je.isDirectory()) {
					continue;
				}
				java.io.InputStream is = jarFile.getInputStream(je);
				java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
				while (is.available() > 0) {
					fo.write(is.read());
				}
				fo.close();
				is.close();
			}
		} catch (Exception e) {
		}
		return clazz;
	}

	protected void setSelectedMode(boolean selectedMode) {
		this.selectedMode = selectedMode;
	}

	protected void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	protected boolean isPaintSelected() {
		return paintSelected;
	}

	protected List<Class<? extends Shape>> getClasses() {
		return classes;
	}

	protected ShapesFactory getFactory() {
		return factory;
	}

	protected Map<String, Double> getProp() {
		return prop;
	}

	protected void setProp(Map<String, Double> prop) {
		this.prop = prop;
	}

	protected void setPluginsMap(List<Class<? extends Shape>> plugins) {
		this.plugins = plugins;
	}

	protected List<Class<? extends Shape>> getPluginsMap() {
		return this.plugins;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		clicked = true;
		if (selectedMode) {
			selectIndex = sIndex;
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		Double minDist = null;
		sIndex = -2;
		if (shapes.size() != 0 && shapes.get(0).getPosition() != null) {
			minDist = Math.sqrt((shapes.get(0).getPosition().x - x)
					* (shapes.get(0).getPosition().x - x)
					+ (shapes.get(0).getPosition().y - y)
					* (shapes.get(0).getPosition().y - y));
			sIndex = 0;
		}
		for (int i = 1; i < shapes.size(); i++) {
			Point p = new Point();
			p = shapes.get(i).getPosition();
			if (p == null)
				continue;
			int side = (p.x - x) * (p.x - x);
			int side2 = (p.y - y) * (p.y - y);
			double dist = Math.sqrt(side + side2);
			if (dist < minDist) {
				minDist = dist;
				sIndex = i;
			}
		}
		repaint();
	}

}
