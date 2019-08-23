/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import egovframework.example.cmmn.service.FileVO;


/**
 * @Class Name : SampleVO.java
 * @Description : SampleVO Class
 * @Modification Information @ * @ 수정일 수정자 수정내용 @ --------- ---------
 *               ------------------------------- @ 2009.03.16 최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see Copyright (C) by MOPAS All right reserved.
 */
public class SampleVO extends SampleDefaultVO {

    private static final long serialVersionUID = 1L;

    /** 아이디 */
    private String id;

    /** 이름 */
    @NotEmpty(message = "name은 필수값입니다.")
    @Size(min=1,max=10,message="{errors.range}") //직접 메시지를 입력할수도 있고 다음과 같이 messageSource에 저장된 건을 가져다 쓸수도 있음.
    private String name;

    /** 내용 */
    /*
     * @NotEmpty같이 hibernate Valid를 이용하는 경우+외부 메시지를 이용할 경우 WEB-INF/classes/ValidationMessages.properties에 지정된 메시지를 가져온다.
     * (Spring messageSource와 호환안됨)
     * spring messageSource와 같이 이용하게 하려면 LocalValidatorFactoryBean에서 설정을 하라고 하는데 이는 좀더 확인을 해봐야 함
     */
    @NotEmpty(message = "{errors.required}") 
    private String description;

    /** 사용여부 */
    private String useYn;

    /** 등록자 */
    @NotEmpty(message = "regUser는 필수값입니다.") //@Valid 어노테이션에서 검증할 유효성을 어노테이션으로 지정
    private String regUser;
    
    /*
     * converter로 multipart file을 FileVO로 변환해서 받기 위해서는 VO안에 변환할 대상 vo를 
     * 지정하고 html input type="file" name="filevo"로 지정하면 Controller호출시 자동으로 
     * Converter가 실행되어 형변환된 내용이 VO에 담기게 된다.
     */
    private FileVO filevo;

    /*
     * Number Format 지정
     */
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,###.###")
    private BigDecimal decimal = new BigDecimal("10000000000");
    
    private Date date;
    
    private Map<String,String> map;
    
    private List<String> list;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegUser() {
        return regUser;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }
    
    public FileVO getFilevo() {
        return filevo;
    }

    public void setFilevo(FileVO filevo) {
        this.filevo = filevo;
    }

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}