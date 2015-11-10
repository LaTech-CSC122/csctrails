package grandtheftroster.utilities;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Logger {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	private BufferedWriter bw;
	private String pathURL;
	
	
	public Logger(String pathURL){
		this.pathURL = pathURL;
		Path path = Paths.get(pathURL);
		try{
			bw = new BufferedWriter(Files.newBufferedWriter(path, ENCODING));
		}catch(Exception e){
			System.out.println("Error: Could not create BufferedWritter");
			System.out.println(e.toString());
		}
	}

	public void log(String message) {
		try{
			bw.write(message);
			bw.flush();
		}catch(Exception e){
				System.out.println("Error: Could write to: " + pathURL);
		}
	}
	
	public void close(){
		try{
			bw.close();
		}catch(Exception e){
			System.out.println("Error: Could not close Scanner");
			System.out.println(e.toString());
		}
	}
}
