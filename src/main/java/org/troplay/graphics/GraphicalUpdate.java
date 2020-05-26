package org.troplay.graphics;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

@Getter @Setter
@Builder
public class GraphicalUpdate {
	private Point point;
	private BufferedImage image;
	private Integer z;
}