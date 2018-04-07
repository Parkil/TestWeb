package egovframework.example.cmmn.viewresolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/*
 * PDF로 표시되는 View AbstractPdfView를 상속받아 구현한다.
 */
public class PdfView extends AbstractPdfView {
	
	private Logger log = Logger.getLogger(PdfView.class);
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//model에는 Controller에속한 SessionAttribute, ModelAttribute, 그리고 ModelAndView.addObject로 입력한 값이 들어온다
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
