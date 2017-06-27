package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Paint extends JFrame implements ActionListener {

	private String filename;
	private JButton clear, lineBtn, circleBtn, rectangleBtn, squareBtn,
			rightTriangleBtn, ellipseBtn, triangleBtn, colorBtn, fillColorBtn,
			select, undo, redo, update, delete, saveXML, saveJSON, loadXML,
			loadJSON, loadClass, nB;
	private JPanel all, buttons;
	private int fields = 0, lastaddedat = 7;
	private boolean isSet = false;
	private Engine draw ;
	private ArrayList<JTextField> compon = new ArrayList<JTextField>();
	private ArrayList<JButton> newShapesButtons = new ArrayList<JButton>();

	public Paint(Engine draw2) {
		
		draw = draw2;
		all = new JPanel(null);
		buttons = new JPanel(null);
		add(all);
		ImageIcon clearall = new ImageIcon(this.getClass().getResource(
				"/clear.png"));
		ImageIcon selection = new ImageIcon(this.getClass().getResource(
				"/select.png"));
		ImageIcon undoo = new ImageIcon(this.getClass()
				.getResource("/undo.png"));
		ImageIcon pen = new ImageIcon(this.getClass().getResource("/Pen.png"));
		ImageIcon updat = new ImageIcon(this.getClass().getResource(
				"/update.png"));
		ImageIcon deleteImg = new ImageIcon(this.getClass().getResource(
				"/delete.png"));
		ImageIcon loadImg = new ImageIcon(this.getClass().getResource(
				"/load.png"));
		ImageIcon saveImg = new ImageIcon(this.getClass().getResource(
				"/save.png"));
		ImageIcon classImg = new ImageIcon(this.getClass().getResource(
				"/class.png"));
		ImageIcon redoo = new ImageIcon(this.getClass()
				.getResource("/redo.png"));
		ImageIcon line = new ImageIcon(this.getClass().getResource("/line.png"));
		ImageIcon square = new ImageIcon(this.getClass().getResource(
				"/Square.png"));
		ImageIcon rectangle = new ImageIcon(this.getClass().getResource(
				"/Rectangle.png"));
		ImageIcon circle = new ImageIcon(this.getClass().getResource(
				"/Circle.png"));
		ImageIcon ellipse = new ImageIcon(this.getClass().getResource(
				"/Ellipse.png"));
		ImageIcon triangle = new ImageIcon(this.getClass().getResource(
				"/ScaleneTriangle.png"));
		ImageIcon c = new ImageIcon(this.getClass().getResource("/bucket.png"));
		buttons.setBounds(0, 0, 75, 650);
		clear = new JButton(clearall);
		clear.addActionListener(this);
		clear.setBounds(0, 0, 75, 50);

		lineBtn = new JButton(line);
		lineBtn.setName("eg.edu.alexu.csd.oop.draw.Line");
		lineBtn.setBounds(0, 50, 75, 50);
		lineBtn.addActionListener(this);
		circleBtn = new JButton(circle);
		circleBtn.setName("eg.edu.alexu.csd.oop.draw.Circle");
		circleBtn.setBounds(0, 100, 75, 50);
		circleBtn.addActionListener(this);
		rectangleBtn = new JButton(rectangle);
		rectangleBtn.setName("eg.edu.alexu.csd.oop.draw.Rectangle");
		rectangleBtn.setBounds(0, 150, 75, 50);
		rectangleBtn.addActionListener(this);
		triangleBtn = new JButton(triangle);
		triangleBtn.setName("eg.edu.alexu.csd.oop.draw.Triangle");
		triangleBtn.setBounds(0, 200, 75, 50);
		triangleBtn.addActionListener(this);
		squareBtn = new JButton(square);
		squareBtn.setName("eg.edu.alexu.csd.oop.draw.Square");
		squareBtn.setBounds(0, 250, 75, 50);
		squareBtn.addActionListener(this);
		ellipseBtn = new JButton(ellipse);
		ellipseBtn.setName("eg.edu.alexu.csd.oop.draw.Ellipse");
		ellipseBtn.setBounds(0, 300, 75, 50);
		ellipseBtn.addActionListener(this);
		fillColorBtn = new JButton(c);
		fillColorBtn.setBounds(0, 350, 75, 50);
		fillColorBtn.addActionListener(this);
		colorBtn = new JButton(pen);
		colorBtn.addActionListener(this);
		colorBtn.setBounds(0, 400, 75, 50);
		select = new JButton(selection);
		select.setBounds(0, 450, 75, 50);
		select.addActionListener(this);
		undo = new JButton(undoo);
		undo.setBounds(0, 500, 75, 50);
		undo.addActionListener(this);
		redo = new JButton(redoo);
		redo.setBounds(0, 550, 75, 50);
		redo.addActionListener(this);
		update = new JButton("Udpdate", updat);
		update.setBounds(1025, 0, 120, 50);
		update.addActionListener(this);
		delete = new JButton("delete", deleteImg);
		delete.setBounds(1025, 50, 120, 50);
		delete.addActionListener(this);
		saveXML = new JButton("XML", saveImg);
		saveXML.setBounds(1025, 100, 120, 50);
		saveXML.addActionListener(this);
		saveJSON = new JButton("JSON", saveImg);
		saveJSON.setBounds(1025, 150, 120, 50);
		saveJSON.addActionListener(this);
		loadXML = new JButton("XML", loadImg);
		loadXML.setBounds(1025, 250, 120, 50);
		loadXML.addActionListener(this);
		loadJSON = new JButton("JSON", loadImg);
		loadJSON.setBounds(1025, 200, 120, 50);
		loadJSON.addActionListener(this);
		loadClass = new JButton("NewShape", classImg);
		loadClass.setBounds(1025, 300, 120, 50);
		loadClass.addActionListener(this);
		all.add(loadClass);

		buttons.add(select);
		buttons.add(clear);
		buttons.add(lineBtn);
		buttons.add(circleBtn);
		buttons.add(rectangleBtn);
		buttons.add(triangleBtn);
		buttons.add(squareBtn);
		buttons.add(ellipseBtn);
		buttons.add(fillColorBtn);
		buttons.add(redo);
		buttons.add(undo);
		buttons.add(colorBtn);
		all.add(buttons);
		all.add(delete);
		all.add(update);
		all.add(saveXML);
		all.add(saveJSON);
		all.add(loadJSON);
		all.add(loadXML);
		draw.setLayout(null);
		draw.setBounds(75, 0, 950, 600);
		draw.setBackground(Color.WHITE);
		all.add(draw);
		newShapesButtons.add(lineBtn);
		newShapesButtons.add(circleBtn);
		newShapesButtons.add(rectangleBtn);
		newShapesButtons.add(ellipseBtn);
		newShapesButtons.add(squareBtn);
		newShapesButtons.add(triangleBtn);
		newShapesButtons.add(rightTriangleBtn);
	}

	private void addPlugins() {
		draw.getPlugins();
		for (int i = 0; i < draw.getPluginsMap().size(); i++) {
			String[] arr = draw.getPluginsMap().get(i).getName()
					.split(Pattern.quote("."));
			String name = arr[arr.length - 1];
			JButton shapeBt = new JButton(name);
			shapeBt.setName(draw.getPluginsMap().get(i).getName());
			lastaddedat++;
			shapeBt.setBounds(1025, 350 + 50 * (lastaddedat - 8), 120, 50);
			all.add(shapeBt);
			all.revalidate();
			all.repaint();
			newShapesButtons.add(shapeBt);
			shapeBt.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == fillColorBtn) {
			Color color = JColorChooser.showDialog(null, "Choose a Color",
					Color.BLACK);
			draw.setFillColor(color);
		} else if (e.getSource() == colorBtn) {
			Color color = JColorChooser.showDialog(null, "Choose a Color",
					Color.BLACK);
			draw.setColor(color);
		} else if (e.getSource() == clear) {
			clearFields();
			draw.setSelectedMode( false);
			draw.setClicked(false);
			isSet = false;
			draw.clearAll();
		} else if (e.getSource() == select) {
			draw.setSelectedMode( true);
		} else if (e.getSource() == undo) {
			draw.setSelectedMode( false);
			draw.setClicked( false);
			draw.undo();
		} else if (e.getSource() == redo) {
			draw.setSelectedMode(false);
			draw.setClicked( false);
			draw.redo();
		} else if (e.getSource() == delete) {
			draw.delete();
		} else if (e.getSource() == update) {
			clearFields();

			final Shape shape = draw.getSelectedShape();
			if (shape != null) {
				draw.setProp( shape.getProperties());
				setField();
				isSet = true;
				nB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						int j = 0, error = 0;
						for (String key : draw.getProp().keySet()) {
							if (compon.size() == 0
									|| compon.get(j).getText().equals("")) {
								error = 1;
								break;
							}
							draw.getProp().put(key,
									Double.parseDouble(compon.get(j).getText()));
							j++;
						}
						if (error == 0) {
							draw.updateSelected(shape);
						}
					}
				});
			}
		} else if (e.getSource() == saveXML || e.getSource() == saveJSON) {
			draw.setSelectedMode (false);
			draw.setClicked( false);
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.dir")));
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				filename = selectedFile.getAbsolutePath();
				if (!filename.contains(Pattern.quote("."))
						&& e.getSource() == saveXML)
					filename += ".xml";
				if (!filename.contains(Pattern.quote("."))
						&& e.getSource() == saveJSON)
					filename += ".json";
				System.out.println(filename);
				draw.save(filename);
			}
		} else if (e.getSource() == loadXML || e.getSource() == loadJSON) {
			draw.setSelectedMode(false);
			draw.setClicked(false);
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.dir")));
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				filename = selectedFile.getAbsolutePath();
				draw.load(filename);
			}
		} else if (e.getSource() == loadClass) {
			draw.setSelectedMode(false );
			draw.setClicked(false);
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.dir")));
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				filename = selectedFile.getAbsolutePath();
				if (filename.contains(".jar")) {
					List<Class<? extends Shape>> newShape;

					newShape = draw.dynamicLoad(filename);
					if (newShape.size() == 0)
						JOptionPane.showMessageDialog(null,
								"INVALID CLASS TO ADD");
					for (int i = 0; i < newShape.size(); i++) {
						lastaddedat++;
						String[] arr = newShape.get(i).getName()
								.split(Pattern.quote("."));
						String className = arr[arr.length - 1];
						JButton newShapeBtn = new JButton(className);
						newShapeBtn.setName(newShape.get(i).getName());
						newShapeBtn.setBounds(1025,
								350 + 50 * (lastaddedat - 8), 120, 50);
						all.add(newShapeBtn);
						all.revalidate();
						all.repaint();
						newShapesButtons.add(newShapeBtn);
						newShapeBtn.addActionListener(this);
					}
				} else {
					JOptionPane.showMessageDialog(null, "INVALID CLASS TO ADD");
				}
			}
		}
		for (int i = 0; i < newShapesButtons.size(); i++) {
			if (e.getSource() == newShapesButtons.get(i)) {
				draw.setSelectedMode( false);
				draw.setClicked(false);
				final String className = newShapesButtons.get(i).getName();
				clearFields();

				Shape shappp = null;
				shappp = draw.getFactory().makeShape(newShapesButtons.get(i)
						.getName(), draw.getClasses());
				if (shappp.getProperties() == null) {
					draw.addShape(shappp);
					draw.repaint();
					break;
				}
				isSet = true;
				draw.setProp( shappp.getProperties());
				setField();

				nB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						int j = 0, error = 0;
						for (String key : draw.getProp().keySet()) {
							if (compon.size() == 0
									|| compon.get(j).getText().equals("")) {
								error = 1;
								break;
							}
							draw.getProp().put(key,
									Double.parseDouble(compon.get(j).getText()));
							j++;
						}
						if (error == 0) {
							draw.newShape(className);
						}
					}
				});
			}
		}
	}

	private void clearFields() {
		// TODO Auto-generated method stub
		if (isSet) {
			for (int i = fields; i > 0; i--) {
				all.remove(i);
				all.revalidate();
				all.repaint();
			}
			all.remove(nB);
			all.revalidate();
			all.repaint();
			compon.clear();
			fields = 0;
		}
	}

	private void setField() {
		int i = -40;
		for (String key : draw.getProp().keySet()) {
			Double value = draw.getProp().get(key);
			JTextField text = new JTextField();
			if (value != null)
				text.setText(value.toString());
			JLabel row = new JLabel(key + " : ");
			row.setBounds(i += 50, 605, 60, 30);
			text.setBounds(i += 60, 607, 50, 30);
			compon.add(text);
			all.add(row, ++fields);
			all.add(text, ++fields);
			all.revalidate();
			all.repaint();
		}
		nB = new JButton("Set params");
		nB.setBounds(700, 605, 100, 30);
		all.add(nB);
	}

	public static void main(String[] args) {
		Engine draw = Engine.create();
		Paint show = new Paint(draw);
		show.setTitle("Paint");
		show.setSize(1150, 670);
		show.setVisible(true);
		show.setResizable(false);
		Toolkit toolKit = show.getToolkit();
		Dimension size = toolKit.getScreenSize();
		show.setLocation((size.width - show.getWidth()) / 2,
				(size.height - show.getHeight()) / 2);
		show.setDefaultCloseOperation(EXIT_ON_CLOSE);
		show.setLocationRelativeTo(null);
		show.draw.initialize();
		show.addPlugins();
	}
}
