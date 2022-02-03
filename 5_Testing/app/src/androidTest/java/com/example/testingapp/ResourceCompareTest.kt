package com.example.testingapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test


class ResourceCompareTest{

    /**
     * the below is bad practice because we will be using the same instance of a class for testing hence making our tests dependent and flaky so rather than
     * doing this we initialize it in each function separately but when we have a lot of test cases that takes a lot of system resources and hence is bad.
     *
     * So rather than doing that we use a different function for initialization of variables and add an annotation @Before which will call this function
     * before every test case.
     */
//    private val resourceCompare=ResourceCompare()


    private lateinit var resourceCompare:ResourceCompare

    @Before
    fun setup(){
        resourceCompare= ResourceCompare()
    }

    @After
    fun tearDown(){
        // this function is to destroy variables or close databases.
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue(){
        val context=ApplicationProvider.getApplicationContext<Context>()
        val result=resourceCompare.isEqual(context,R.string.app_name,"TestingApp")
        assertThat(result).isTrue()
    }
    @Test
    fun stringResourceSameAsGivenString_returnsFalse(){
        val context=ApplicationProvider.getApplicationContext<Context>()
        val result=resourceCompare.isEqual(context,R.string.app_name,"Testing")
        assertThat(result).isFalse()
    }
}