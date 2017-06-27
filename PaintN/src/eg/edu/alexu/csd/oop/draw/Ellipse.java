package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Ellipse implements Shape, Cloneable {

	protected int width, height, x, y, radius;
	protected Point position;
	protected Color color, fillColor;
	protected Engine engine = Engine.create();
	protected Map<String, Double> properties = new HashMap<String, Double>();

	public Ellipse() {
		properties.put("height", null);
		properties.put("width", null);
		properties.put("centerX", null);
		properties.put("centerY", null);
	}

	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		this.position = new Point(properties.get("centerX").intValue(),
				properties.get("centerY").intValue());
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

		height = properties.get("height").intValue();
		width = properties.get("width").intValue();
		x = properties.get("centerX").intValue() - width / 2;
		y = properties.get("centerY").intValue() - height / 2;

		canvas.setColor(fillColor);
		canvas.fillOval(x, y, width, height);
		canvas.setColor(color);
		canvas.drawOval(x, y, width, height);

		if (engine.isPaintSelected()) {
			canvas.setColor(Color.BLACK);
			canvas.fillRect(x + width / 2, y - 2, 5, 5);// top
			canvas.fillRect(x + width / 2 - 2, y + height / 2, 5, 5);// center
			canvas.fillRect(x - 2, y + height / 2, 5, 5);// left
			canvas.fillRect(x + width - 2, y + height / 2, 5, 5);// right
			canvas.fillRect(x + width / 2, y + height - 2, 5, 5);// down

		}

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
