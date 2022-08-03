package egovframework.example.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

@Repository("sampleDAO")
public class SampleDAO extends EgovAbstractDAO {

    public String insertSample(SampleVO vo) {
        return (String)insert("sampleDAO.insertSample_S", vo);
    }

    public void updateSample(SampleVO vo) {
        update("sampleDAO.updateSample_S", vo);
    }

    public void deleteSample(SampleVO vo) {
        delete("sampleDAO.deleteSample_S", vo);
    }

    public SampleVO selectSample(SampleVO vo) {
        return (SampleVO) select("sampleDAO.selectSample_S", vo);
    }

    @SuppressWarnings("rawtypes")
    public List selectSampleList(SampleDefaultVO searchVO) {
        return list("sampleDAO.selectSampleList_D", searchVO);
    }

    public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
        return (Integer)getSqlMapClientTemplate().queryForObject("sampleDAO.selectSampleListTotCnt_S", searchVO);
    }
}
