package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Square extends Rectangle {

	public Square() {
		// TODO Auto-generated constructor stub
		super();
		properties.clear();
		properties.put("length", null);
		properties.put("startX", null);
		properties.put("startY", null);
		width = height = length;
	}

	@Override
	public void setPosition(Point position) {
		try {
			int x = (properties.get("startX").intValue() + properties.get(
					"length").intValue()) / 2;
			int y = (properties.get("startY").intValue() + properties.get(
					"length").intValue()) / 2;
			this.position = new Point(x, y);
		} catch (Exception e) {

		}
	}

	@Override
	public void setProperties(java.util.Map<String, Double> properties) {
		this.properties = properties;

	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub

		length = properties.get("length").intValue();
		x = properties.get("startX").intValue();
		y = properties.get("startY").intValue();
		canvas.setColor(fillColor);
		canvas.fillRect(x, y, length, length);
		canvas.setColor(color);
		canvas.drawRect(x, y, length, length);

		if (engine.isPaintSelected()) {
			canvas.setColor(Color.BLACK);
			canvas.fillRect(x + length / 2 - 2, y - 2, 5, 5);// top
			canvas.fillRect(x + length / 2 - 2, y + length - 2, 5, 5);// down
			canvas.fillRect(x - 2, y + length / 2 - 2, 5, 5); // left
			canvas.fillRect(x + length - 2, y + length / 2 - 2, 5, 5);// right
		}
	}

}
