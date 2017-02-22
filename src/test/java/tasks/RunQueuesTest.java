package tasks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tasks.one.NoStarvationBlockingQueue;

@Slf4j
public class RunQueuesTest {

    @Test
    public void testsOne() throws InterruptedException {
        final NoStarvationBlockingQueue<Integer> queue = new NoStarvationBlockingQueue<>();
        final int counter = 10;

        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = "add-" + Thread.currentThread().getName();
                log.info("started: {}", name);

                int repeat = 0;
                while (repeat <= counter/2) {
                    log.info("{} --> {}",name,repeat);
                    queue.add(repeat);
                    repeat++;
                    sleep(1);
                }
            }
        });

        Thread pollThread = new Thread(new Runnable() {
            @Override
            public void run() {

                String name = "poll-" + Thread.currentThread().getName();
                log.info("started: {}", name);

                int repeat = 0;
                while (repeat <= counter) {
                    log.info("{} --> {}",name , queue.poll());
                    repeat++;
                    sleep(10);
                }
            }
        });

        Thread pickThread = new Thread(new Runnable() {
            @Override
            public void run() {

                String name = "pick-" + Thread.currentThread().getName();
                log.info("started: {}", name);

                while (!Thread.currentThread().isInterrupted()) {
//                    log.info("{} --> {}",name , queue.peek());
                    queue.peek();
                    sleep(5);
                }
            }
        });

        Thread addThree = new Thread(addThread);

        addThree.start();
        pickThread.start();
        pollThread.start();
        addThread.start();

        addThread.join();
        pollThread.join();
        addThree.join();
        pickThread.interrupt();

        log.info("current: {}",queue.peek());
    }


    private static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException ignored) {
        }
    }
}

//// TODO: 2/21/17 no asserts needed.
