import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
   private static final String REGEX = "\\b\\w\\b";
   private static final String INPUT = "cat cat cat cattie cat";
   
   /**
    * Input - path with scripts folder; Output - dependency order
    * @param args
 * @throws FileNotFoundException 
    */
	public static void main(String[] args) throws FileNotFoundException  {
		File dir = new File(args[0]);
		
		//Check if directory
		if (!dir.exists() && !dir.isDirectory())
				System.exit(1);
		

		//Take SQL-files
		ArrayList<Script> scripts = new ArrayList<Script>();
		for(File file : dir.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {return name.toUpperCase().endsWith(".SQL");}} ))

			scripts.add(new Script(file));
		
		searchDependence(scripts);
		searchDependence(scripts);
	}
	
	private static void searchDependence(ArrayList<Script> scripts){
		Script script = scripts.remove(0);
		
		System.out.println( script.contents );
	}
	
	private static class Script{
		File f;
		String contents;
		String schema;
		String name;
		ArrayList depends;
		
		public Script(File f) {
			this.f = f;
			 try {
				this.contents = new String(Files.readAllBytes(Paths.get(f.toURI())), "cp1251");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	{      Pattern p = Pattern.compile(REGEX);
	      Matcher m = p.matcher(INPUT);   // get a matcher object
	      int count = 0;
	      
	      File f = new File("");
	      f.isDirectory();

	      while(m.find()) {
	         count++;
	         //System.out.println("Match number "+count);
	         //System.out.println("start(): "+m.start());
	         //System.out.println("end(): "+m.end());
	         System.out.println( INPUT.substring(m.start(), m.end()) );
	      }
	}

}