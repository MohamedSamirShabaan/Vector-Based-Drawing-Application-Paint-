package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Line implements Shape, Cloneable {

	protected Point position;
	protected Color color, fillColor;
	protected int x1, x2, y1, y2;
	protected Map<String, Double> properties = new HashMap<String, Double>();
	protected Engine engine = Engine.create();
	
	public Line() {
		properties.put("startX", null);
		properties.put("endX", null);
		properties.put("startY", null);
		properties.put("endY", null);
	}

	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		try {
			int x = (properties.get("startX").intValue() + properties.get(
					"endX").intValue()) / 2;
			int y = (properties.get("startY").intValue() + properties.get(
					"endY").intValue()) / 2;
			this.position = new Point(x, y);
		} catch (Exception e) {

		}
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		this.properties = properties;

	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return properties;
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void setFillColor(Color color) {
		// TODO Auto-generated method stub
		this.fillColor = color;
	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return fillColor;
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub

		canvas.setColor(color);
		x1 = properties.get("startX").intValue();
		y1 = properties.get("startY").intValue();
		x2 = properties.get("endX").intValue();
		y2 = properties.get("endY").intValue();

		canvas.drawLine(x1, y1, x2, y2);

		if (engine.isPaintSelected()) {
			canvas.setColor(Color.BLACK);
			canvas.fillRect(x1 - 2, y1 - 2, 5, 5);
			canvas.fillRect(x2 - 2, y2 - 2, 5, 5);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
