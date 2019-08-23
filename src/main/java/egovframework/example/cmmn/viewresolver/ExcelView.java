package egovframework.example.cmmn.viewresolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/*
 * 사용자 정의 Excel View
 * spring 3.2.5에서는 xls(HSSF)만 지원하며 그 이후버전에서는 현재 HSSFWorkbook으로 되어있는 파라메터가 
 * Workbook인터페이스로 변경되어 xlsx(XSSF)나 xlsx streaming(SXSSF)도 지원이 가능한것으로 판단됨.
 */
public class ExcelView extends AbstractExcelView {
    
    private Logger log = Logger.getLogger(PdfView.class);
    
    public ExcelView() {
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        log.info("Custom Excel View Called");
        Sheet sh = wb.createSheet();
        Row title_row = sh.createRow(0);
        for (int cellnum = 0; cellnum < 11; cellnum++) {
            Cell cell = title_row.createCell(cellnum);

            String title = (cellnum != 10) ? "값" : "합계";
            cell.setCellValue(title);
        }
        
        String sum_format_str = "SUM(%1$s:%2$s)";
        for (int rownum = 1; rownum <= 2500; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < 11; cellnum++) {
                Cell cell = row.createCell(cellnum);
                
                if(cellnum != 10) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(1);
                }else {
                    cell.setCellType(Cell.CELL_TYPE_FORMULA);
                    cell.setCellFormula( String.format(sum_format_str, "A"+(rownum+1), "J"+(rownum+1)) );
                }
            }
        }
        
        for (int cellnum = 0; cellnum < 10; cellnum++) {
            sh.autoSizeColumn(cellnum); //셀 크기 자동지정
        }
        wb.close();
    }
}
