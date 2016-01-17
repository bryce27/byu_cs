package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuperClass {
	public List<File> readFile(File directory, String fileSelectionPattern, boolean recursive) throws FileNotFoundException {
		List<File> allFiles = new ArrayList<File>(); // ArrayLists are awesome!
		File[] files = directory.listFiles();
		Pattern pattern = Pattern.compile(fileSelectionPattern);
		for(File f : files){
			Matcher matcher = pattern.matcher(f.getName());
			if (matcher.matches() && f.isFile()){
				allFiles.add(f);
			}
			else if (f.isDirectory() && recursive == true){
				recursiveRead(f, fileSelectionPattern, allFiles);
			}
		}
		return allFiles;
	}
	
	public void recursiveRead(File directory, String fileSelectionPattern, List<File> allFiles){
		File[] files = directory.listFiles();
		Pattern pattern = Pattern.compile(fileSelectionPattern);
		for(File f : files){
			Matcher matcher = pattern.matcher(f.getName());
			if (f.isDirectory()){
				recursiveRead(f, fileSelectionPattern, allFiles);
			}
			else if (matcher.matches()){
				allFiles.add(f);
			}
		}
		return;
	}
}
