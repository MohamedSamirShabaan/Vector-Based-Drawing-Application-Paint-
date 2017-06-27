package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Rectangle implements Shape , Cloneable{

	protected int width, height, length;
	protected int x, y;
	protected Point position;
	protected Color color, fillColor;
	protected Engine engine = Engine.create();
	protected Map<String, Double> properties = new HashMap<String, Double>();

	public Rectangle() {
		properties.put("height", null);
		properties.put("width", null);
		properties.put("startX", null);
		properties.put("startY", null);
	}

	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		int x = (properties.get("startX").intValue() + properties.get("width")
				.intValue()) / 2;
		int y = (properties.get("startY").intValue() + properties.get("height")
				.intValue()) / 2;
		this.position = (new Point(x, y));
		// this.position = position;
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
		x = properties.get("startX").intValue();
		y = properties.get("startY").intValue();

		canvas.setColor(fillColor);
		canvas.fillRect(x, y, width, height);
		canvas.setColor(color);
		canvas.drawRect(x, y, width, height);

		if (engine.isPaintSelected()) {
			canvas.setColor(Color.BLACK);
			canvas.fillRect(x + width / 2 - 2, y - 2, 5, 5);// top
			canvas.fillRect(x + width / 2 - 2, y + height - 2, 5, 5);// down
			canvas.fillRect(x - 2, y + height / 2 - 2, 5, 5); // left
			canvas.fillRect(x + width - 2, y + height / 2 - 2, 5, 5);// right
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}