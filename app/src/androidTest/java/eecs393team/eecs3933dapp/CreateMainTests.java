package eecs393team.eecs3933dapp;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by austin on 10/24/15.
 */
public class CreateMainTests extends ActivityInstrumentationTestCase2<CreateMain> {

    CreateMain mCreateMain;
    Button launchNextButton;
    ImageView connecting_graphic;

    public CreateMainTests() {
        super(CreateMain.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCreateMain= getActivity();
        launchNextButton = (Button) getActivity().findViewById(R.id.button);
        connecting_graphic = (ImageView) getActivity().findViewById(R.id.imageView);
    }

    public void testPreconditions() {
        System.out.println("Testing Preconditions...");
        assertNotNull("mCreateMain is null", mCreateMain);
        assertEquals("Activity is of wrong type",
                CreateMain.class, mCreateMain.getClass());
        System.out.println("Done.");
    }

    @MediumTest
    public void testConnectionFailed(){
        mCreateMain.setServer("localhost"); //localhost shouldn't be server
        mCreateMain.connectToServer();
        assertEquals("Back", launchNextButton.getText());
        assertEquals(R.drawable.red_x, connecting_graphic.getDrawable());
        Instrumentation.ActivityMonitor mainMonitor =
                getInstrumentation().addMonitor(MainActivity.class.getName(),
                        null, false);
        TouchUtils.clickView(this, launchNextButton);
        MainActivity mainActivity = (MainActivity)
                mainMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("CreateMain is null", mainActivity);
        assertEquals("Monitor for MainActivity has not been called", 1, mainMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CreateMain.class, mainActivity.getClass());
        getInstrumentation().removeMonitor(mainMonitor);
        mainActivity.finish();
    }

    @MediumTest
    public void testConnectionSuccessful(){
        assertTrue(false);
    }
}
