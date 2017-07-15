package gen_template.util;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;

import org.junit.Assert;

/*
 * jcodemodel api를 이용하여 동적으로 자바코드를 생성하는 예제`
 */
public class GenerateCodeUtil {
	
	public void genTemplateCode(Map<String,String> gen_data_map) throws Exception{
		JCodeModel model = new JCodeModel();
		
		//기본 클래스 생성
		JPackage jp = model._package("gen_template");
		JDefinedClass jc = jp._class(gen_data_map.get("class_nm")); //파일명
		jc.javadoc().add("Generated Class.");
		
		//jc._extends(LoadableComponent.class); //extends LoadableComponent
		JClass jClassExtends = model.ref(LoadableComponent.class).narrow(jc);
		jc._extends(jClassExtends); //extends LoadableComponent<Test123>
		
		JFieldVar driver = jc.field(JMod.PRIVATE, WebDriver.class, "driver");
		jc.field(JMod.PRIVATE, String.class, "url", JExpr.lit(gen_data_map.get("url")));
		
		int idx = 1;
		String xpath = null;
		
		while((xpath = gen_data_map.get("xpath"+idx)) != null) {
			JFieldVar id = jc.field(JMod.PRIVATE, WebElement.class, "btn"+idx);
			id.annotate(FindBy.class).param("xpath", xpath);
			++idx;
		}
		
		/*
		 * 생성자 생성 부분
		 * 
		 * System.out.println("hello")를 호출하는 방법
		 * model.ref(System.class).staticRef("out").invoke("println").arg(JExpr.lit("hello"))
		 */
		JMethod constructor = jc.constructor(JMod.PUBLIC);
		constructor.param(WebDriver.class, "driver");
		constructor.body().assign(JExpr._this().ref(driver.name()), JExpr.ref(driver.name()));
		constructor.body().add(model.ref(PageFactory.class).staticInvoke("initElements").arg(
					JExpr.ref("driver")
				).arg(
					JExpr._this()
				)
				);
		
		JMethod is_loaded = jc.method(JMod.PROTECTED, model.VOID, "isLoaded");
		is_loaded._throws(Error.class);
		is_loaded.annotate(Override.class);
		is_loaded.body().directStatement("driver.get(url);"); //문자열을 이용해서 메소드를 호출하는 방법
		
		JMethod load = jc.method(JMod.PROTECTED, model.VOID, "load");
		load.annotate(Override.class);
		load.body().add(model.ref(Assert.class).staticInvoke("assertEquals").arg(
					"Insert Title Here"
				).arg(
					driver.invoke("getTitle")
				));
		
		JMethod exec_stub = jc.method(JMod.PUBLIC, model.VOID, "exec");
		exec_stub.varParam(Object.class, "params"); //Object... 같은 동적개수 파라메터정의
		exec_stub.body().directStatement("//insert detail test code here");
		
		model.build(new File("src/test/java"));
	}
}
