package eg.edu.alexu.csd.oop.draw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class ClassPathFinder {

	@SuppressWarnings("unchecked")
	public List<Class<? extends Shape>> getFiles(String paths) {
		List<Class<? extends Shape>> classes = new LinkedList<Class<? extends Shape>>();
		List<File> filesList = new ArrayList<File>();
		for (final String path : paths.split(File.pathSeparator)) {
			final File file = new File(path);
			if (file.isDirectory()) {
				recurse(filesList, file);
			} else {
				filesList.add(file);
			}
		}
		for (int i = 0; i < filesList.size(); i++) {
			if (filesList.get(i).toString().endsWith(".class")) {
				// removes the .class extension
				String classname = filesList.get(i).getName();// .substring(0,filesList.get(i).length()-6);
				classname = classname.substring(0, classname.length() - 6);
				final Package[] packages = Package.getPackages();
				final String className = classname;

				for (final Package p : packages) {
					final String pack = p.getName();
					final String tentative = pack + "." + className;
					try {
						Object o = Class.forName(tentative).newInstance();
						if (o instanceof Shape) {
							classes.add((Class<? extends Shape>) o.getClass());
						}
					} catch (final ClassNotFoundException e) {
						continue;
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					}
				}

			}

			if (filesList.get(i).toString().endsWith(".jar")) {
				try {
					List<String> list = getJarContent(filesList.get(i)
							.toString());
					for (String file : list) {
						try {
							String[] arr = file.toString().split(
									Pattern.quote("."));
							String name = arr[0];
							name = name.replace('/', '.');
							Object o = Class.forName(name).newInstance();
							if (o instanceof Shape) {
								classes.add((Class<? extends Shape>) o
										.getClass());
							}
						} catch (ClassNotFoundException e) {
						} catch (InstantiationException e) {
						} catch (IllegalAccessException e) {
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return classes;
	}

	private void recurse(List<File> filesList, File f) {
		File list[] = f.listFiles();
		for (File file : list) {
			if (file.isDirectory()) {
				recurse(filesList, file);
			} else {
				filesList.add(file);
			}
		}
	}

	private List<String> getJarContent(String jarPath) throws IOException {
		List<String> content = new ArrayList<String>();
		@SuppressWarnings("resource")
		JarFile jarFile = new JarFile(jarPath);
		Enumeration<JarEntry> e = jarFile.entries();
		while (e.hasMoreElements()) {
			JarEntry entry = e.nextElement();
			String name = entry.getName();
			content.add(name);
		}
		return content;
	}

}