package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import javafx.scene.image.Image;

public class WindowMethods {

	public static Image[] getWindowIcons() {
		try {
			Integer[] supportedSizes = { 16, 32, 48, 64, 128, 194, 256, 512 };
			Image[] icons = new Image[supportedSizes.length];
			for (int i = 0; i < supportedSizes.length; i++) {
				String filePath = "websites/favicons/favicon-" + supportedSizes[i] + "x" + supportedSizes[i];
				icons[i] = new Image(ClassResourceReaderModule.getInputStream(filePath + ".png"));
			}
			return icons;
		} catch (Exception e) {
			System.err.println("Exception while loding icons");
			return null;
		}
	}

}
