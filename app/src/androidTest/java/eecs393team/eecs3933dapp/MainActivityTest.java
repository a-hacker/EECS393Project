package eecs393team.eecs3933dapp;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

/**
 * Created by austin on 10/24/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mMainActivity;
    Button create_Button;
    Button edit_Button;
    Button print_Button;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        create_Button = (Button) getActivity().findViewById(R.id.create_button);
        edit_Button = (Button) getActivity().findViewById(R.id.edit_button);
        print_Button = (Button) getActivity().findViewById(R.id.print_button);
    }

    public void testPreconditions() {
        System.out.println("Testing Preconditions...");
        assertNotNull("mMainActivity is null", mMainActivity);
        assertEquals("Activity is of wrong type",
                MainActivity.class, mMainActivity.getClass());
        System.out.println("Done.");
    }

    @MediumTest
    public void testLaunchCreate(){
        System.out.println("Starting Create test...");
        Instrumentation.ActivityMonitor createMainMonitor =
                getInstrumentation().addMonitor(CreateMain.class.getName(),
                        null, false);
        TouchUtils.clickView(this, create_Button);
        CreateMain createMainActivity = (CreateMain)
                createMainMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("CreateMain is null", createMainActivity);
        assertEquals("Monitor for MainActivity has not been called", 1, createMainMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CreateMain.class, createMainActivity.getClass());
        getInstrumentation().removeMonitor(createMainMonitor);
        System.out.println("Finished Create test.");
        createMainActivity.finish();
    }

    @MediumTest
    public void testLaunchEdit(){
        Instrumentation.ActivityMonitor editMainMonitor =
                getInstrumentation().addMonitor(EditMain.class.getName(),
                        null, false);
        TouchUtils.clickView(this, edit_Button);
        EditMain editMainActivity = (EditMain)
                editMainMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("EditMain is null", editMainActivity);
        assertEquals("Monitor for EditMain has not been called", 1, editMainMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditMain.class, editMainActivity.getClass());
        getInstrumentation().removeMonitor(editMainMonitor);
        editMainActivity.finish();
    }

    @MediumTest
    public void testLaunchPrint(){
        Instrumentation.ActivityMonitor printMainMonitor =
                getInstrumentation().addMonitor(PrintMain.class.getName(),
                        null, false);

        TouchUtils.clickView(this, print_Button);
        PrintMain printMainActivity = (PrintMain)
                printMainMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("PrintMain is null", printMainActivity);
        assertEquals("Monitor for ProintMain has not been called", 1, printMainMonitor.getHits());
        assertEquals("Activity is of wrong type",
                PrintMain.class, printMainActivity.getClass());
        getInstrumentation().removeMonitor(printMainMonitor);
        printMainActivity.finish();
    }

    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }
}
