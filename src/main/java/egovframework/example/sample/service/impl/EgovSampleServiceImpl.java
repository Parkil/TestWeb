package egovframework.example.sample.service.impl;

import java.util.List;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.example.sample.mapper.SampleMapper;
import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

@Service("sampleService")
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements
        EgovSampleService {

    private final Logger log = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

    private final SampleMapper sampleDAO;
    
    private final EgovIdGnrService egovIdGnrService;

    public EgovSampleServiceImpl(SampleMapper sampleDAO, EgovIdGnrService egovIdGnrService) {
        this.sampleDAO = sampleDAO;
        this.egovIdGnrService = egovIdGnrService;
    }

    public String insertSample(SampleVO vo) throws Exception {
        log.debug(vo.toString());

        String id = egovIdGnrService.getNextStringId();
        vo.setId(id);
        log.debug(vo.toString());

        sampleDAO.insertSample(vo);
        return id;
    }

    public void updateSample(SampleVO vo) {
        sampleDAO.updateSample(vo);
    }

    public void deleteSample(SampleVO vo) {
        sampleDAO.deleteSample(vo);
    }

    public SampleVO selectSample(SampleVO vo) throws Exception {
        SampleVO resultVO = sampleDAO.selectSample(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    public List<?> selectSampleList(SampleDefaultVO searchVO) {
        return sampleDAO.selectSampleList(searchVO);
    }

    public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
        return sampleDAO.selectSampleListTotCnt(searchVO);
    }
}
