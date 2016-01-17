import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Saver {
	
	// read image contents into a string
	public String imageToString(Image img) {
		StringBuilder sb = new StringBuilder("P3\n");
		
		// ADD HEADER INFO //
		sb.append(img.width);
		sb.append(" ");
		sb.append(img.height);
		sb.append("\n255\n");
		
		// CYCLE THROUGH PIXELS //
		String output = new String();
		for (int i = 0; i < img.height; i++ ){
			for (int j = 0; j < img.width; j++ ){
				output = img.imageArray[i][j].toString();
				sb.append(output);
			}
		}
		return sb.toString();
	}
	
	
	public void writeFile(String inputString, String outputFileName) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));
	 
		pw.write(inputString);
	 
		pw.close();
	}
}
