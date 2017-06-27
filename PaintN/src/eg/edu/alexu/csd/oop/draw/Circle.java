package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Ellipse {

	public Circle() {
		// TODO Auto-generated constructor stub
		super();
		properties.clear();
		properties.put("radius", null);
		properties.put("centerX", null);
		properties.put("centerY", null);
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub

		radius = properties.get("radius").intValue();
		x = properties.get("centerX").intValue() - radius / 2;
		y = properties.get("centerY").intValue() - radius / 2;

		canvas.setColor(fillColor);
		canvas.fillOval(x, y, radius, radius);
		canvas.setColor(color);
		canvas.drawOval(x, y, radius, radius);

		if (engine.isPaintSelected()) {
			canvas.setColor(Color.BLACK);
			canvas.fillRect(x + radius / 2, y - 2, 5, 5);// top
			canvas.fillRect(x + radius / 2 - 2, y + radius / 2, 5, 5);// center
			canvas.fillRect(x - 2, y + radius / 2, 5, 5);// left
			canvas.fillRect(x + radius - 2, y + radius / 2, 5, 5);// right
			canvas.fillRect(x + radius / 2, y + radius - 2, 5, 5);// down
		}
	}

}
