package com.zlp.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.zlp.util.CommonUtil;

public class LoggerFactory {
	
	private static Logger logger = Logger.getLogger(LoggerFactory.class.getName());
	
	private LoggerFactory() {}
	
//	private final static String fileName = "system";
	private final static ResourceBundle rb;
//	private static Properties conf = new Properties();
	static {
//		final String configFileName = ResourceBundle.getBundle(fileName).getString("run.config.file.name");
		rb = ResourceBundle.getBundle(CommonUtil.configFileName());
//		InputStream stream = null;
//		try {
//			stream = LoggerFactory.class.getResourceAsStream("../../../../log.properties");
//			if(stream == null) stream = LoggerFactory.class.getResourceAsStream(file);
//			conf.load(stream);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			logger.severe(e.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//			logger.severe(e.toString());
//		} finally {
//			try{
//				if(stream != null) stream.close();
//			} catch (IOException e) {
//				System.err.println(e.toString());
//			}
//		}
	}
	
	public static Logger getLogger(Class<?> clazz) {
		
		Logger log = Logger.getLogger(clazz.getName());
		
		log.setLevel(logLevel(rb.getString("log.custom.level")));
		
		String logFileDir = rb.getString("log.custom.dir");
		if(logFileDir == null) logFileDir = System.getProperty("user.home") + "/default.log";
		
		if(!"0".equals(rb.getString("log.custom.everyday"))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logFileDir = logFileDir.substring(0, logFileDir.lastIndexOf(".log")) + "-" + sdf.format(new Date()) + ".log";
		} 
		
		int limit = 0;
		if(rb.getString("log.custom.limit") != null) {
			limit = Integer.parseInt(rb.getString("log.custom.limit").toString());
		}
		int count = 1;
		if(rb.getString("log.custom.count") != null) {
			count = Integer.parseInt(rb.getString("log.custom.count").toString());
		}
		boolean append = true;
		if(rb.getString("log.custom.append") != null) {
			append = Boolean.parseBoolean(rb.getString("log.custom.append"));
		}
		
		FileHandler fileHandler = null;
		try {
			File logFile = new File(new File(logFileDir).getParent());
			if(!logFile.exists()) logFile.mkdirs();
			
			fileHandler = new FileHandler(logFileDir, limit, count, append);
			if(!"".equals(rb.getString("log.custom.format"))) {
				Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(rb.getString("log.custom.format"));
                fileHandler.setFormatter((Formatter)clz.newInstance());
			} else {
				fileHandler.setFormatter(new SimpleFormatter());
			}
			log.addHandler(fileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
			logger.severe(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe(e.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.severe(e.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.severe(e.toString());
		} catch (InstantiationException e) {
			e.printStackTrace();
			logger.severe(e.toString());
		}
		
		return log;
	}
	
	private static Level logLevel(String level) {
		if(level == null) return Level.INFO;
		switch(level) {
		case "OFF": return Level.OFF;
		case "SEVERE": return Level.SEVERE;
		case "WARNING": return Level.WARNING;
		case "INFO": return Level.INFO;
		case "CONFIG": return Level.CONFIG;
		case "FINE": return Level.FINE;
		case "FINER": return Level.FINER;
		case "FINEST": return Level.FINEST;
		case "ALL": return Level.ALL;
		default: return Level.INFO;
		}
	}
	
}
