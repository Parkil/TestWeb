package egovframework.example.sample.service;

import java.util.List;


public interface EgovSampleService {
	
    String insertSample(SampleVO vo) throws Exception;
    
    void updateSample(SampleVO vo) throws Exception;
    
    void deleteSample(SampleVO vo) throws Exception;
    
    SampleVO selectSample(SampleVO vo) throws Exception;
    
    List selectSampleList(SampleDefaultVO searchVO) throws Exception;
    
    int selectSampleListTotCnt(SampleDefaultVO searchVO);
}