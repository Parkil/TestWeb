package jbehave_test;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.openqa.selenium.WebDriver;

import util.Util;

/*
 * jbehave를 이용한 테스트
 * cucumber jvm과 세팅은 거의 유사하나 몇가지 주의점이 있다.
 * 여기서 구현한 JBehaveTest 클래스를 Junit test case에서 상속을 받아야 jbehave가 정상적으로 작동된다.
 * 
 */
public abstract class JBehaveTest extends JUnitStory {
	protected final static WebDriver driver = Util.getFireFoxDriver();
	
	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader()))
				.useStoryReporterBuilder(new StoryReporterBuilder()
				.withDefaultFormats()
				.withFormats(Format.HTML, Format.CONSOLE)
				.withRelativeDirectory("jbehave-report"));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List candidateSteps() {
		return new InstanceStepsFactory(configuration(), this).createCandidateSteps();
	}
}
