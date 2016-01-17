import java.util.ArrayList;
import java.util.List;

public class Pixel {
	// 
	int red;
	int green;
	int blue;
	
	public Pixel (String Red, String Green, String Blue){
		red = Integer.parseInt(Red);
		green = Integer.parseInt(Green);
		blue = Integer.parseInt(Blue);
	}
	
	public void invert(){
		red = Math.abs(red - 255);
		green = Math.abs(green - 255);
		blue = Math.abs(blue - 255);
	}
	
	public void grayscale(){
		int average = (this.red + this.green + this.blue) / 3;
		this.red = average;
		this.green = average;
		this.blue = average;
	}

	public void printPixel(){
		System.out.print(red);
		System.out.print(green);
		System.out.print(blue);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(red);
		sb.append("\n");
		sb.append(green);
		sb.append("\n");
		sb.append(blue);
		sb.append("\n");
		String output = sb.toString();
		return output;
	}
}
