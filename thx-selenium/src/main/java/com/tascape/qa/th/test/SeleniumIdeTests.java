package com.tascape.qa.th.test;

import com.tascape.qa.th.comm.Firefox;
import com.tascape.qa.th.suite.SeleniumIdeSuite;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.openqa.selenium.server.htmlrunner.HTMLLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author linsong wang
 */
public abstract class SeleniumIdeTests extends AbstractTest {
    private static final Logger LOG = LoggerFactory.getLogger(SeleniumIdeTests.class);

    private final SeleniumServer seleniumServer;

    public SeleniumIdeTests() throws Exception {
        RemoteControlConfiguration rcc = new RemoteControlConfiguration();
        rcc.setTrustAllSSLCertificates(true);
        this.seleniumServer = SeleniumIdeSuite.getSeleniumServer();
    }

    protected boolean runSeleniumIdeFirefox(File html, String browserURL) throws Exception {
        File tempDir = new File(html.getParentFile() + "/temp/");
        tempDir.mkdir();
        File suite = File.createTempFile("selenium-ide-temp-suite-", ".html", tempDir);
        suite.deleteOnExit();
        String content = TEST_SUITE_TEMPLATE.replaceAll("XXXXXXXXXXXXXXXXXXXX", html.getName());
        FileUtils.write(suite, content);
        LOG.debug("Temp test suite file {}", suite.getAbsolutePath());

        File result = this.getTestLogPath().resolve("ide-result.html").toFile();
        LOG.debug("Test result file {}", result.getAbsolutePath());

        this.captureScreens(2000);
        HTMLLauncher launcher = new HTMLLauncher(this.seleniumServer);
        String ff = "*firefox";
        String bin = sysConfig.getProperty(Firefox.SYSPROP_FF_BINARY);
        if (bin != null) {
            ff += " " + bin;
        }
        String pf = launcher.runHTMLSuite(ff, browserURL, suite.toURI().toURL().toString(), result, 36000, true);
        suite.delete();
        return "PASSED".equals(pf);
    }

    private static final String TEST_SUITE_TEMPLATE = new StringBuilder()
            .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            .append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ")
            .append("http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n")
            .append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n")
            .append("<head>\n")
            .append("  <meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\" />\n")
            .append("  <title>Test Suite</title>\n" + "</head>\n" + "<body>\n")
            .append("<table id=\"suiteTable\" cellpadding=\"1\" cellspacing=\"1\" border=\"1\" class=\"selenium\"><tbody>\n")
            .append("<tr><td><b>Test Suite</b></td></tr>\n")
            .append("<tr><td><a href=\"../XXXXXXXXXXXXXXXXXXXX\">XXXXXXXXXXXXXXXXXXXX</a></td></tr>\n")
            .append("</tbody></table>\n" + "</body>\n" + "</html>").toString();

}
