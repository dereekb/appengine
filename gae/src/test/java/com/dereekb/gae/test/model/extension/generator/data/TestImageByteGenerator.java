package com.dereekb.gae.test.model.extension.generator.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestImageByteGenerator
        implements TestByteDataGenerator {

	private Integer width = 1;
	private Integer height = 1;
	private Integer bufferSize = 0x10000;
	private String imageType = "png";

	@Override
	public byte[] generateBytes() {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();

		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, width, height);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);
		byte[] bytes = baos.toByteArray();

		try {
			ImageIO.write(image, imageType, baos);
			bytes = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

}
