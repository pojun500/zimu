package pers.jun.zimu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pers.jun.zimu.converter.Converter;
import pers.jun.zimu.converter.ConverterFactory;

public class MainTest {

	@Test
	public void testMain() {
		String[] args = {"D:\\zimu"};
		Main.main(args);
		System.exit(0);
	}
	@Test
	public void testChange() {
		Converter converter = ConverterFactory.getConverter("ass");
		converter.couldChange("[Events]");
		converter.couldChange("Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
		String line = "Dialogue: 0,0:00:02.01,0:00:04.02,*Default,NTP,0000,0000,0000,,测试\\N{\fn方正综艺_GBK\fs14\b0\3c&H2F2F2F&}test1,test2 test3...";
		line = converter.convert(line);
		assertEquals("测试\r\ntest1,test2 test3...\r\n", line);
	}
}
