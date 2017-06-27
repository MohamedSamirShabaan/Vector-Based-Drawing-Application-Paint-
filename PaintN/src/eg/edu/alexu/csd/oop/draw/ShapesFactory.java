package eg.edu.alexu.csd.oop.draw;

import java.util.List;

public class ShapesFactory {

	public Shape makeShape(String shapeName,
			List<Class<? extends Shape>> classes) {
		int index = -2;
		for (int i = 0; i < classes.size(); i++) {
			if (classes.get(i).getName().equals(shapeName)) {
				index = i;
				break;
			}
		}
		Shape s = null;
		if (index >= 0) {
			try {
				s = classes.get(index).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s;

	}
}
