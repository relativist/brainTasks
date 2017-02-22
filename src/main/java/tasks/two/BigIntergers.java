package tasks.two;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

@Slf4j
public class BigIntergers {
    private static final int SIZE = 30_000_000;

    private static void fill(int[] volume) {
        for (int i = 0; i < volume.length; i++) {
            volume[i] = i;
        }
    }

    public static void main(String[] args) {
        int[] volumeOne = new int[SIZE];
        int[] volumeTwo = new int[SIZE];
        fill(volumeOne);
        fill(volumeTwo);

        customWay(volumeOne,volumeTwo);
        shortWay(volumeOne,volumeTwo);
    }

    private static int [] customWay(int[] volumeOne, int[] volumeTwo) {
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
        return all;
    }

    private static int [] shortWay(int[] volumeOne, int[] volumeTwo) {
        long start = System.currentTimeMillis();
        int[] all = ArrayUtils.addAll(volumeOne, volumeTwo);
        Arrays.sort(all);
        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("short way: {} ms", duration);
        return all;
    }


    // todo полагаю есть способ быстрее чем customWay.
}
