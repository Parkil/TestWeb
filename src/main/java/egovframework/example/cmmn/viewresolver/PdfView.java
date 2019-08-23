package egovframework.example.cmmn.viewresolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/*
 * 사용자 정의 PDF View
 */
public class PdfView extends AbstractPdfView {
    
    protected Log log = LogFactory.getLog(this.getClass());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        log.info("PdfView Model : "+model);
        
        document.addTitle("Pdf View");
        document.add(new Paragraph("First : First"));
        document.add(new Paragraph("Second : Second"));
        
        Table table = new Table(4);
        table.addCell("1");
        table.addCell("2");
        table.addCell("3");
        table.addCell("4");
        
        table.addCell("2-1");
        table.addCell("2-2");
        table.addCell("2-3");
        table.addCell("2-4");
        
        document.add(table);
    }
}
