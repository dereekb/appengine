package com.dereekb.gae.utilities.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * {@link BufferImage} utility for conversions.
 *
 * @author dereekb
 *
 */
public class BufferedImageUtility {

	public static byte[] imageToPngBytes(BufferedImage image) throws IOException {
		return imageToBytes(image, "png");
	}

	public static byte[] imageToJpgBytes(BufferedImage image) throws IOException {
		return imageToBytes(image, "jpg");
	}

	public static byte[] imageToBytes(BufferedImage image,
	                                  String formatName)
	        throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, formatName, baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	public static BufferedImage bytesToImage(byte[] bytes,
	                                         String format)
	        throws IOException {
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage image = ImageIO.read(in);
		return image;
	}

}
