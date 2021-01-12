package ro.mta.se.lab.controller;

import java.util.concurrent.*;

public class TitanThread extends Thread {

    private Runnable runnable;
    private ExecutorService executorService;

    public TitanThread(Runnable runnableObject) {
        runnable = runnableObject;
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized void runNewThread(Runnable runnableObject) {
        TitanThread newThread = new TitanThread(runnableObject);
        newThread.start();
    }

    @Override
    public void run() {
        Future future = executorService.submit(runnable);
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch(TimeoutException ex) {
            future.cancel(true);
        } catch (Exception e) {
            executorService.shutdownNow();
        } finally {
            executorService.shutdownNow();
        }
    }

}
