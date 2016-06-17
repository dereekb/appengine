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

	public TestImageByteGenerator() {}

	public TestImageByteGenerator(Integer width, Integer height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	public TestImageByteGenerator(Integer width, Integer height, Integer bufferSize, String imageType) {
		this(width, height);
		this.setBufferSize(bufferSize);
		this.setImageType(imageType);
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getBufferSize() {
		return this.bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getImageType() {
		return this.imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	// MARK: TestByteDataGenerator
	@Override
	public byte[] generateBytes() {

		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();

		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, this.width, this.height);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(this.bufferSize);
		byte[] bytes = baos.toByteArray();

		try {
			ImageIO.write(image, this.imageType, baos);
			bytes = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes;
	}

	@Override
	public String toString() {
		return "TestImageByteGenerator [width=" + this.width + ", height=" + this.height + ", bufferSize="
		        + this.bufferSize + ", imageType=" + this.imageType + "]";
	}

}
