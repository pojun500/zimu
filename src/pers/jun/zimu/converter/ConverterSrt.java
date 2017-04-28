package pers.jun.zimu.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.jun.zimu.util.Util;

public class ConverterSrt implements Converter {
	private static Pattern pattern = Pattern.compile("^[0-9]*$");
	@Override
	public boolean couldChange(String line) {
		Matcher matcher = pattern.matcher(line);
		if (!matcher.matches()&&!line.contains("-->")) {
			return true;
		}
		return false;
	}

	@Override
	public String convert(String line) {
		line = line.replaceAll("\\{.*?\\}", "");
		line = Util.addLine(line);
		return line;
	}

}
