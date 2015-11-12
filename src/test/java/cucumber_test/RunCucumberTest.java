package cucumber_test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/*
 * cucumber jvm을 이용한 행위주도테스트(BDD)
 * 
 * 필요항목
 * 1.테스트 행위를 정의한 feature 파일
 * 2.1번에서 정의한 테스트를 수행할 수행클래스
 * 3.실제로 cucumber테스트를 수행할 실행클래스
 * 
 * CucumberOptions에서 feature위치및 기타사항을 정해줄수도 있으나 기본경로는
 * a. src/test/resources/[사용자지정경로]/feature파일
 * b. src/test/java/[사용자지정경로]/1,2번 파일
 * 
 * 위와 같으며 사용자 지정경로는 a,b가 동일해야 한다.
 * 주의할점은 b의 패키지경로를 cucumber로 지정하게 되면 테스트 수행시 java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserException 오류가 발생한다.
 * (전체경로뿐만 아니라 일부경로로 지정해도 동일한 현상이 발생하는 듯 함)
 * 해당 오류는 실제 클래스 파일이 없어서 생기는 오류가 아니기 때문에 .jar파일을 추가해도 오류가 해결되지 않는다 주의할것
 * 
 * 테스트 실행시 gherkin.lexer.LexingError: Lexing error on line ~ 오류가 발생할 경우
 * 개행문자나 기타 특수문자때문일수 있으니 해당라인을 지우고 다시 입력할것 복사-붙여넣기를 하면 계속 오류가 발생하는 경우가 있다.
 * 
 * feature파일 작성시 주의점
 * Feature,Scenario,When/Then/And 작성시
 * [Scenario: ] 와 같이 : 을 같이 붙여 써야 한다.
 * [Scenario : ]와 같이 쓰면 인식을 못하니 주의할것
 * 
 * Cucumber 테스트시에는 Junit @BeforeClass,@AfterClass,@Before,@After 가 작동하지 않는다 주의할것
 * 
 * 책에서는 maven > maven test를 이용해서 테스트를 실행하라고 나오지만 Run as Junit으로 실행해도 결과가 표시된다.
 * - Maven test로 실행하면 오류가 발생함
 * 
 * feature에서 지정하고 테스트케이스를 만들지 않을경우 console로 메소드구조를 표시해준다.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber_test.html"})
public class RunCucumberTest {
	
}
