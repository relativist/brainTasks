package tasks.two;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

@Slf4j
public class BigIntergers {
    private static final int SIZE = 30_000_000;

    private static final int[] volumeOne = new int[SIZE];
    private static final int[] volumeTwo = new int[SIZE];

    static {
        fill(volumeOne);
        fill(volumeTwo);
    }

    private static void fill(int[] volume) {
        for (int i = 0; i < volume.length; i++) {
            volume[i] = i;
        }
    }

    public static void main(String[] args) {
        customWay();
        shortWay();
    }

    private static void customWay() {
        long start = System.currentTimeMillis();
        int[] all = new int[volumeOne.length + volumeTwo.length];

        int curPositionOne = 0;
        int curPositionTwo = 0;

        for (int i = 0; i < all.length; i++) {

            int v1, v2;
            if (curPositionOne >= volumeOne.length) {
                all[i] = volumeTwo[curPositionTwo];
                curPositionTwo++;
                continue;
            } else {
                v1 = volumeOne[curPositionOne];
            }

            if (curPositionTwo >= volumeTwo.length) {
                all[i] = volumeOne[curPositionOne];
                curPositionOne++;
                continue;
            } else {
                v2 = volumeTwo[curPositionTwo];
            }

            if (v1 <= v2) {
                all[i] = v1;
                curPositionOne++;
            } else {
                all[i] = v2;
                curPositionTwo++;
            }
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("custom way: {} ms", duration);
    }

    private static void shortWay() {
        long start = System.currentTimeMillis();
        int[] all = ArrayUtils.addAll(volumeOne, volumeTwo);
        Arrays.sort(all);
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("short way: {} ms", duration);
    }


    // todo полагаю есть способ быстрее чем customWay.
}
