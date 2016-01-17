package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends SuperClass implements IGrep {
	@Override
	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, String substringSelectionPattern, boolean recursive){
		Scanner scan = null;
		List<File> allFiles = new ArrayList<File>();
		Map<File, List<String>> gMap = new TreeMap <File, List<String>>();
		try {
			allFiles = readFile(directory, fileSelectionPattern, recursive);
		}
		catch (FileNotFoundException e){
			System.out.println("File not found!");
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile(substringSelectionPattern);
		for(File f : allFiles){
			try {
				scan = new Scanner(f);
			}
			catch (FileNotFoundException e){
				System.out.println("File not found!");
				e.printStackTrace();
			}
			List<String> matches = new ArrayList<String>();
			while(scan.hasNextLine()){
				String currentLine = scan.nextLine();
				Matcher matcher = pattern.matcher(currentLine);
				if (matcher.find()){
					matches.add(currentLine);
				}
			}
			if(!matches.isEmpty()){
				gMap.put(f,  matches);
			}
		}
		if (scan != null){
			scan.close();
		}
		return gMap;
	}

}
