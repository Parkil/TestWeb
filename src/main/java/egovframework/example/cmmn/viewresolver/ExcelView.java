package egovframework.example.cmmn.viewresolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/*
 * 사용자 정의 Excel View
 * spring 3.2.5에서는 xls(HSSF)만 지원하며 그 이후버전에서는 현재 HSSFWorkbook으로 되어있는 파라메터가 
 * Workbook인터페이스로 변경되어 xlsx(XSSF)나 xlsx streaming(SXSSF)도 지원이 가능한것으로 판단됨.
 */
public class ExcelView extends AbstractXlsxView {
    
    private final Logger log = LoggerFactory.getLogger(ExcelView.class);
    
    @Override
    protected void buildExcelDocument(@NonNull Map<String, Object> model, @NonNull Workbook workbook,
                                      @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws Exception {
        log.info("Custom Excel View Called");

        Sheet workbookSheet = workbook.createSheet();
        Row titleRow = workbookSheet.createRow(0);

        for (int cellIdx = 0; cellIdx < 11; cellIdx++) {
            Cell cell = titleRow.createCell(cellIdx);

            String title = (cellIdx != 10) ? "값" : "합계";
            cell.setCellValue(title);
        }

        String sumFormatStr = "SUM(%1$s:%2$s)";

        for (int rowIdx = 1; rowIdx <= 2500; rowIdx++) {
            Row row = workbookSheet.createRow(rowIdx);
            for (int cellIdx = 0; cellIdx < 11; cellIdx++) {
                Cell cell;

                if(cellIdx != 10) {
                    cell = row.createCell(cellIdx, CellType.NUMERIC);
                    cell.setCellValue(1);
                }else {
                    cell = row.createCell(cellIdx, CellType.FORMULA);
                    cell.setCellFormula( String.format(sumFormatStr, "A"+(rowIdx+1), "J"+(rowIdx+1)) );
                }
            }
        }

        for (int cellIdx = 0; cellIdx < 10; cellIdx++) {
            workbookSheet.autoSizeColumn(cellIdx); //셀 크기 자동지정
        }

        workbook.close();
    }
}