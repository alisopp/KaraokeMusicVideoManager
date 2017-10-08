package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Static methods of often used functions for JavaFx windows
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class WindowModule {

	/**
	 * Returns a list of all window icons
	 * 
	 * @return Image[] (with Image of all given sizes)
	 */
	public static Image[] getWindowIcons() {
		try {

			// all the image sizes that are there
			Integer[] supportedSizes = { 16, 32, 48, 64, 128, 194, 256, 512 };

			// create an array of the same length in which all images will be saved
			Image[] icons = new Image[supportedSizes.length];

			// save all images in the newly created array
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

	/**
	 * Returns an ImageView object with the given image URL from class resources
	 * 
	 * @param pathToImage
	 *            (String | URL to class resource image)
	 * @return ImageView object
	 */
	public static ImageView createMenuIcon(String pathToImage) {
		return createMenuIcon(pathToImage, 15);
	}

	/**
	 * Returns an ImageView object with the given image URL from class resources and
	 * converts it to a given size
	 * 
	 * @param pathToImage
	 *            (String | URL to class resource image)
	 * @param size
	 *            (Integer | image size)
	 * @return ImageView object
	 */
	public static ImageView createMenuIcon(String pathToImage, int size) {

		// load image from class resources
		Image userIcon = new Image(ClassResourceReaderModule.getInputStream(pathToImage));

		// add it to a new ImageView object
		ImageView userView = new ImageView(userIcon);

		// set size of ImageView
		userView.setFitWidth(size);
		userView.setFitHeight(size);

		return userView;
	}

}
