package tasks.one;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NoStarvationBlockingQueue<E> {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private final static int MAX_SIZE = 2;
    private final Queue<E> queue = new ArrayDeque<>(MAX_SIZE);

    public void add(E e) {
        lock.lock();
        try {
            while (queue.size() == MAX_SIZE) {
                condition.await();
            }
            queue.add(e);
            condition.signal();

        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
    }

    public E poll() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                condition.await();
            }
            E poll = queue.poll();
            condition.signal();
            return poll;
        } catch (InterruptedException e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    public E peek() {
        lock.lock();
        try {
        return queue.peek();
        } finally {
            lock.unlock();
        }
    }
}

// изменения
//1. изменить модификатор методов класса на public и методов. (расширение области видимости)
//2. if (queue.size() == MAX_SIZE) {    На   while (queue.size() == MAX_SIZE) {
// ввиду того что поток может проснуться а условие все еще будет неверным,
// поэтому нужно еще раз проверить, отсюда и while. Потоки сами по себе могут просыпаться (внезапно).
// А у нас еще и общий Condition в двух методах c signal-ом. Поэтому while прям обязательно.
//3. добавить condition.signal(); в методы poll и add. для оповещения потоков, что ресурс освободился.



// тесты в RunQueuesTest классе.