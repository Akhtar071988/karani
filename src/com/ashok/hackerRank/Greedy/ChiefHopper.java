package com.ashok.hackerRank.Greedy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * problem: Chief Hopper
 * https://www.hackerrank.com/challenges/chief-hopper
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */

public class ChiefHopper {

    private static PrintWriter out;
    private static InputStream in;

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = System.out;
        in = System.in;
        out = new PrintWriter(outputStream);

        solve();
        out.close();
    }

    private static void solve() throws IOException {
        InputReader in = new InputReader();
        int n = in.readInt();
        int[] height = in.readIntArray(n);
        long max = 0;

        if (n == 1) {
            out.println((height[0] + 1) >>> 1);
            return;
        }

        for (int i = 0; i < n; i++)
            max = Math.max(max, height[i]);

        long start = 0, mid = max, end = max;
        while (start != mid) {
            if (possible(mid, height, max))
                end = mid;
            else
                start = mid;

            mid = (end + start) >>> 1;
        }

        if (possible(mid, height, max))
            out.println(mid);
        else
            out.println(end);
    }

    private static boolean possible(long energy, int[] height, long max) {
        for (int i = 0; i < height.length && energy < max; i++) {
            energy += energy - height[i];
            if (energy < 0)
                return false;
        }

        return energy >= 0;
    }

    final static class InputReader {
        byte[] buffer = new byte[8192];
        int offset = 0;
        int bufferSize = 0;

        public int readInt() throws IOException {
            int number = 0;
            int s = 1;
            if (offset == bufferSize) {
                offset = 0;
                bufferSize = in.read(buffer);
            }
            if (bufferSize == -1)
                throw new IOException("No new bytes");
            for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
                if (buffer[offset] == '-')
                    s = -1;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
                number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
                if (offset == bufferSize - 1) {
                    offset = -1;
                    bufferSize = in.read(buffer);
                }
            }
            ++offset;
            return number * s;
        }

        public int[] readIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = readInt();

            return ar;
        }
    }
}
