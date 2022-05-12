package com.example.githubapi.ui

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubapi.R
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun activity_assert_not_null() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_is_resumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activity_fragment_container_not_null() {
        scenario.onActivity {
            TestCase.assertNotNull(it.findViewById(R.id.main_container))
        }
    }

    @Test
    fun activity_displayed_users_fragment() {
        onView(withId(R.id.fragment_users)).check(matches(isDisplayed()))
    }

    @Test
    fun activity_navigate_to_repos_fragment() {
        onView(withId(R.id.users_fragment_recycler)).perform(
            RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(1)
        )
            .perform(click())
        onView(withId(R.id.fragment_users)).check(matches(isDisplayed()))
    }
}