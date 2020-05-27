package org.troplay.graphics;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	protected void loadGraphics(String graphicsPath) {
		List<String> graphics = getGraphicsInDirectory(graphicsPath);

		this.images = graphics.stream()
				.map(gp -> {
					try {
						System.out.println("Loading " + gp + "...");
						return ImageIO.read(new File(gp));
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				})
				.collect(Collectors.toList());
	}

	private List<String> getGraphicsInDirectory(String graphicsPath) {
		String filePath = graphicsBasePath + "/" + graphicsPath;
		File[] files = new File(filePath).listFiles();

		return Arrays.stream(files)
				.map(File::toString)
				.collect(Collectors.toList());
	}

	public Boolean collision(Point mousePoint) {
		return rectangle.contains(mousePoint);
	}
}
