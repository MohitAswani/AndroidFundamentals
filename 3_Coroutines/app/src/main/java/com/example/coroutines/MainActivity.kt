package com.example.coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity() {

    companion object{
        const val tag="MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView=findViewById<TextView>(R.id.textView)


//        Part 1 : Basics of coroutines
//        // Simplest way to start a coroutine but not the best way to do so
//        // Every coroutine need to be started in a scope based on its lifecycle
//        // GlobalScope means that the coroutine will be alive as long as the app is running
//        GlobalScope.launch {
//            // instructions we want the coroutine to execute
//            // the instructions will be executed asynchronously and inside another thread
//            // below function is a suspend which works similar to sleep function of thread but instead of blocking the thread it just blocks the coroutine
//            delay(3000L)
//            Log.d(tag,"Coroutine is running in thread ${Thread.currentThread().name}")
//        }
//        Log.d(tag,"UI is running in thread ${Thread.currentThread().name}")
//
//        // If the main thread finished or is killed then all other threads and coroutines will also be cancelled


//        Part2 : suspend functions
//        // We can only call a suspend function from within a coroutine or within another function
//        doNetworkCall() this will give error since its a suspend function
//        GlobalScope.launch {
//            val networkCall1=doNetworkCall1()
//            val networkCall2=doNetworkCall2()
//
//            // the coroutine will execute doNetworkCall1 there it encounters delay and it suspends the function and move to next function and it again encounters delay and suspends that function
//            // the delay in networkCall1 will add with delay in networkCall2 since they are launched in the same coroutine
//
//            Log.d(tag,networkCall1)
//            Log.d(tag,networkCall2)
//        }


//        Part 3: Coroutine contexts
//        // Context in coroutines defines in which thread the coroutine will be launched
//        // The below coroutine will launch in main thread with global scope
//        // We can use Dispatchers.Main for UI changes
//        // We can use Dispatchers.IO for doing IO ops
//        // We can use Dispatchers.Default for doing complex calculations
//        // We can use Dispatchers.Unconfined to make the coroutine stay in the function it started in
//        // or new can also start new thread by using newSingleThreadContext(Name of the thread)
//        // The most important thing about coroutine context is that we can switch context from within the coroutine
////        GlobalScope.launch(newSingleThreadContext("My-thread")) {
////            Log.d(tag,"Starting coroutine in thread ${Thread.currentThread().name}")
////        }
//        GlobalScope.launch(Dispatchers.IO) {
//            Log.d(tag,"Starting coroutine in thread ${Thread.currentThread().name}")
//            val answer=doNetworkCall1()
//            // to switch the context we use the below function
//            withContext(Dispatchers.Main){
//                Log.d(tag,"Setting text in thread ${Thread.currentThread().name}")
//                textView.text=answer
//            }
//        }


//        Part 4 : runBlocking
//        // The below function will launch a coroutine in the main thread but it will also block the main thread
//        // If we have a suspend function and want to call it from the main thread and don't want the asynchronous behaviour they we use runBlocking
//        // Another use of runBlocking is to call suspend function in JUnit tests without having to deal with coroutines
//        // Calling the delay function in main thread is also equivalent to putting the main thread to sleep.
//        Log.d(tag,"Before runBlocking")
//        runBlocking {
//            // this lambda has the reference to the coroutine scope hence we can launch more coroutine just by writing launch
//            launch(Dispatchers.IO){
//                // this coroutine will launch asynchronously to the outside coroutine
//                delay(3000L)
//                Log.d(tag,"Finished IO coroutine 1")
//            }
//            launch(Dispatchers.IO){
//                delay(3000L)
//                Log.d(tag,"Finished IO coroutine 2")
//            }
//            // the above code launches two different coroutines independent of each other and hence will not add the delays. Also the IO thread will not be blocked like the main thread.
//            Log.d(tag,"Start of runBlocking")
//            delay(5000L)
//            Log.d(tag,"End of runBlocking")
//        }
//        Log.d(tag,"After run blocking")


//            Part 5: Jobs
//         Whenever we launch a coroutine it returns a job which we can save in a variable
//        val job=GlobalScope.launch(Dispatchers.Default) {
////            repeat(5){
////                Log.d(tag,"Coroutine is still working")
////                delay(1000L)
////            }
//
////            Log.d(tag,"Starting the calculation....")
////            for(i in 30..45){
////                if(isActive)   // this statement will check if the coroutine is active or not
////                {
////                    Log.d(tag,"Result for $i is ${fib(i)}")
////                }
////            }
////            Log.d(tag,"Ending the calculation....")
//
//            // What the below code will do is to end the coroutine automatically if it takes more than 3 seconds and we do not need to cancel this job manually now
//            withTimeout(3000L){
//                Log.d(tag,"Starting the calculation....")
//                for(i in 30..45){
//                    if(isActive)   // this statement will check if the coroutine is active or not
//                    {
//                        Log.d(tag,"Result for $i is ${fib(i)}")
//                    }
//                }
//                Log.d(tag,"Ending the calculation....")
//            }
//        }
//        runBlocking {
////            job.join()   // join function is suspend function which will block the thread it is running in until the coroutine is finished
//            delay(2000L)
////            job.cancel()  // cancel function will cancel the job . So after the coroutine is launched we can cancel the job by using this function
//            // The issue with cancellation is that cancelling a coroutine is not so easy and our coroutine need to be set up to be cancelled properly
//            // in the above case our coroutine is mostly in paused state hence it can be cancelled easily
//            // But the process of cancellation is cooperative and there needs to be enough time to tell the coroutine to cancel
//
//            // So in the case of fibonacci number since the function is not set up to be cancelled it doesn't get cancelled even though job.cancel() is called.
//            // The reason is that our coroutine is so busy calculating that it never checks for cancellation.
//            // And if the function was a suspend function if could pause to check for cancellation but right now it is not.
//            // To prevent this from happening we use isActive in an if statement to cancel it at the right time.
//            // So now it stops the calculation soon after it is cancelled
//            // We usually cancel a job when it takes longer than expected for a task.
//            // For that we can also use withTimeout function like above.
//            Log.d(tag,"Main thread is continuing")
//        }


//        Part 6 : Async and Await
//        // If we have more than one suspend function within the same coroutine they are sequential by default which means that they will be executed one after another
//        // But if we want them to execute them simultaneously or asynchronously we could start different coroutines for different functions.
//        // But that is a very bad practice.
//        // So to solve this issue we use async which is similar launching a job but rather than returning a value it returns a deferred object.
//        // Deferred value is a non-blocking cancellable future — it is a Job with a result. So deferred value is job which returns a result.
//        // And to get the value of a deferred object we use answer1.await() which waits till deferred object is actually calculated.
//        // So we should always use async to launch a coroutine which returns a value.
//        // And we can also use async instead of launch like GlobalScope.async()
//        GlobalScope.launch(Dispatchers.IO) {
//            val time= measureTimeMillis {
//                // If we do this it will take 6 seconds overall for both functions to complete
//                val answer1=doNetworkCall1()
//                val answer2=doNetworkCall2()
//                Log.d(tag,"Answer1 is $answer1")
//                Log.d(tag,"Answer2 is $answer2")
//            }
//            Log.d(tag,"Requests took $time")
//        }
//        // The following is a very bad practice and hence must not be done
//        GlobalScope.launch(Dispatchers.IO) {
//            val time= measureTimeMillis {
//                // If we do this it will take 3 seconds overall for both functions to complete
//                var answer1=""
//                var answer2=""
//                val job1=launch { answer1=doNetworkCall1() }
//                val job2=launch { answer2=doNetworkCall2() }
//                job1.join()
//                job2.join()
//                Log.d(tag,"Answer1 is $answer1")
//                Log.d(tag,"Answer2 is $answer2")
//            }
//            Log.d(tag,"Requests took $time")
//        }
//        GlobalScope.launch(Dispatchers.IO) {
//            val time= measureTimeMillis {
//                // If we do this it will take 3 seconds overall for both functions to complete
//                val answer1= async { doNetworkCall1() }
//                val answer2= async { doNetworkCall2() }
//                Log.d(tag,"Answer1 is ${answer1.await()}")
//                Log.d(tag,"Answer2 is ${answer2.await()}")
//            }
//            Log.d(tag,"Requests took $time")
//        }



//        Part 7 : lifecycleScope and viewModelScope
//        // If we want to launch a coroutine with the lifecycle of a activity or a fragment we use lifecycleScope
//        // If we want to launch a coroutine with the lifecycle of a view model we use viewModelScope
//        textView.setOnClickListener {
//            // Since the below function is launched in global scope it will keep on running even after the MainActivity2 starts
////            GlobalScope.launch {
////                while(true)
////                {
////                    delay(1000L)
////                    Log.d(tag,"Still running")
////                }
////            }
//
//            // This will only keep on running till the activity or fragment is active else it will end
//            lifecycleScope.launch {
//                while(true)
//                {
//                    delay(1000L)
//                    Log.d(tag,"Still running")
//                }
//            }
//            GlobalScope.launch {
//                delay(5000L)
//                Intent(this@MainActivity,MainActivity2::class.java).also {
//                    startActivity(it)
//                    finish()
//                }
//            }
        }
    }

    private fun fib(n:Int):Long{
        return when(n){
            0->0
            1->1
            else-> fib(n-1)+fib(n-2)
        }
    }

    private suspend fun doNetworkCall1():String{
        delay(3000L)
        return "Network call succeeded"
    }
    private suspend fun doNetworkCall2():String{
        delay(3000L)
        return "Network call succeeded"
    }
}