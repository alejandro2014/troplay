package org.troplay.graphics;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Drawable {
	protected Point point;
    protected Rectangle rectangle;

    public Boolean isClicking;

    protected BufferedImage currentImage;
    protected List<BufferedImage> images = new ArrayList<>();
	protected Boolean show;

	protected Boolean refresh;
	protected Boolean drawOnce;

	protected final String graphicsBasePath;

	protected Drawable() {
		this.graphicsBasePath = System.getProperty("user.dir") + "/src/main/resources/graphics";
	}

	protected void loadGraphics(String graphicsPath) {
		List<String> graphics = getGraphicsInDirectory(graphicsPath);

		this.images = graphics.stream()
				.map(gp -> loadImage(gp))
				.collect(Collectors.toList());
	}

	private BufferedImage loadImage(String graphicsPath) {
		try {
			System.out.println("Loading " + graphicsPath + "...");
			return ImageIO.read(new File(graphicsPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> getGraphicsInDirectory(String graphicsPath) {
		String filePath = graphicsBasePath + "/" + graphicsPath;
		File[] files = new File(filePath).listFiles();

		List<String> fileNames = Arrays.stream(files)
				.map(File::toString)
				.collect(Collectors.toList());

		Collections.sort(fileNames);

		return fileNames;
	}

	public Boolean collision(Point mousePoint) {
		System.out.println("Show: " + show + " Rectangle: " + rectangle + " Mouse point: " + mousePoint);
		return show && rectangle != null && rectangle.contains(mousePoint);
	}
}
