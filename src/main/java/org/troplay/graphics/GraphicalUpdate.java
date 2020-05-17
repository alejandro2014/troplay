package org.troplay.graphics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.Image;
import java.awt.Point;

@Getter @Setter
@Builder
public class GraphicalUpdate {
	private Point point;
	private Image image;
	private Integer z;
}