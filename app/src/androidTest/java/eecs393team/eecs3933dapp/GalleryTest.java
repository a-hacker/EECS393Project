package eecs393team.eecs3933dapp;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * Created by austin on 11/12/15.
 */
public class GalleryTest extends ActivityInstrumentationTestCase2<Gallery> {

    private Gallery mGal;

    public GalleryTest() {
        super(Gallery.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mGal = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mGal is null", mGal);
        assertEquals("Activity is of wrong type",
                Gallery.class, mGal.getClass());
    }

    public void testSelectSTL(){
        for (String fileName: mGal.buttons.keySet()) {
            if (fileName.endsWith(".STL")){
                assertLoad(mGal, mGal.buttons.get(fileName));
            }
        }
    }

    public void testSelectNonSTL(){
        for (String fileName: mGal.buttons.keySet()) {
            if (!fileName.endsWith(".STL")){
                //should not be possible
                assertTrue(false);
            }
        }
    }

    public void assertLoad(Gallery mGal, Button file){
        Instrumentation.ActivityMonitor stlViewMonitor =
                getInstrumentation().addMonitor(STLViewActivity.class.getName(),
                        null, false);
        TouchUtils.clickView(this, file);
        STLViewActivity stlActivity = (STLViewActivity)
                stlViewMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("STLView is null", stlActivity);
        assertEquals("Monitor for stlActivity with, " + file.getText() + " has not been called", 1, stlViewMonitor.getHits());
        assertEquals("Activity is of wrong type",
                STLViewActivity.class, stlActivity.getClass());
        getInstrumentation().removeMonitor(stlViewMonitor);
        stlActivity.finish();
    }

}
