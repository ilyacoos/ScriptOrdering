import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// What for 2
public class Main {
	private static ArrayList<Script> scripts = new ArrayList<Script>(); 
   
   /**
    * Input - path with scripts folder; Output - dependency order in stg output
    * @param args
    */
	public static void main(String[] args){
		File dir = new File(args[0]);
		
		//Check if directory
		if (!dir.exists() && !dir.isDirectory())
				System.exit(1);
		

		//Take SQL-files
		FilenameFilter fileFilter = new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.toUpperCase().endsWith(".SQL");
			}
		};
		
		//Read files
		for(File file : dir.listFiles( fileFilter )){
			scripts.add(new Script(file));
		}

		//Rollout files
		for(Script script : scripts){
			if( !script.checked ) searchDependence(script);
		}
	}
	
	private static void searchDependence(Script script){
		script.checked = true;
		
		for(Script s : scripts){
			if( !s.checked && script.find( s.schema, s.object ) )
				searchDependence(s);
		}
		System.out.println( script.fName );
	}
	
	private static class Script{
		String contents;
		String schema;
		String object;
		String fName;
		boolean checked;
		
		public Script(File file) {
			this.fName = file.getName();
			
			Pattern p = Pattern.compile("([^.]*)[.]([^.]*)");
			Matcher m = p.matcher(file.getName());
			
			if ( m.find() ) {
			    this.schema = m.group(1);
			    this.object = m.group(2);
			}
			
			try {
				this.contents = new String(Files.readAllBytes(Paths.get(file.toURI())), "cp1251");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean find(String schema, String object){
			String pattern;
			if( this.schema.equalsIgnoreCase(schema) )
				pattern = "\\s([\"\\[]?" + schema + "[\"\\]]?[.])?[\"\\[]?" + object + "[\"\\]]?[.\\s]";
			else
				pattern = "\\s[\"\\[]?" + schema + "[\"\\]]?[.][\"\\[]?" + object + "[\"\\]]?[.\\s]";
			Pattern p = Pattern.compile( pattern, Pattern.CASE_INSENSITIVE );
			Matcher m = p.matcher( this.contents );
			return m.find();
		}
	}
}