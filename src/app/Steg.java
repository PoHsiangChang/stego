package app;

import java.io.*;
import java.util.*;
import java.awt.*;//image.BufferedImage
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


class Steg
{



/**
 * A constant to hold the number of bits per byte
 */
private final int byteLength=8;

/**
 * A constant to hold the number of bits used to store the size of the file extracted
 */
protected final int sizeBitsLength=32;
/**
 * A constant to hold the number of bits used to store the extension of the file extracted
 */
protected final int extBitsLength=64;

 
 /**
Default constructor to create a steg object, doesn't do anything - so we actually don't need to declare it explicitly. Oh well. 
*/

public Steg()
{

}

/**
A method for hiding a string in an uncompressed image file such as a .bmp or .png
You can assume a .bmp will be used
@param cover_filename - the filename of the cover image as a string 
@param payload - the string which should be hidden in the cover image.
@return a string which either contains 'Fail' or the name of the stego image which has been 
written out as a result of the successful hiding operation. 
You can assume that the images are all in the same directory as the java files
 * @throws IOException 
 * @throws FileNotFoundException 
*/
//TODO you must write this method
public String hideString(String payload, String cover_filename) throws FileNotFoundException, IOException
{	
	BufferedImage Originalimage = ImageIO.read(new File(cover_filename));
	
	 int messageLength  = payload.length();
	 int width  = Originalimage.getWidth();
	 int height  = Originalimage.getHeight();
	 int imageSize = width * height;
	 
	 if (messageLength > imageSize)
	 {
		System.err.println("Payload is too long for this image");
	 }
	
	  BufferedImage ba=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
	
	int i = 0;
	for (int y = 0; y < height-1; y++) 
	{
		for (int x = 0; x < width-1; x++)
		{
			// Get the color components of the pixel you are on
			int rgb	= Originalimage.getRGB(x, y);
			
			Color color	= new Color(rgb);
			int r		= color.getRed();
			int g		= color.getGreen();
			int b		= color.getBlue();
			// Get a character of the message to encode
			char c = 0;
			if (i < messageLength){
                            c = payload.charAt(i);
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
			
			ba.setRGB(x,y,Originalimage.getRGB(x,y)); 
			// Increment the character counter
			i++;
		}
	}

	 File NewFile=new File("C:\\Users\\PoHsiangChang\\Desktop\\NewPic.bmp");
	 ImageIO.write(ba,"bmp",NewFile);
	
	
	System.out.println("hide");
	
	
return "1";
} 
//TODO you must write this method
/**
The extractString method should extract a string which has been hidden in the stegoimage
@param the name of the stego image 
@return a string which contains either the message which has been extracted or 'Fail' which indicates the extraction
was unsuccessful
 * @throws IOException 
*/
public String extractString(String stego_image) throws IOException
{

	BufferedImage Stegoimage = ImageIO.read(new File(stego_image));
	
	int width = Stegoimage.getWidth();
	int height = Stegoimage.getHeight();
	String message = "";
	for (int y = 0; y < height; y++) 
	{
		for (int x = 0; x < width; x++) 
		{
			// Get the pixel color information
			int rgb	= Stegoimage.getRGB(x, y);
			
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
			// If we reach a 0, we��re done (null terminator)
			if (c == 0) {
				return message;
			}
			// Add the character to the message output
			message += c;
		}
	
	}
	
	System.out.println(message);

	
	return message;
}

//TODO you must write this method
/**
The hideFile method hides any file (so long as there's enough capacity in the image file) in a cover image

@param file_payload - the name of the file to be hidden, you can assume it is in the same directory as the program
@param cover_image - the name of the cover image file, you can assume it is in the same directory as the program
@return String - either 'Fail' to indicate an error in the hiding process, or the name of the stego image written out as a
result of the successful hiding process
*/
public String hideFile(String file_payload, String cover_image)
{
	return	"123";
}

//TODO you must write this method
/**
The extractFile method hides any file (so long as there's enough capacity in the image file) in a cover image

@param stego_image - the name of the file to be hidden, you can assume it is in the same directory as the program
@return String - either 'Fail' to indicate an error in the extraction process, or the name of the file written out as a
result of the successful extraction process
*/
public String extractFile(String stego_image)
{
	return	"123";
}

//TODO you must write this method
/**
 * This method swaps the least significant bit with a bit from the filereader
 * @param bitToHide - the bit which is to replace the lsb of the byte of the image
 * @param byt - the current byte
 * @return the altered byte
 */
public int swapLsb(int bitToHide,int byt)
{		
	return	123;
}




}