package com.lwl.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lwl.ueditor.EduitorConfig;
import com.lwl.ueditor.PathFormat;
import com.lwl.ueditor.define.AppInfo;
import com.lwl.ueditor.define.BaseState;
import com.lwl.ueditor.define.FileType;
import com.lwl.ueditor.define.State;

public class BinaryUploader {

	public static void main(String[] args) {
		System.out.println(null + "");
	}
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
//			FileItemIterator iterator = upload.getItemIterator(request);
//
//			while (iterator.hasNext()) {
//				fileStream = iterator.next();
//
//				if (!fileStream.isFormField())
//					break;
//				fileStream = null;
//			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());


			if (multipartFile == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");//  /ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}
			String originFileName = multipartFile.getOriginalFilename();//111.png
			String suffix = FileType.getSuffixByFilename(originFileName);//.png

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());//111
			savePath = savePath + suffix;///ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}.png

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);///ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}.png

//			String physicalPath = (String) conf.get("rootPath") + savePath;//D:/Program Files/apache-tomcat-7.0.64/wtpwebapps//ueditor/jsp/upload/image/20151116/1447675575987060166.png
			String physicalPath = EduitorConfig.FTP_SQL_IMG_UPLOADPATH + savePath;
			/*************zrk修改：改变文件存储路径*************/
//			String physicalPath = (String) conf.get("physicsPath");
//			if(physicalPath!=null&&!"".equals(physicalPath))
//				physicalPath += savePath;
//			else
//				physicalPath = conf.get("rootPath") + savePath;
//			/**************************/
			InputStream is = multipartFile.getInputStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				if(storageState.getInfoString("url")==null){
					storageState.putInfo("url", PathFormat.format(savePath));
				}
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (IOException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (Exception e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
