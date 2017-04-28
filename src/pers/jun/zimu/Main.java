package pers.jun.zimu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import pers.jun.zimu.converter.Converter;
import pers.jun.zimu.converter.ConverterFactory;
import pers.jun.zimu.util.FileEncode;
import pers.jun.zimu.util.MyPath;
import pers.jun.zimu.util.Util;

public class Main {

	public static void main(String[] args) {
		String path;
		if (args!=null&&args.length>0) {
			path = args[0];
		}else{
			path = MyPath.getProjectPath();
		}
		System.out.println("path：" + path);
		BufferedReader reader = null;
		FileOutputStream os = null;
		try {
			File parentFolder = new File(path);
			File[] listFiles = parentFolder.listFiles();
			for (File file : listFiles) {
				String name = file.getName();
				int index = name.lastIndexOf(".");
				if (index >= 0) {
					String ext = name.substring(index + 1).toLowerCase();
					Converter converter = ConverterFactory.getConverter(ext);
					if (converter==null) {
						continue;
					}
					name = name.substring(0, index);
					File outFile = new File(parentFolder, name + ".txt");
					os = new FileOutputStream(outFile);
					System.out.println("begin：" + name);
					String code = FileEncode.getCode(file);
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), code));
					String line = null;
					while ((line = reader.readLine()) != null){
						if (converter.couldChange(line)) {
							os.write(converter.convert(line).getBytes());
						}
					}
					Util.close(reader, os);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(reader, os);
		}
		System.out.println("Press enter to exit");
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
