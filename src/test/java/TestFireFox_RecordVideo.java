import static org.junit.Assert.*;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.media.VideoFormatKeys;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.Util;


public class TestFireFox_RecordVideo {
	private static WebDriver driver;
	private static JavascriptExecutor je;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Util.getFireFoxDriver();
		je = (JavascriptExecutor)driver;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * monte 라이브러리(MonteScreenRecorder.jar)를 이용한 테스트과정 동영상 녹화
	 * 녹화된 동영상은 windows의 경우 c:/사용자/[사용자명]/동영상 폴더에 ScreenRecording [녹화시간].[지정한 동영상 확장자]명으로 저장된다.
	 * 
	 * 듀얼 모니터의 경우 주 모니터만 동영상으로 녹화를 하는 듯하다
	 * 
	 * 주의할점은 테스트가 진행되는 화면자체를 녹화하기 때문에 다른 동작을 수행하면 그 동작이 녹화가 된다 주의할것
	 */
	@Test
	public void test_using_monte_library() throws Exception{
		
		//monte 라이브러리에서 이용할 화면 정보 추출(AWT 이용)
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		/*
		 * ScreenRecorder 에서 사용할 포맷 설정(아래 설정 기준)
		 * file_format		= file포맷을 avi로 지정
		 * screen_format	= 초당 프레임 15, 색심도 24
		 * mouse_format		= 마우스커서 색상 및 리프레시 주기
		 * audio_format		= null일 경우에는 소리는 녹음하지 않음
		 */
		Format file_format		= new Format(FormatKeys.MediaTypeKey, MediaType.FILE, FormatKeys.MimeTypeKey, FormatKeys.MIME_AVI);
		Format screen_format	= new Format(FormatKeys.MediaTypeKey, MediaType.VIDEO, 
											 FormatKeys.EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
											 VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
											 VideoFormatKeys.DepthKey, (int)24,
											 VideoFormatKeys.FrameRateKey, Rational.valueOf(15),
											 VideoFormatKeys.QualityKey, 1.0f,
											 VideoFormatKeys.KeyFrameIntervalKey, (int)(15*60)
											);
		
		Format mouse_format		= new Format(FormatKeys.MediaTypeKey, MediaType.VIDEO, VideoFormatKeys.EncodingKey, "black", VideoFormatKeys.FrameRateKey, Rational.valueOf(30));
		Format audio_format		= null;
		
		ScreenRecorder sr = new ScreenRecorder(gc, file_format, screen_format, mouse_format, audio_format);
		sr.start();
		
		driver.get("http://google.co.kr");
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!");
		element.submit();
		

		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				System.out.println(input.getTitle());
				return input.getTitle().startsWith("Cheese!");
			}
			
		});
		
		assertEquals("Cheese! - Google 검색", driver.getTitle());
		
		driver.quit();
		sr.stop();
	}
}
