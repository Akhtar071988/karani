package com.ashok.friends.harsh;

import com.ashok.lang.dsa.RandomStrings;
import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * This class is to solve Harshvardhan's problems (programming only ;) )
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Harsh {
    private static Output out;
    private static InputReader in;
    private static final String path =
            "D:\\GitHub\\Language\\Language\\Common\\src\\Code\\Main\\";

    public static void main(String[] args) throws IOException {
//        in = new InputReader(path + "input.txt");
//        out = new Output(path + "output.txt");
        in = new InputReader();
        out = new Output();

        testMultipleThreadOneByOne(in.readInt());
        Harsh a = new Harsh();
        a.solve();
        out.close();
    }

    private void solve() throws IOException {
        while (true) {
            int size = in.readInt();

            ArrayList<ArrayListObject> list = getList(size);
            out.println(list);

            Collections.sort(list, new Comparator<ArrayListObject>() {
                @Override
                public int compare(ArrayListObject o1, ArrayListObject o2) {
                    return o1.getStatus().compareToIgnoreCase(o2.getStatus());
                }
            });

            out.println(list);
            out.flush();
        }
    }

    final static class ArrayListObject {
        private String status;

        ArrayListObject(String st) {
            status = st;
        }

        public String getStatus() {
            return status;
        }

        public String toString() {
            return status;
        }
    }

    private static ArrayList<ArrayListObject> getList(int size) {
        RandomStrings random = new RandomStrings();
        Random rand = new Random();
        ArrayList<ArrayListObject> list = new ArrayList<>();

        for (int i = 0; i < size; i++)
            list.add(new ArrayListObject(random.nextStringAaBb(rand.nextInt(5) + 5)));

        return list;
    }

    private static boolean pattern(String a, String b) {
        if (a.length() > b.length())
            return false;

        int[] map = new int[256], copy = new int[256];

        for (int i = 0; i < a.length(); i++)
            map[a.charAt(i)]++;

        for (int j = 0; j < b.length(); j++)
            copy[b.charAt(j)]++;

        for (int i = 0; i < a.length(); i++)
            if (map[a.charAt(i)] > copy[a.charAt(i)])
                return false;

        return true;
    }

    private static int days(int x, int y, int n) {
        if (x >= n)
            return 1;

        int velocity = x - y;
        int target = n - y - 1;

        return 1 + target / velocity;
    }

    private static String process(String s) {
        StringBuilder sb = new StringBuilder(s.length());

        int comment = s.indexOf("//");

        for (int i = 0; i < comment; i++) {
            if (s.charAt(i) == '+')
                sb.append('*');
            else
                sb.append(s.charAt(i));
        }

        int i = Math.max(0, comment);
        for (; i < s.length(); i++)
            sb.append(s.charAt(i));

        return sb.toString();
    }

    private static void testMultipleThreadOneByOne(int n) {
        final NumberPrinter odd = new NumberPrinter(n, 1), even = new NumberPrinter(n, 2);
        final Semaphore oddLock = new Semaphore(1), evenLock = new Semaphore(0);
        Thread oddThread = new Thread(() -> {
            while (odd.current <= n) {
                try {
                    oddLock.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                odd.run();
                evenLock.release();
            }
        });

        Thread evenThread = new Thread(() -> {
            while (even.current <= n) {
                try {
                    evenLock.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                even.run();
                oddLock.release();
            }
        });

        oddThread.setName("odd");
        evenThread.setName("even");

        oddThread.start();
        evenThread.start();
    }

    final static class NumberPrinter implements Runnable {
        final int end;
        private int current;

        NumberPrinter(int e, int c) {
            this.end = e;
            current = c;
        }

        @Override
        public void run() {
            out.println(Thread.currentThread().getName() + ": " + current);
            out.flush();
            current += 2;
        }
    }
}
