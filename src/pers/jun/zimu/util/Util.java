package pers.jun.zimu.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {
	public static String removeBracket(String line) {
		return line.replaceAll("\\{.*?\\}", "");
	}
	public static String addLine(String line) {
		return line + "\r\n";
	}

	public static void close(BufferedReader reader, FileOutputStream os) {
		if (reader != null)
			try {
				reader.close();
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			}
		if (os != null)
			try {
				os.close();
			} catch (IOException localIOException1) {
				localIOException1.printStackTrace();
			}
	}
}
