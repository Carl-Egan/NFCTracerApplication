package com.example.nfccontacttracing.activities;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.nfccontacttracing.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class HomePageActivityTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityTestRule = new ActivityTestRule<HomePageActivity>(HomePageActivity.class);

    private HomePageActivity mActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(IrelandStatsActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

    }

    @Test
    public void testLaunchOfStatsOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.stats));
        
      onView(withId(R.id.stats)).perform(click());

      Activity irelandStatsActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
      assertNotNull(irelandStatsActivity);
      irelandStatsActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
    }
}