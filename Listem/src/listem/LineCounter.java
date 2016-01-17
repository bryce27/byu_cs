package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LineCounter extends SuperClass implements ILineCounter{
	@Override 
	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, boolean recursive){
		Scanner scan = null;
		List<File> allFiles = new ArrayList<File>();
		Map<File, Integer> lMap = new TreeMap<File, Integer>();
		try {
			allFiles = readFile(directory, fileSelectionPattern, recursive);
		}
		catch (FileNotFoundException e){
			System.out.println("File not found!");
			e.printStackTrace();
		}
		for (File f : allFiles){
			int lineCount = 0;
			try {
				scan = new Scanner(f);
			}
			catch (FileNotFoundException e){
				System.out.println("File not found!");
				e.printStackTrace();
			}
			while (scan.hasNextLine()){
				lineCount++;
				String currentLine = scan.nextLine();
			}
			lMap.put(f, lineCount);
		}
		if (scan != null){
			scan.close();
		}
		return lMap;
	}
}
