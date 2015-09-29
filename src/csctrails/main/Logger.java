package csctrails.main;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Logger {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	final static String DEFAULT_DIR = "log/";
	
	private BufferedWriter bw;
	private DateFormat logDateFormat;
	private boolean notifiedOfIO;
	
	
	public Logger(){
		logDateFormat = new SimpleDateFormat("hh:mm:ss:");
		notifiedOfIO = false;
		String pathURL= DEFAULT_DIR + "CSC122 Trails Log " +
				(new SimpleDateFormat("yy-MM-dd-hh-mm-ss")).format(new Date()) + ".txt";
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
			bw.write(formatString(message, true));
			bw.flush();
		}catch(Exception e){
			if(!notifiedOfIO){
				System.out.println("Error: Could not print to output." + e.toString());
				System.out.println("Printing to standard output...");
				notifiedOfIO = true;
			}
			System.out.println(formatString(message, true));
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
	
	private String formatString(String message, boolean printDate){
		String output = "";
		String dateString = logDateFormat.format(new Date());
		Scanner scanner = new Scanner(message);
		if(printDate) output += dateString;
		if(scanner.hasNextLine()) output += " -" + scanner.nextLine() + "\n";
		while(scanner.hasNextLine()){
			output += "          -" + scanner.nextLine();
			output += "\n";
		}
		scanner.close();
		return output;
	}
}
