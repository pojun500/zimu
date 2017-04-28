package pers.jun.zimu.converter;

public interface Converter {
	public boolean couldChange(String line);
	public String convert(String line);
}
