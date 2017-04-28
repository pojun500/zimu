package pers.jun.zimu.converter;

public class ConverterFactory {
	public static Converter getConverter(String ext){
		if ("ass".equals(ext)) {
			return new ConverterAss();
		}else if ("srt".equals(ext)) {
			return new ConverterSrt();
		}
		return null;
	}
}
