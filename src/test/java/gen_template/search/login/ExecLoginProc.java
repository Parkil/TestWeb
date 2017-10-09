package gen_template.search.login;

import org.openqa.selenium.WebDriver;

public interface ExecLoginProc {
	
	public void exec_login(WebDriver driver, Object... params) throws Exception;
	
	public boolean is_logout_btn(WebDriver driver, Object... params) throws Exception;
}
