package jcl;

import java.io.*;
import java.util.*;

public class Linker {
	private static HashMap<String,String> LinkedMaps = new HashMap<String,String>();
	public static void Link(String headerfile,String methodname, String ...param) {
		String method = methodname;
		method += '(';
		for (String fparam : param) {
			method += fparam + ',';
		}
		method = new StringBuilder(method).deleteCharAt(method.length() - 1).toString();
		method += ");";
		LinkedMaps.put(headerfile,method);
	}
	public static void Link(String headerfile) {
		LinkedMaps.put(headerfile,"");
	}
	public static void LinkMethod() {
		
	}
	public static ArrayList<String> Call(String custom_code) {
		ArrayList<String> buf = new ArrayList<String>();
		LinkedMaps.forEach((name,method)->{
			String dir = "";
			CreateTempFile("temp.c");
			try {
				IOManager.Write("temp.c","#include <" + name + ">\nint main(void) { " + custom_code + method + "return 0;}");
				ProcessBuilder gcc = new ProcessBuilder("cmd.exe","/c","gcc.exe","temp.c");
				Process _gcc = gcc.start();
				_gcc.waitFor();
				ProcessBuilder ps =new ProcessBuilder(dir + "a.exe");

				//From the DOC:  Initially, this property is false, meaning that the 
				//standard output and error output of a subprocess are sent to two 
				//separate streams
				ps.redirectErrorStream(true);

				Process pr = ps.start();  

				BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				
				while ((line = in.readLine()) != null) {
				    buf.add(line);
				    }
				in.close();
			} catch (Exception e) {
				System.out.println("An error ccurred while compiling.");
			}
			new File("a.exe").delete();
			new File("temp.c").delete();
		});
		return buf;
	}
	public static ArrayList<String> Call() {
		return Call("");
	}
	private static void CreateTempFile(String filename) {
		File f = new File(filename);
		try {
			f.createNewFile();
		} catch (Exception e) { }
	}
}
