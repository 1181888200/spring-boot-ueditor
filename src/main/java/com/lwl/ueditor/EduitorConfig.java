package com.lwl.ueditor;

import org.springframework.beans.factory.annotation.Value;

public class EduitorConfig {

	@Value("${ueditor.upload.path}")
	public static final String FTP_SQL_IMG_UPLOADPATH = "E:\\";//上传文件夹
	
	
	@Value("${ueditor.config.name}")
	public static final String UE_CONFIG_NAME = "ueditor.config.json";
	
	@Value("${ueditor.config.path}")
	public static final  String UE_CONFIG_PATH = null;
	
}
