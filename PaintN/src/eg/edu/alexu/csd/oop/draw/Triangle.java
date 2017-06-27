package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class Triangle extends Line implements Shape, Cloneable {
	private int x3, y3;

	public Triangle() {
		properties.clear();
		properties.put("point1_X", null);
		properties.put("point2_X", null);
		properties.put("point3_X", null);
		properties.put("point1_Y", null);
		properties.put("point2_Y", null);
		properties.put("point3_Y", null);
	}

	@Override
	public void setPosition(Point position) {

		try {
			int x = (properties.get("point1_X").intValue()
					+ properties.get("point2_X").intValue() + properties.get(
					"point3_X").intValue()) / 3;
			int y = (properties.get("point1_Y").intValue()
					+ properties.get("point2_Y").intValue() + properties.get(
					"point3_Y").intValue()) / 3;
			this.position = new Point(x, y);
		} catch (Exception e) {
			this.position = null;
		}
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub

		canvas.setColor(fillColor);

		x1 = properties.get("point1_X").intValue();
		y1 = properties.get("point1_Y").intValue();
		x2 = properties.get("point2_X").intValue();
		y2 = properties.get("point2_Y").intValue();
		x3 = properties.get("point3_X").intValue();
		y3 = properties.get("point3_Y").intValue();
		Polygon p = new Polygon();
		p.addPoint(x1, y1);
		p.addPoint(x2, y2);
		p.addPoint(x3, y3);
		canvas.fillPolygon(p);
		canvas.setColor(color);
		canvas.drawLine(x1, y1, x2, y2);
		canvas.drawLine(x1, y1, x3, y3);
		canvas.drawLine(x2, y2, x3, y3);

		if (engine.isPaintSelected()) {
			canvas.setColor(Color.BLACK);
			canvas.fillRect(x1 - 2, y1 - 2, 5, 5);
			canvas.fillRect(x2 - 2, y2 - 2, 5, 5);
			canvas.fillRect(x3 - 2, y3 - 2, 5, 5);
		}
	}
}
