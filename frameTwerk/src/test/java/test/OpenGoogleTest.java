package test;

import PageBase.LoginPage;
import org.testng.annotations.Test;

/**
 * Created by sterlingg on 11/24/2015.
 */
public class OpenGoogleTest {
    String googleNewsURL;



    @Test
    public void openGoogler() {
        LoginPage login = new LoginPage();
        login.login();
    }
}
