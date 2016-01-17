
public class Image {
	// 2d array of pixels
	Pixel[][] imageArray;
	int width;
	int height;
	
	public void printArray(){
		for(int i = 0; i < width; i++)
		   {
		      for(int j = 0; j < height; j++)
		      {
		    	  (imageArray[i][j]).printPixel();
		      }
		      System.out.println();
		   }
	}
}
