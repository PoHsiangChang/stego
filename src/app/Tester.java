package app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Tester {

	public static void main(String[] args) {
		FileReader fr = new FileReader("MARBLES.BMP");
		System.out.println(fr.getSizeBits());
		System.out.println(fr.getExtBits());



	}

}
