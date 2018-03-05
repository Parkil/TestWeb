package gen_template.testcase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * 데이터 주도 테스트에 이용되는 데이터를 반환하는 클래스
 */
@SuppressWarnings("rawtypes")
public class GetDataCollection {
	
	//하드코딩된 데이터를 반환
	public Collection getPlainData() {
		return Arrays.asList(new Object[][] {
				{"2222","1111"},
				{"4444","3333"},
				{"6666","5555"},
				{"8888","7777"},
				{"user","user"}
		});
	}
	
	//csv형식의 파일을 읽어들여 데이터를 반환 파일이 존재하지 않을때에는 null을 반환
	public Collection getCSVData(String path) throws Exception{
		List<Object[]> ret_list = new ArrayList<Object[]>();
		File f = new File(path);
		
		if(!f.exists()) {
			return null;
		}
		
		FileInputStream fis = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		String line = null;
		String val[] = null;
		while((line = br.readLine()) != null) {
			val = line.split(",");
			ret_list.add(val);
		}
		
		br.close();
		fis.close();
		
		return ret_list;
	}
	
	//엑셀형식의 파일을 읽어들여 데이터를 반환 파일이 존재하지 않을 경우에는 null을 반환
	public Collection getExcelData(String path) throws Exception {
		List<Object[]> ret_list = new ArrayList<Object[]>();
		File f = new File(path);
		
		if(!f.exists()) {
			return null;
		}
		
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		DataFormatter df = new DataFormatter();
		
		Iterator<Row> rowIter = sheet.iterator();
		Row temp = null;
		String temp_arr[] = null;
		while(rowIter.hasNext()) {
			temp = rowIter.next();
			temp_arr = new String[2];
			
			/*
			 * formatCellValue
			 * 엑셀에서 지정한 서식대로 데이터를 표시하기 위해 사용
			 * format을 이용하지 않고 cell.toString()이나 다른방식으로 데이터를 처리하게 되면
			 * 셀에 '1111' 데이터가 들어있고 텍스트서식으로 지정되었다고 가정할때 
			 * 1111.0 이나 -1111 처럼 잘못 표시되는 경우가 생긴다.
			 */
			temp_arr[0] = df.formatCellValue(temp.getCell(0));
			temp_arr[1] = df.formatCellValue(temp.getCell(1));
			
			ret_list.add(temp_arr);
		}
		
		workbook.close();
		return ret_list;
	}
}
