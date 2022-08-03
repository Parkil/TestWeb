package egovframework.example.cmmn.web;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

public class EgovImgPaginationRenderer extends AbstractPaginationRenderer {

    public EgovImgPaginationRenderer() {
        String strWebDir = "/###ARTIFACT_ID###/images/egovframework/cmmn/";

        firstPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">" +
                            "<image src='" + strWebDir + "btn_page_pre10.gif' border=0/></a>&#160;"; 
        previousPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">" +
                                "<image src='" + strWebDir + "btn_page_pre1.gif' border=0/></a>&#160;";
        currentPageLabel = "<strong>{0}</strong>&#160;";
        otherPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
        nextPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">" +
                            "<image src='" + strWebDir + "btn_page_next10.gif' border=0/></a>&#160;";
        lastPageLabel = "<a href=\"#\" onclick=\"{0}({1}); return false;\">" +
                            "<image src='" + strWebDir + "btn_page_next1.gif' border=0/></a>&#160;";
    }
}
