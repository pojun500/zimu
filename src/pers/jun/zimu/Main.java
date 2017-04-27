package pers.jun.zimu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.jun.zimu.util.FileEncode;
import pers.jun.zimu.util.MyPath;

public class Main {
	private static String numberPatternStr = "^[0-9]*$";

	public static void main(String[] args) {
		String path = MyPath.getProjectPath();
		System.out.println(path);
		BufferedReader reader = null;
		FileOutputStream os = null;
		try {
			File parentFolder = new File("D:\\zimu");
			File[] listFiles = parentFolder.listFiles();
			for (File file : listFiles) {
				String name = file.getName();
				int index = name.lastIndexOf(".");
				if (index >= 0) {
					String ext = name.substring(index + 1).toLowerCase();
					name = name.substring(0, index);
					if ("ass".equals(ext)) {
						File outFile = new File(parentFolder, name + ".txt");
						os = new FileOutputStream(outFile);
						System.out.println("begin：" + name);
						String code = getCode(file);
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), code));
						String tempString = null;
						boolean beginFlag = false;
						int textIndex = -1;
						while ((tempString = reader.readLine()) != null)
							if ("[Events]".equals(tempString)) {
								beginFlag = true;
							} else if ((beginFlag) && (textIndex < 0)) {
								if (tempString.startsWith("Format")) {
									tempString = tempString.substring(tempString.indexOf(":"));
									String[] split = tempString.split(",");
									for (int i = 0; i < split.length; i++) {
										if ("Text".equals(split[i].trim())) {
											textIndex = i;
										}
									}
								}
							} else if (textIndex > 0) {
								tempString = removeBracket(tempString);
								String[] split = tempString.split(",");
								if (split.length >= textIndex) {
									tempString = split[textIndex];
									tempString = tempString.replace("\\N", "\r\n");
									tempString = addLine(tempString);
									os.write(tempString.getBytes());
								}
							}
						os.close();
						reader.close();
					} else if ("srt".equals(ext)) {
						File outFile = new File(parentFolder, name + ".txt");
						os = new FileOutputStream(outFile);
						System.out.println("begin：" + name);
						Pattern pattern = Pattern.compile(numberPatternStr);
						String code = getCode(file);
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), code));
						String tempString = null;
						while ((tempString = reader.readLine()) != null) {
							Matcher matcher = pattern.matcher(tempString);
							if (!matcher.matches()) {
								if (!tempString.contains("-->")) {
									tempString = tempString.replaceAll("\\{.*?\\}", "");
									tempString = addLine(tempString);
									byte[] bt = tempString.getBytes();
									os.write(bt);
								}
							}
						}
						reader.close();
						os.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(reader, os);
		}
		System.out.println("Press enter to exit");
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static String getCode(File file) {
		String code = FileEncode.getFileEncode(file);
		if ("asci".equals(code)) {
			code = "GBK";
		}
		return code;
	}

	private static void close(BufferedReader reader, FileOutputStream os) {
		if (reader != null)
			try {
				reader.close();
			} catch (IOException localIOException) {
			}
		if (os != null)
			try {
				os.close();
			} catch (IOException localIOException1) {
			}
	}

	private static String removeBracket(String line) {
		return line.replaceAll("\\{.*?\\}", "");
	}

	private static String addLine(String line) {
		return line + "\r\n";
	}
}
