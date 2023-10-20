package com.olp.user_mgmt.to;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CaptchaUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaUtil.class);

	private String charPool[] = { "A", "B", "C", "D", "t", "u", "v", "x", "y", "z", "1", "2", "3", "4", "E", "F", "G",
			"H", "I", "J", "g", "h", "i", "j", "k", "7", "8", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "9", "0", "5", "6", "7", "8", "9", "a", "b",
			"c", "d", "e", "f", "l", "m", "n", "o", "p", "q", "r", "s" };

	private String rndcode = "";
	private int rndNumber;
	private String captcodeGenerated = "";

	public String getCaptcodeGenerated() {
		return captcodeGenerated;
	}

	public void setCaptcodeGenerated(String captcodeGenerated) {
		this.captcodeGenerated = captcodeGenerated;
	}

	public byte[] execute(HttpServletResponse response) throws Exception {
		byte imageBytes[] = null;
		ByteArrayOutputStream baos = null;
		try {
			int width = 110;
			int height = 33;
			int fontSize = 15;
			int xGap = 10;
			int yGap = 20;
			String fontName = "Arial";
			Color gradiantStartColor = Color.ORANGE;
			Color gradiantEndColor = Color.ORANGE;
			Color textColor = new Color(0, 0, 0);

			String genrateRandomCode = genrateRandomCode();
			setCaptcodeGenerated(genrateRandomCode);
			String[] newData = { genrateRandomCode };

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bufferedImage.createGraphics();
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHints(rh);
			GradientPaint gp = new GradientPaint(0, 0, gradiantStartColor, 0, height / 2, gradiantEndColor, true);
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, width, height);
			for (int i = 0; i < width - 10; i = i + 25) {
				int q = Math.abs(genrateSecureRandomNumber()) % width;
				int colorIndex = Math.abs(genrateSecureRandomNumber()) % 200;
				g2d.setColor(new Color(colorIndex, colorIndex, colorIndex));
			}
			g2d.setColor(textColor);
			int index = Math.abs(genrateSecureRandomNumber()) % newData.length;
			String captcha = newData[index];

			int x = 0;
			int y = 0;
			for (int i = 0; i < newData[index].length(); i++) {
				Font font = new Font(fontName, Font.BOLD, fontSize);
				g2d.setFont(font);
				x += xGap + (Math.abs(genrateSecureRandomNumber()) % 2);
				y = yGap + Math.abs(genrateSecureRandomNumber()) % 4;
				g2d.drawChars(newData[index].toCharArray(), i, 1, x, y);
			}
			g2d.dispose();
			baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", baos);
			baos.flush();
			imageBytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			baos.close();
		}
		return imageBytes;
	}

	private String genrateRandomCode() throws NoSuchAlgorithmException {
		rndcode = "";
		for (int i = 0; i < 6; i++) {
			rndNumber = genrateSecureRandomNumber() % 50;
			rndcode = rndcode.concat(charPool[rndNumber]);
		}
		return rndcode;
	}

	public int genrateSecureRandomNumber() {
		SecureRandom secureRandomGenerator = null;
		int sr = 0;
		try {
			// For windows machine
			if (System.getProperty("os.name").startsWith("Windows")) {
				secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
				sr = secureRandomGenerator.nextInt(1000000);
			}
			// for Linux machine
			else {
				secureRandomGenerator = SecureRandom.getInstance("NativePRNG");
				sr = secureRandomGenerator.nextInt(1000000);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sr;
	}

}
