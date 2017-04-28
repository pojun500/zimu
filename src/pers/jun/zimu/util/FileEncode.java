package pers.jun.zimu.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileEncode {
	public static String getFileEncode(String path) {
		File file = new File(path);
		return getFileEncode(file);
	}

	public static String getFileEncode(File file) {
		String charset = "asci";
		byte[] first3Bytes = new byte[3];
		BufferedInputStream bis = null;
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				String str1 = charset;
				return str1;
			}
			if ((first3Bytes[0] == -1) && (first3Bytes[1] == -2)) {
				charset = "Unicode";
				checked = true;
			} else if ((first3Bytes[0] == -2) && (first3Bytes[1] == -1)) {
				charset = "Unicode";
				checked = true;
			} else if ((first3Bytes[0] == -17) && (first3Bytes[1] == -69) && (first3Bytes[2] == -65)) {
				charset = "UTF8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				while ((read = bis.read()) != -1) {
					if (read >= 240)
						break;
					if ((128 <= read) && (read <= 191))
						break;
					if ((192 <= read) && (read <= 223)) {
						read = bis.read();
						if ((128 > read) || (read > 191)) {
							break;
						}
					} else if ((224 <= read) && (read <= 239)) {
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						charset = "UTF-8";
						break;
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();

			if (bis != null)
				try {
					bis.close();
				} catch (IOException localIOException1) {
				}
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException localIOException2) {
				}
		}
		return charset;
	}

	public static String getEncode(int flag1, int flag2, int flag3) {
		String encode = "";

		if ((flag1 == 255) && (flag2 == 254)) {
			encode = "Unicode";
		} else if ((flag1 == 254) && (flag2 == 255)) {
			encode = "UTF-16";
		} else if ((flag1 == 239) && (flag2 == 187) && (flag3 == 191)) {
			encode = "UTF8";
		} else {
			encode = "asci";
		}
		return encode;
	}
	
	public static String getCode(File file) {
		String code = getFileEncode(file);
		if ("asci".equals(code)) {
			code = "GBK";
		}
		return code;
	}
}