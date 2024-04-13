package util;

import java.util.concurrent.*;

public class RequestProcessor {
    private static final int THREAD_POOL_SIZE = 10;
    private static final int QUEUE_SIZE = 100;
    private static final int REQUEST_TIMEOUT_SECONDS = 60; // 60s


    private final ExecutorService executorService = new ThreadPoolExecutor(
            THREAD_POOL_SIZE,
            THREAD_POOL_SIZE,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(QUEUE_SIZE)
    );

    public void processRequest(Runnable request) {
        Future<?> future = executorService.submit(request);

        try {
            // Wait for the thread to complete or timeout
            future.get(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        executorService.shutdown();
    }

}
