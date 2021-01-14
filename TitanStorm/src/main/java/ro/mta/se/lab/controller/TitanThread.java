package ro.mta.se.lab.controller;

import java.util.concurrent.*;

/**
 * Class used to handle events asynchronous, it provides a static method that serves as an
 * interface provided a Runnable object
 */
public class TitanThread extends Thread {

    /**
     * Current thread pool and runnable to be executed
     */
    private Runnable runnable;
    private ExecutorService executorService;

    /**
     * Simple constructor for the generated objects
     *
     * @param runnableObject the runnable object to run inside the thread
     */
    private TitanThread(Runnable runnableObject) {
        runnable = runnableObject;
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Given the runnable object, it will run it inside a new thread, and will
     * make sure that the execution will stop after 3 seconds, even if the thread will
     * fail or run in an infinite loop.
     *
     * @param runnableObject the runnable object to run inside the new thread
     */
    public static synchronized void runNewThread(Runnable runnableObject) {
        TitanThread newThread = new TitanThread(runnableObject);
        newThread.start();
    }

    /**
     * This will create a new thread pool with one thread, submit the runnable object
     * and wait for it to close or force the shutdown after 3 seconds.
     */
    @Override
    public void run() {
        Future future = executorService.submit(runnable);
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException ex) {
            future.cancel(true);
        } catch (Exception e) {
            executorService.shutdownNow();
        } finally {
            executorService.shutdownNow();
        }
    }
}
