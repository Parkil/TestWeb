package util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

/*
 * 2개의 이미지를 비교하는 클래스
 */
public class CompareUtil {
	public enum Result { Matched, SizeMismatch, PixcelMismatch};
	
	public static Result CompareImage(String baseFile, String actualFile) {
		Result compareResult = Result.PixcelMismatch;
		
		Image baseImage	  = Toolkit.getDefaultToolkit().getImage(baseFile);
		Image actualImage = Toolkit.getDefaultToolkit().getImage(actualFile);
		
		try {
			PixelGrabber baseImageGrab = new PixelGrabber(baseImage, 0, 0, -1, -1, false);
			PixelGrabber actualImageGrab = new PixelGrabber(actualImage, 0, 0, -1, -1, false);
			
			int[] baseImageData = null;
			int[] actualImageData = null;
			
			if(baseImageGrab.grabPixels()) {
				int width = baseImageGrab.getWidth();
				int height = baseImageGrab.getHeight();
				
				baseImageData = new int[width * height];
				baseImageData = (int[])baseImageGrab.getPixels();
			}
			
			if(actualImageGrab.grabPixels()) {
				int width = actualImageGrab.getWidth();
				int height = actualImageGrab.getHeight();
				
				actualImageData = new int[width * height];
				actualImageData = (int[])actualImageGrab.getPixels();
			}
			
			System.out.println(baseImageGrab.getHeight()+"<>"+actualImageGrab.getHeight());
			System.out.println(baseImageGrab.getWidth()+"<>"+actualImageGrab.getWidth());
			
			if(baseImageGrab.getWidth() != actualImageGrab.getWidth() || baseImageGrab.getHeight() != actualImageGrab.getHeight()) {
				compareResult = Result.SizeMismatch;
			}else if(java.util.Arrays.equals(baseImageData, actualImageData)) {
				compareResult = Result.Matched;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return compareResult;
	}
}
