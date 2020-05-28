package org.troplay.graphics;

import lombok.Data;
import troplay.Const;
import troplay.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Scene {
	private BufferedImage buffer;
	private List<Drawable> drawables;
	private Graphics2D canvas;
	private Drawable clickedDrawable;

	public Scene() {
		this.buffer = new BufferedImage(Const.SCREEN_WIDTH,Const.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.drawables = new ArrayList<>();
	}

	public void draw(Panel panel) {
		canvas = (Graphics2D) panel.getBufferActual().getGraphics();

		drawables.forEach(drawable -> drawImageInCanvas(drawable, canvas, panel));

		panel.repaint();
	}

	private void drawImageInCanvas(Drawable drawable, Graphics2D g, Panel panel) {
		Image image = drawable.getCurrentImage();
		int x = (int) drawable.getPoint().getX();
		int y = (int) drawable.getPoint().getY();

		g.drawImage(image, x, y, panel);
	}

	public void addDrawable(Drawable drawable) {
		this.drawables.add(drawable);
    }

    public void checkCollision(Point mousePoint) {
		this.clickedDrawable = null;
		this.drawables.forEach(d -> d.setIsClicking(false));

		Optional<Drawable> drawableClicked = this.drawables.stream()
				.filter(d -> isColliding(d, mousePoint))
				.findFirst();

		drawableClicked.ifPresent(drawable -> {
			drawable.setIsClicking(true);
			this.clickedDrawable = drawable;
		});
	}

	private boolean isColliding(Drawable d, Point mousePoint) {
		return d.getShow() && d.getRectangle() != null  && d.collision(mousePoint);
	}
}
