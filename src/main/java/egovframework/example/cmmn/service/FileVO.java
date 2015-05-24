package egovframework.example.cmmn.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/*
 * MultipartFile -> FileVO Convert를 위한 VO
 */
public class FileVO {
	private String orgFileName = "";
	private String realFileName;
	private String ext;
	private File file;
	private MultipartFile multipartfile = null;
	private long fileSize;
	
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public String getRealFileName() {
		return realFileName;
	}
	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public MultipartFile getMultipartfile() {
		return multipartfile;
	}
	public void setMultipartfile(MultipartFile multipartfile) {
		this.multipartfile = multipartfile;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public void transferFile(String filePath) throws IllegalAccessException, IOException {
		if(fileSize != 0 || orgFileName.intern() != "".intern()) {
			file = new File(filePath+"/"+orgFileName);
			multipartfile.transferTo(file);
		}
	}
}
