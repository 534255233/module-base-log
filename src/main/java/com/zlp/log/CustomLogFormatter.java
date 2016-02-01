package com.zlp.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 描述：自定义日记格式
 * 暂时没用
 * @author zlp
 *
 */
public class CustomLogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sb.append(sdf.format(new Date())).append(" ");
		sb.append(record.getLoggerName()).append(" ");
		sb.append("\n");
		sb.append(record.getLevel()).append(" ");
		sb.append(record.getMessage());
		sb.append("\n");
		return sb.toString();
	}

}
