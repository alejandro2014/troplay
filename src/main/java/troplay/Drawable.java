package troplay;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static troplay.Const.BASE_DIR;

@Data
@NoArgsConstructor
public class Drawable {
	protected Point point;
    protected Rectangle rectangle;

    protected BufferedImage currentImage;
    protected List<BufferedImage> images = new ArrayList<>();
	protected Boolean show;

	protected Boolean refresh;
	protected Boolean drawOnce;

	protected final String graphicsBasePath = BASE_DIR + "/src/main/resources/graphics";

	protected void loadGraphics(String graphicsPath) throws IOException {
		images = new ArrayList<>();

		String[] playerGraphicPaths = { "presentacion" };

		for (int j = 0; j < playerGraphicPaths.length; j++) {
			String filePath = graphicsBasePath + "/" + graphicsPath + "/" + playerGraphicPaths[j] + ".png";
			System.out.println("Loading " + filePath);

			BufferedImage image = ImageIO.read(new File(filePath));
			this.images.add(image);
		}
	}

	public Boolean collision(Point mousePoint) {
		return rectangle.contains(mousePoint);
	}
}
