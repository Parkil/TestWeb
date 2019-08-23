package util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

public class WebElementExpand {
	
	/*
	 * driver.findElement를 이용하여 생성된 WebElement에서는 정상적으로 작동하지만 
	 * PageFactory를 이용하여 mapping된 WebElement에서는 작동하지 않는다.(WrapsDriver로 Casting이 되지 않는다고 에러메시지 표시)
	 * 이를 막기 위해서는 driver를 메소드나 생성자 인자로 받아서 처리하는 방법이 가장 나을듯함
	 */
	public static void setAttribute(WebElement element, String attributName, String value) {
		System.out.println("element : "+element);
		WrapsDriver wd = (WrapsDriver)element;
		JavascriptExecutor je = (JavascriptExecutor)wd.getWrappedDriver();
		
		je.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributName, value);
	}
	
	/*
	 * 해당위치를 하이라이트 처리
	 */
	public static void highlightElement(WebElement element) {
		for(int i = 0 ; i<5 ; i++) {
			WrapsDriver wd = (WrapsDriver)element;
			JavascriptExecutor je = (JavascriptExecutor)wd.getWrappedDriver();
			
			je.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "color:green; border: 2px solid yellow;");
			je.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "");
			je.executeScript("alert(arguments[0]);", element, ""); //alert창으로 확인결과 WebElement를 JavascriptExecutor에 입력하면 javascript html dom객체로 인식
		}
	}
	
	/*
	 * 특정 요소만 스크린샷 생성
	 */
	public static File captureElement(WebElement element) throws Exception{
		WrapsDriver wd = (WrapsDriver)element;
		TakesScreenshot tss = (TakesScreenshot)wd.getWrappedDriver();
		
		File screen = tss.getScreenshotAs(OutputType.FILE);
		
		BufferedImage temp_img = ImageIO.read(screen);
		
		int width  = element.getSize().width;
		int height = element.getSize().height;
		
		//Rectangle rect = new Rectangle(width, height);
		
		Point p = element.getLocation();
		
		BufferedImage dest_img = temp_img.getSubimage(p.getX(), p.getY(), width, height);
		
		ImageIO.write(dest_img, "png", screen);
		
		return screen;
	}
}
