package org.troplay.graphics;

import java.awt.Image;
import java.awt.Point;

public record GraphicalUpdate(Point coords, Image image, int z) {}