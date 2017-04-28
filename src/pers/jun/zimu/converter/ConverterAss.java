package pers.jun.zimu.converter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import pers.jun.zimu.Main;
import pers.jun.zimu.util.Util;

public class ConverterAss implements Converter {
	
	private boolean beginEventsFlag = false;
	private int textIndex = -1;
	@Override
	public boolean couldChange(String line) {
		if (textIndex > 0) {
			return true;
		}
		if ("[Events]".equals(line)) {
			beginEventsFlag = true;
		} else if ((beginEventsFlag) && (textIndex < 0)) {
			if (line.startsWith("Format")) {
				line = line.substring(line.indexOf(":"));
				String[] split = line.split(",");
				for (int i = 0; i < split.length; i++) {
					if ("Text".equals(split[i].trim())) {
						textIndex = i;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String convert(String line) {
		line = Util.removeBracket(line);
		String[] split = line.split(",");
		if (split.length >= textIndex) {
			String result = null;
			String[] copyOfRange = Arrays.copyOfRange(split, textIndex, split.length);
			for (int i = 0; i < copyOfRange.length; i++) {
				if (i==0) {
					result = copyOfRange[i];
				}else{
					result += "," + copyOfRange[i];
				}
			}
			result = result.replace("\\N", "\r\n");
			result = Util.addLine(result);
			return result;
		}
		return null;
	}

	

}
