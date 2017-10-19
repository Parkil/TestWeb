package egovframework.example.sample.mapper;

import java.util.List;

import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

public interface SampleMapper {
	public void insertSample(SampleVO vo);

	public void updateSample(SampleVO vo);

	public void deleteSample(SampleVO vo);

	public SampleVO selectSample(SampleVO vo);

	public List<?> selectSampleList(SampleDefaultVO searchVO);

	public int selectSampleListTotCnt(SampleDefaultVO searchVO);
}
