package com.lwl.controller.ueditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lwl.ueditor.EduitorConfig;

/**
 * 获取上传后的文件入口
 * @author lwl
 * @create 2019年1月28日 下午2:08:29
 * @version 1.0
 */
@Controller
@RequestMapping
public class UeditorResourceController {

	
	/**
	 * 此方法根据ueditor.config.json 中各自对应的【xxxxxPathFormat】属性
	 * @param type
	 * @param date
	 * @param file
	 * @param response
	 * @throws IOException
	 * @author lwl
	 * @create 2019年1月28日 下午2:08:45
	 */
	@RequestMapping(value="/upload/ueditor/{type}/{date}/{file}")
	public void download(@PathVariable String type , @PathVariable String date , @PathVariable String file, HttpServletResponse response) throws IOException {
        byte[] stream =  getMedia(type, date, file, null);
        writeStream(response, stream);
    }
	
	
	private byte[] getMedia(String type , String date ,String fileName , String suffix) throws IOException {
        File file = new File(buildPath(EduitorConfig.FTP_SQL_IMG_UPLOADPATH ,"/upload/ueditor", type , date , fileName ));
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }
    
    
    private void writeStream(HttpServletResponse response, byte[] byteStream) {
        if (byteStream == null) {
            return;
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(byteStream);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private String buildPath(String... paths){
        if(paths == null || paths.length == 0){
            return "/";
        }
        StringBuilder result = new StringBuilder();
        for (String path : paths) {
            if(path.startsWith("http://") || path.indexOf(":") >= 0){
                //
            }else if(path.indexOf("/")!=0 && path.indexOf("\\") !=0){
                result.append("/");
            }
            result.append(path);
        }
        return result.toString();
    }
	
}
