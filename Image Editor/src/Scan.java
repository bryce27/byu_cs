import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Scan {
	Image readFile(String inputFileName) throws IOException{
		 
		Scanner s = null;
		Image img = new Image();
		
        try {
        	// GET THE HEADER INFO FROM THE PPM //
            s = new Scanner(new BufferedReader(new FileReader(inputFileName)));
            s.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
            s.next(); // read the P3 -- do nothing with it
            img.width = Integer.parseInt(s.next());
            img.height = Integer.parseInt(s.next());
            
            // MAKE A MULTI DIM ARRAY //
            Pixel[][] multi = new Pixel[img.height][img.width];
            
            s.next(); // read max value -- do nothing with it
            
            // WE WILL USE THESE TO KEEP TRACK OF WHERE TO PUT THE PIXELS //
            int rowCount = 0;
            int columnCount = 0;
            
            // MAIN LOOP THAT CREATES PIXELS AND STORES THEM //
            while (s.hasNext())
            {
               String Red = s.next();
               String Green = s.next();
               String Blue = s.next();
               Pixel pix = new Pixel(Red, Green, Blue);
               multi[rowCount][columnCount] = pix;
               columnCount++;
               if (columnCount == img.width) {
            	   columnCount = 0;
            	   rowCount++;
               }
               //System.out.print(str);
            }
            img.imageArray = multi; // store it in the image object
            return img;
            
        } finally {
            if (s != null) {
                s.close();
            }
	}
}
	
}
