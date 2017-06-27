package eg.edu.alexu.csd.oop.draw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SaveJson {

	public void save(String path, ArrayList<Shape> shapes) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("{");
			bw.newLine();
			for (int i = 0; i < shapes.size(); i++) {
				Shape s = shapes.get(i);
				String st = s.toString();
				String[] arr = st.split(Pattern.quote("."));
				st = arr[arr.length - 1];
				arr = st.split("@");
				st = arr[0];
				bw.write("\"shape\":");
				bw.write("\"");
				bw.write(s.getClass().toString()
						.substring(6, s.getClass().toString().length()));
				bw.write("\"");
				bw.write(",");
				bw.newLine();
				bw.write("\"Poistion\":");
				bw.write("[");
				if (s.getPosition() == null) {
					bw.write("-1");
					bw.write(",");
					bw.write("-1");
				} else {
					bw.write(s.getPosition().x + "");
					bw.write(",");
					bw.write(s.getPosition().y + "");
				}
				bw.write("]");
				bw.write(",");
				bw.newLine();
				bw.write("\"color\":");
				bw.write("[");
				if (s.getColor() == null) {
					bw.write("-1");
					bw.write(",");
					bw.write("-1");
					bw.write(",");
					bw.write("-1");
				} else {
					bw.write(s.getColor().getRed() + "");
					bw.write(",");
					bw.write(s.getColor().getGreen() + "");
					bw.write(",");
					bw.write(s.getColor().getBlue() + "");
				}
				bw.write("]");
				bw.write(",");
				bw.newLine();

				bw.write("\"fillcolor\":");
				bw.write("[");
				if (s.getFillColor() == null) {
					bw.write("-1");
					bw.write(",");
					bw.write("-1");
					bw.write(",");
					bw.write("-1");
				} else {
					bw.write(s.getFillColor().getRed() + "");
					bw.write(",");
					bw.write(s.getFillColor().getGreen() + "");
					bw.write(",");
					bw.write(s.getFillColor().getBlue() + "");
				}
				bw.write("]");
				bw.write(",");
				bw.newLine();

				bw.write("\"prop\":");
				bw.write("[");
				if (s.getProperties() == null) {
					bw.write("\"null\"");
				} else {
					int len = s.getProperties().size(), counter = 0;
					for (String key : s.getProperties().keySet()) {
						bw.write(s.getProperties().get(key).toString());
						if (counter != len - 1) {
							bw.write(",");
						}
						counter++;
					}
				}
				bw.write("]");
				if (i + 1 != shapes.size()) {
					bw.write(",");
					bw.newLine();
				}
			}
			bw.newLine();
			bw.write("}");
			bw.close();

		} catch (IOException e) {
			throw new RuntimeException("Error while saving Json !!!");
		}
	}
}
