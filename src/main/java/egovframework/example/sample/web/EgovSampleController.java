package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.example.mvccustom.SessionAttribute;
import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;

@Controller
/*
 * 대상으로 지정한 파라메터를 세션에 sessionStatus.setComplete()가 호출될때까지 저장한다.
 * 저장하는 대상
 * model.addAttribute("sampleVO", new SampleVO());
 * @ModelAttribute
 */
@SessionAttributes("sessionVO")
public class EgovSampleController {

    private final EgovSampleService sampleService;
    protected final EgovPropertyService propertiesService;
    protected final DefaultBeanValidator beanValidator;

    public EgovSampleController(EgovSampleService sampleService,
                                EgovPropertyService propertiesService, DefaultBeanValidator beanValidator) {
        this.sampleService = sampleService;
        this.propertiesService = propertiesService;
        this.beanValidator = beanValidator;
    }

    private final Logger log = LoggerFactory.getLogger(EgovSampleController.class);

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/sample/egovSampleList.do")
    public String selectSampleList(
            @ModelAttribute("searchVO") SampleDefaultVO searchVO,
            ModelMap model,
            @RequestHeader Map<String,Object> header_map,
            @SessionAttribute(value="sessionVO") Object obj)
            throws Exception {
        
        model.addAttribute("sessionVO",searchVO);
        
        /** EgovPropertyService.sample */
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));

        /** pageing setting */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List sampleList = sampleService.selectSampleList(searchVO);
        model.addAttribute("resultList", sampleList);

        int totCnt = sampleService.selectSampleListTotCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

        return "/sample/egovSampleList";
    }
    @RequestMapping("/sample/selectSample.do")
    public @ModelAttribute("sampleVO")
    SampleVO selectSample(SampleVO sampleVO,
            @ModelAttribute("searchVO") SampleDefaultVO searchVO)
            throws Exception {
        
        return sampleService.selectSample(sampleVO);
    }

    @RequestMapping("/sample/addSampleView.do")
    public String addSampleView(
            @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model, HttpSession session, @ModelAttribute("sessionVO") SampleDefaultVO sessionVO)
            throws Exception {
        model.addAttribute("sampleVO", new SampleVO());
        return "/sample/egovSampleRegister";
    }

    @RequestMapping("/sample/addSample.do")
    @Transactional(rollbackFor={Exception.class}) //rollbackFor에서 지정된 예외가 발생하면 Transaction을 rollback처리한다.
    public String addSample(
            @ModelAttribute("searchVO") SampleDefaultVO searchVO,
            @Valid SampleVO sampleVO, BindingResult bindingResult, Model model,
            HttpServletRequest request,
            SessionStatus status)
            throws Exception {
        
        sampleVO.getFilevo().transferFile("c:/web-down");
        if (bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.debug(o.toString());
            }
            model.addAttribute("sampleVO", sampleVO);
            return "/sample/egovSampleRegister";
        }

        sampleService.insertSample(sampleVO);
        status.setComplete();
        return "forward:/sample/egovSampleList.do";
    }

    @RequestMapping("/sample/updateSampleView.do")
    public String updateSampleView(@RequestParam("selectedId") String id,
            @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model)
            throws Exception {
        SampleVO sampleVO = new SampleVO();
        sampleVO.setId(id);
        model.addAttribute(selectSample(sampleVO, searchVO));
        return "/sample/egovSampleRegister";
    }

    @RequestMapping("/sample/updateSample.do")
    public String updateSample(
            @ModelAttribute("searchVO") SampleDefaultVO searchVO,
            SampleVO sampleVO, BindingResult bindingResult, Model model,
            SessionStatus status) throws Exception {

        beanValidator.validate(sampleVO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sampleVO", sampleVO);
            return "/sample/egovSampleRegister";
        }

        sampleService.updateSample(sampleVO);
        status.setComplete();
        return "forward:/sample/egovSampleList.do";
    }

    @RequestMapping("/sample/deleteSample.do")
    public String deleteSample(SampleVO sampleVO,
            @ModelAttribute("searchVO") SampleDefaultVO searchVO,
            SessionStatus status) throws Exception {
        sampleService.deleteSample(sampleVO);
        status.setComplete();
        return "forward:/sample/egovSampleList.do";
    }

    @RequestMapping("/login.do")
    public String loginview() throws Exception {
        return "/login/login";
    }
    
    @RequestMapping(value="/sample/jsonptest.do")
    @ResponseBody
    public String jsonpTest() throws Exception {
        Map<String,String> map = new HashMap<String,String>();
        map.put("key1", "value1");
        map.put("키2", "값2");
        
        ObjectMapper om = new ObjectMapper();
        StringBuffer sb = new StringBuffer();
        sb.append("callback(");
        sb.append(om.writeValueAsString(map));
        sb.append(")");
        
        log.info("JSONP Message : "+sb.toString());
        return sb.toString();
    }
    
    @RequestMapping(value="/sample/jsontest.do")
    @ResponseBody
    public String jsonTest() throws Exception {
        Map<String,String> map = new HashMap<String,String>();
        map.put("key1", "value1");
        map.put("키2", "값2");
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(map);
    }
    
    /*
     * @ModelAttribute를 지정하지 않아도 vo와 동일한 이름의 파라메터가 존재하면 자동으로 binding된다.
     * VO에 Map이나 List가 존재하면 파라메터 명으로 바로 binding을 시킬수 있다.
     * 
     * List - {파라메터명}[Collection index]=value
     * ex) 파라메터가 list1이라는 이름의 List<String>일때
     * http://localhost:8080/test.do?list1[0]=1 - list의 첫번째 값을 1로 설정
     * http://localhost:8080/test.do?list1[3]=1 - list의 세번째 값을 1로 설정(첫번째,두번째 값은 null로 지정)
     * 
     * Map - {파라메터명}[key]=value
     * http://localhost:8080/test.do?map1['b']=b - map에 b라는 key의 값을 b로 지정
     * 
     * List나 Map의 경우 VO에서 굳이 초기화를 하지 않아도 정상적으로 작동한다.
     */
    @RequestMapping("/multiParamTest.do")
    public List<String> multiParamTest(
            @ModelAttribute("searchVO") SampleDefaultVO vo,
            HttpServletRequest req,
            HttpServletResponse resp) throws Exception {
        log.info("listParam {}",vo.getList());
        log.info("mapParam {}",vo.getMap());
        
        ArrayList<String> retList = new ArrayList<String>();
        return retList;
    }
    /*
     * method에 ModelAttribute를 설정시 해당 Controller에서 반환하는 View에서는 해당데이터를 가져다 쓸 수 있다.
     */
    @ModelAttribute("ControllerScopeModel")
    public String scopeModel() {
        return "scopeModel";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("Controller binder 실행");
        binder.setDisallowedFields("id"); //[id]라는 이름을 가지는 파라메터를 binding에서 제외처리
    }
}