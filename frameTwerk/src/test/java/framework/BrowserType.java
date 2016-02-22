package framework;

/**
 * Created by sterlingg on 11/25/2015.
 */
public enum BrowserType {
    ANDROID("android"),
    IOS("ios"),
    FIREFOX("firefox"),
    CHROME("chrome"),
    IE("ie"),
    SAFARI("safari"),
    OPERA("opera"),
    GHOSTDRIVER("ghostdriver"),
    REMOTE("remote"),
    HTMLUNIT("htmlunit");

    private final String browser;

    BrowserType(String browser) {
        this.browser = browser;
    }

    public String getBrowser() {
        return browser;
    }
}
