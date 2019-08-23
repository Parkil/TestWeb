package egovframework.example.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.cmmn.service.FileVO;

/*
 * MultipartFile을 FileVO로 변환
 */
public class MultipartFileToFileVOConverter implements Converter<MultipartFile, FileVO> {

    @Override
    public FileVO convert(MultipartFile source) {
        FileVO result = null;
        
        if(source == null) {
            result = null;
        }else {
            result = new FileVO();
            String orgFileName = source.getOriginalFilename();
            result.setOrgFileName(orgFileName);;
            result.setMultipartfile(source);
            result.setFileSize(source.getSize());
            
            int idx = orgFileName.lastIndexOf(".");
            if(idx == -1) {
                result.setExt(null);
            }else {
                String ext = orgFileName.substring(idx+1);
                result.setExt(ext);
            }
        }
        
        return result;
    }
}
