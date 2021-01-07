package ro.mta.se.lab.controller;

import java.util.concurrent.*;

public class TitanThread extends Thread {

    private Runnable runnable;

    public TitanThread(Runnable runnableObject) {
        runnable = runnableObject;
    }

    public static synchronized void runNewThread(Runnable runnableObject) {
        TitanThread newThread = new TitanThread(runnableObject);
        newThread.start();
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(runnable);
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch(TimeoutException ex) {
            future.cancel(true);
        } catch (Exception e) {
            executor.shutdownNow();
        } finally {
            executor.shutdownNow();
        }
    }
}
