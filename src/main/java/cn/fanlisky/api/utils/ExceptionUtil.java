package cn.fanlisky.api.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @description 异常工具类
 * @author Gary
 */
public class ExceptionUtil {

	private ExceptionUtil() {
	}

	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter(sw)){
			t.printStackTrace(pw);
			return sw.toString();
		}
	}
}
