package com.tascape.qa.th.comm;

import com.android.uiautomator.stub.IUiDevice;
import com.android.uiautomator.stub.IUiObject;
import com.android.uiautomator.stub.Point;
import com.android.uiautomator.stub.UiSelector;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author linsong wang
 */
public class UiAuotmatorRmiDemoTests {
    private static final Logger LOG = LoggerFactory.getLogger(UiAuotmatorRmiDemoTests.class);

    private static AndroidUiAutomatorDevice uiad;

    static {
        try {
            uiad = new AndroidUiAutomatorDevice(8998);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private final IUiDevice uiDeviceStub = uiad.getUiDeviceStub();

    private final IUiObject uiObjectStub = uiad.getUiObjectStub();

    public void testDemo1() throws Exception {
        uiDeviceStub.pressHome();
        uiDeviceStub.waitForIdle();
        uiDeviceStub.click(500, 500);

        LOG.debug(uiDeviceStub.getDisplayWidth() + "/" + uiDeviceStub.getDisplayHeight());
        Point p = uiDeviceStub.getDisplaySizeDp();
        LOG.debug(p.x + "/" + p.y);
        uiDeviceStub.swipe(100, 0, 100, 500, 2);
        LOG.debug(uiDeviceStub.getCurrentActivityName());

        uiDeviceStub.swipe(new Point[]{new Point(100, 500), new Point(100, 0)}, 2);
        uiDeviceStub.swipe(100, 500, 100, 0, 2);
        LOG.debug(uiDeviceStub.getCurrentActivityName());
    }

    public void testDemo2() throws Exception {
        while (true) {
            uiDeviceStub.pressHome();
            uiDeviceStub.waitForIdle();
            uiDeviceStub.pressMenu();
            uiDeviceStub.pressHome();
            uiDeviceStub.waitForIdle();
            uiDeviceStub.pressRecentApps();
            uiDeviceStub.pressHome();
            uiDeviceStub.waitForIdle();
            UiSelector selector = new UiSelector().text("Apps");
            uiObjectStub.useSelector(selector);
            uiObjectStub.click();
        }
    }

    public static void main(String[] args) throws Exception {
        UiAuotmatorRmiDemoTests t = new UiAuotmatorRmiDemoTests();
        t.testDemo1();
        t.testDemo2();
    }
}
