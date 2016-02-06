package app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Tester {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		Steg steg = new Steg();
		
		String payload="abc";
	
		steg.hideString(payload,"/Stego/marbles.bmp");
		steg.extractString(payload.length(),"D:\\resize_small.bmp");
		
		
			}
}
	
//steg.convertByteArrayToString();
		//byte i4=(byte) 0110011;
		//System.out.println(i4);   
		    
		
		
		/*
		BufferedImage image;
		BufferedImage img = loadImage("C:\\Users\\PoHsiangChang\\Desktop\\CyberSecurityTest.bmp");
		String message = "abc";
		encodeMessage(img, message);
		saveImage(img, "C:\\Users\\PoHsiangChang\\Desktop\\CyberSecurityTest.bmp");
		
		System.out.println(decodeImage(img));
		*/
		


	/*
	public static void saveImage(BufferedImage image, String fileName) {
		try {
			ImageIO.write(image, "bmp", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage loadImage(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	


static void encodeMessage(BufferedImage img, String message){ 
	// Check to make sure the parameters are valid
	if (img == null || message == null){
		System.out.println("Error: Null arguments passed in.");
		return;
	}
	int width = img.getWidth();
	int height = img.getHeight();
	int messageLength = message.length();
	// Check to make sure the message will fit in the image
	if (messageLength > width*height){
		System.out.println("Error: message too long.");
		return;
	}
	// i will iterate through each letter of the message
	int i = 0;
	for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
			// Get the color components of the pixel you are on
			int rgb	= img.getRGB(x, y);
			Color color	= new Color(rgb);
			int r		= color.getRed();
			int g		= color.getGreen();
			int b		= color.getBlue();
			// Get a character of the message to encode
			char c = 0;
			if (i < messageLength){
                            c = message.charAt(i);
                        } // Clear the end bits of the pixel by shifting
                        r = (r >> 3) << 3;
                        g = (g >> 3) << 3;
                        b = (b >> 2) << 2; // Fill in the empty bits with the character
                        r += (c & 0b11100000) >> 5;
			g += (c & 0b00011100) >> 2;
			b += (c & 0b00000011);
			// Replace the old pixel with the new encoded pixel
			color = new Color(r, g, b);
			rgb = color.getRGB();
			img.setRGB(x, y, rgb); 
			// Increment the character counter
			i++;
		}
	}
}

static String decodeImage(BufferedImage image) {
	int width = image.getWidth();
	int height = image.getHeight();
	String message = "";
	for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
			// Get the pixel color information
			int rgb	= image.getRGB(y, x);
			Color color	= new Color(rgb);
			int r		= color.getRed();
			int g		= color.getGreen();
			int b		= color.getBlue();
			// Mask off the unimportant bits of the pixel
			r = r & 0b00000111;
			g = g & 0b00000111;
			b = b & 0b00000011;
			// Shift the bits and add them to create the char
			char c = (char) ((r << 5) + (g << 2) + b);
			// If we reach a 0, we°¶re done (null terminator)
			if (c == 0) {
				return message;
			}
			// Add the character to the message output
			message += c;
		}
	}
	return message;
}

}
*/


