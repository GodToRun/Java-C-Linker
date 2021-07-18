package jcl;
import java.io.*;
public class IOManager {
	public static void Write(String filename, String content) {
			try {
				String srcText = new String(content);

				File targetFile = new File(filename);
				targetFile.createNewFile();

				BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile.getPath()), "UTF8"));

				output.write(srcText);
				output.close();
			} catch(Exception e) {
				
			}
	}
}
