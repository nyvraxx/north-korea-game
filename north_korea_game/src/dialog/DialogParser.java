package dialog;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class DialogParser {
	private static HashMap<String, Image> emotions = new HashMap<>();
	public static HashMap<Integer, Dialog> dialogs = new HashMap<>();

	public static void load() {
	}

	static {
		try {
			Scanner s = new Scanner(new File("resources/emotions/emotions.txt"));

			while (s.hasNext()) {
				String emotion = s.next();
				Image img = ImageIO.read(new File("resources/emotions/" + emotion + ".png"));
				emotions.put(emotion, img);
			}

			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Scanner s = new Scanner(new File("resources/dialog.txt"));

			int numDialogs = s.nextInt();

			for (int i = 0; i < numDialogs; i++) {
				String emotion = s.next();
				int id = s.nextInt();
				String line = s.nextLine().substring(1);
				dialogs.put(id, new Dialog(id, line, emotions.get(emotion)));

			}

			s.nextInt();
			while (s.hasNext()) {
				int start = s.nextInt();
				int end = s.nextInt();

				String option = s.nextLine().substring(1);

				dialogs.get(start).options.add(option);
				dialogs.get(start).pointers.add(end);
			}
			s.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static class Dialog {
		public Dialog(int id, String desc, Image image) {
			super();
			this.img = image;
			this.id = id;
			this.desc = desc;
		}

		public Image img;
		int id;
		String desc;
		ArrayList<String> options = new ArrayList<>();
		ArrayList<Integer> pointers = new ArrayList<>();

		@Override
		public String toString() {
			return "[" + desc + " " + options + " " + pointers;
		}
	}
}
