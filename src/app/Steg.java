package app;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.imageio.ImageIO;
//image.BufferedImage



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

	
	BufferedImage originalImage = ImageIO.read(new File(cover_filename));
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
	ImageIO.write(originalImage, "bmp", baos);
	baos.flush();
	byte[] imageInByte = baos.toByteArray();
	int payloadLength = payload.length();
	int re = 0;int news2=0;
	String news2String=""; String HideBits="";
	byte[] newByte = baos.toByteArray();
	
	//picture header 54byte , cant change it
	for(int i =0 ; i <=54*byteLength;i++)
	{
		newByte[i]=(byte)imageInByte[i];
		//System.out.println(newByte[i]+" "+i);
	}
	
	//get payload byte
	byte[] Hidebytes = payload.getBytes();//a:97 
	for(int i = 0 ; i <Hidebytes.length;i++)
	{
		//chagne byte in to but , get payload bit
		HideBits+= String.format("%8s",(byteToBit(Hidebytes[i])).toString());//01100001 01100010 01100011
		//System.err.println(Hidebytes[i]);
		//System.out.println(HideBits);
	}
	
	int j =0;
	//i start from 54byte picture header 
	for(int i =(54*byteLength);i<imageInByte.length ;i++)
	{
			//get picture byte
			byte b2 = (byte)imageInByte[i];
			//get picture bit
			String s2 = Integer.toBinaryString(b2 & 0xFF);
			news2 =(int)Integer.parseInt(s2);
			//shift 8 bit ex:11111111 -> 11111110
			news2= ((news2 >> 1) << 1);

				//54byte picture header + payload length
			if  (i <((54*byteLength)+(payloadLength*byteLength)))
			{
				
				news2String = news2+"";
				//replace with the payload bit in the 8th bit
				news2String=news2String.substring(0,7)+HideBits.charAt((j));
				news2=(int)Integer.parseInt(news2String);
				//System.err.println(s2+" "+news2);
				re = Integer.parseInt(news2String, 2); 			
				newByte[i]=(byte) re;
				//System.err.println(newByte[i]+" "+imageInByte[i]+" "+i);
				j++;
			}
			else
			{
				newByte[i]=(byte)imageInByte[i];
				//System.out.println(newByte[i]+" "+imageInByte[i]+" "+i);
			}	
	}
	
	
		//create a new picture with hiding paylaod
		File resizedImgFile = new File("D:\\resize_small.bmp");
		FileOutputStream fos = new FileOutputStream(resizedImgFile);
	
		byte[] resizedBytes = baos.toByteArray();
		for(int i = 0 ; i <newByte.length;i++)
		{
			resizedBytes[i]=newByte[i];
			//System.out.println(imageInByte[i]+" "+resizedBytes[i]+" "+newByte[i]);
		}
		//write the picture with byte
		fos.write(resizedBytes);
		System.out.println("Encode completed: file =D:\\resize_small.bmp ");	
		
		fos.close();
		baos.close();
	  
		
		return "Success";

	
} 
//byte->bit
public static String byteToBit(int i) {  
    return ""  
            + (byte) ((i >> 7) & 0x1) + (byte) ((i >> 6) & 0x1)  
            + (byte) ((i >> 5) & 0x1) + (byte) ((i >> 4) & 0x1)  
            + (byte) ((i >> 3) & 0x1) + (byte) ((i >> 2) & 0x1)  
            + (byte) ((i >> 1) & 0x1) + (byte) ((i >> 0) & 0x1);  
}  


//TODO you must write this method
/**
The extractString method should extract a string which has been hidden in the stegoimage
@param the name of the stego image 
@return a string which contains either the message which has been extracted or 'Fail' which indicates the extraction
was unsuccessful
 * @throws IOException 
*/
public String extractString(int payloadlength,String stego_image) throws IOException
{
	
	File originalImgFile = new File(stego_image);
	BufferedImage bufferedImage = ImageIO.read(originalImgFile);
	// convert BufferedImage to byte array
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	ImageIO.write(bufferedImage, "bmp", baos);
	baos.flush();
	byte[] originalImgByte = baos.toByteArray();
	//System.out.print(originalImgByte.length);
	baos.close();

	String strTest2="";
	String newString="";
	String value="";
	String message="";
	int intTest2=0;
			//54byte picture header + payload length
	for (int i =(54*byteLength) ; i< ((54*byteLength)+(8*payloadlength));i++)
	{
		
		strTest2 = Integer.toBinaryString(originalImgByte[i] & 0xFF);
		//System.err.println(strTest2.substring(7, 8));//01100001
		newString+=	String.format("%1s",strTest2.substring(7, 8));
		intTest2 += Integer.parseInt(String.format("%1s",strTest2.substring(7, 8)));
		
		
		if( newString.length()==8  && intTest2 >0)
		{
				int decodeByte = Integer.parseInt(newString, 2);  
				//System.out.println(decodeByte);//97
				byte[] byteArray = new byte[] {(byte) decodeByte};
				value = new String(byteArray); 
				message+=value;
				newString="";
				intTest2=0;
		}
	}
	

	 System.out.println("Decode result is: "+message);
		 
    
    
	
	
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

/*
BufferedImage img   = ImageIO.read(new FileInputStream(cover_filename));  
String message =payload;	
/*
// Check to make sure the parameters are valid
	if (img == null || message == null){
		System.out.println("Error: Null arguments passed in.");
	}
	int width = img.getWidth();
	int height = img.getHeight();
	int messageLength = message.length();
	// Check to make sure the message will fit in the image
	if (messageLength > width*height){
		System.out.println("Error: message too long.");
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
	
	ImageIO.write(img, "bmp", new File("C:\\Users\\PoHsiangChang\\Desktop\\NewPic.bmp"));
	
	

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

  readFileSteam
  
int i = 0;
for (int y = 0; y < height-1; y++) 
{
	for (int x = (54*8); x < width-1; x++)
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
		
		//System.out.println(Originalimage.getRGB(x,y));
		
		ba.setRGB(x,y,Originalimage.getRGB(x,y)); 
		// Increment the character counter
		i++;
	}
}

 File NewFile=new File("C:\\Users\\PoHsiangChang\\Desktop\\NewPic.bmp");
 ImageIO.write(ba,"bmp",NewFile);


System.out.println("hide");
*/

/*
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
		// If we reach a 0, we°¶re done (null terminator)
		if (c == 0) 
		{

			System.out.println(message);
			return message;
		}
		// Add the character to the message output
		message += c;
	}

}


System.out.println(message);
*/