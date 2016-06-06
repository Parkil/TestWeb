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

import javax.validation.constraints.NotNull;

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
	@NotNull(message = "ID는 필수값입니다.") //@Valid 어노테이션에서 검증할 유효성을 어노테이션으로 지정
	private String id;

	/** 이름 */
	private String name;

	/** 내용 */
	private String description;

	/** 사용여부 */
	private String useYn;

	/** 등록자 */
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
	private BigDecimal sss = new BigDecimal("10000000000");

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

	public BigDecimal getSss() {
		return sss;
	}

	public void setSss(BigDecimal sss) {
		this.sss = sss;
	}
}
