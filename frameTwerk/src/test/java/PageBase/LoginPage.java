package PageBase;

import framework.SeleniumClass;
import org.openqa.selenium.By;

/**
 * Created by sterlingg on 11/24/2015.
 */
public class LoginPage{
    // public string can be used outside of LoginPage
    public String cat = "cat";
    // private By element can't be used by anything but this class
    private final By userName = By.id("blah");
    // private String with 'constant vaible'
    private final String BLAH = "something";

    public void login() {
        SeleniumClass.open("http://google.com");
        SeleniumClass.clearDataThenType("sterlingg@gmail.com", userName);
    }
}
