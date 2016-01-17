import java.io.IOException;

public class ImageEditor {

	public static void main(String[] args) throws IOException {
		
		ImageEditor ie = new ImageEditor();
		
		// SORT THE ARGUMENTS //
		String inputFileName = args[0];
		String outputFileName = args[1];
		String imageFunction = args[2];
		int blurFactor = 0;
		
		if (args.length == 4) {
			blurFactor = Integer.parseInt(args[3]);
		}
		
		Scan s = new Scan();
		Image importedImage = s.readFile(inputFileName); // also stores the pixels in the image
		
		Image newImage = new Image();
		
		// CALL FUNCTION BASED ON ARGUMENTS //
		if (imageFunction.equals("invert")) {
			newImage = ie.invert(importedImage);
		}
		else if (imageFunction.equals("grayscale")) {
			newImage = ie.grayscale(importedImage);
		}
		else if (imageFunction.equals("emboss")) {
			newImage = ie.emboss(importedImage);
		}
		else if (imageFunction.equals("motionblur")) {
			newImage = ie.motionblur(importedImage, blurFactor);
		}
		
		// SAVE IMAGE TO NEW FILE //
		Saver saver = new Saver();
		String newString = saver.imageToString(newImage);
		saver.writeFile(newString, outputFileName);
		//System.out.print(newString);
	}
	
	public Image invert(Image inputImage) {
		
		for (int i = 0; i < inputImage.height; i++ ){
			for (int j = 0; j < inputImage.width; j++ ){
				inputImage.imageArray[i][j].invert();
			}
		}
		return inputImage;
	}

	public Image grayscale(Image inputImage) {
		for (int i = 0; i < inputImage.height; i++ ){
			
			for (int j = 0; j < inputImage.width; j++ ){
				inputImage.imageArray[i][j].grayscale();
			}
		}
		return inputImage;
	}
	
	public Image emboss(Image inputImage) {
		
		for (int i = inputImage.height-1; i >= 0; i-- ){
			for (int j = inputImage.width-1; j >= 0; j-- ){
				Pixel pix = inputImage.imageArray[i][j];
				
				if (i <= 0 || j <= 0){
					pix.red = 128;
					pix.green = 128;
					pix.blue = 128;
				}
				
				else {
				
					// CALCULATE THE DIFFERENCES //
					int redDiff = pix.red - inputImage.imageArray[i-1][j-1].red;
					int greenDiff = pix.green - inputImage.imageArray[i-1][j-1].green;
					int blueDiff = pix.blue - inputImage.imageArray[i-1][j-1].blue;
					
					// WHICH IS LARGEST? //
					int maxDifference = redDiff;
					
					if (Math.abs(greenDiff) > Math.abs(maxDifference)){
						maxDifference = greenDiff;
					}
					if (Math.abs(blueDiff) > Math.abs(maxDifference)){
						maxDifference = blueDiff;
					}
					
					int v = 128 + maxDifference;
					if (v > 255) v = 255;
					else if (v < 0) v = 0;
					
					pix.red = pix.green = pix.blue = v;
				}
			}
		}
		return inputImage;
	}
	
	public Image motionblur(Image inputImage, int blurFactor) {
		if (blurFactor == 1 ){return inputImage;}
		else {
			for (int i = 0; i < inputImage.height; i++ ){
				for (int j = 0; j < inputImage.width; j++ ){	
						
						int redTotal = 0;
						int greenTotal = 0;
						int blueTotal = 0;
						int redAvg = 0;
						int greenAvg = 0;
						int blueAvg = 0;
						int count = 0;
						
						for (int k = 0; k < blurFactor; k++ ){
							
							if (j+k < inputImage.width){
								// problem is probably in here
								int r = inputImage.imageArray[i][k+j].red;
								int g = inputImage.imageArray[i][k+j].green;
								int b = inputImage.imageArray[i][k+j].blue;
								redTotal += r;
								greenTotal += g;
								blueTotal += b;
								count++;
							}
						}
						
						redAvg = redTotal / count;
						greenAvg = greenTotal / count;
						blueAvg = blueTotal / count;
						
						inputImage.imageArray[i][j].red = redAvg;
						inputImage.imageArray[i][j].green = greenAvg;
						inputImage.imageArray[i][j].blue = blueAvg;
			}
		}
	}
		return inputImage;
	}
}
	
