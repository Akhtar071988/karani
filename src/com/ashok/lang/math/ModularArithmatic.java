package com.ashok.lang.math;

import java.util.LinkedList;

/**
 * This class is created to implement Modular Arithmatic functions.
 * Although some functions are already implemented in {@link BinaryGCD}
 * but that class was primarily designed to implement Binary Method of
 * GCD calculation. This class will implement all the modular arithmatic
 * functions like euclid's gcd algorithm, euclid's extended gcd algorithm,
 * inverse modulo calculation to name a few.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class ModularArithmatic {
    private ModularArithmatic() {
        super();
    }

    public static int gcd(int a, int b) {
        return euclid(a, b);
    }

    public static int gcd(int a, long b) {
        return euclid(a, (int) (b % a));
    }

    public static int gcd(long a, int b) {
        return euclid((int) (a % b), b);
    }

    public static long gcd(long a, long b) {
        if (a == 0) return b;
        if (b == 0) return a;
        if (a < Integer.MAX_VALUE)
            return euclid((int) a, (int) (b % a));

        if (b < Integer.MAX_VALUE)
            return euclid((int) b, (int) (a % b));

        return bgcd(a, b);
    }

    public static int[] extendedEuclid(int a, int b) {
        int[] result = new int[3];
        xEuclid(a, b, result);
        return result;
    }

    public static long[] extendedEuclid(long a, long b) {
        long[] result = new long[3];
        xEuclid(a, b, result);
        return result;
    }

    /**
     * This function returns the modular inverse of number a with modulo mod.
     * It uses basically Extended Euclid Algorithm. This method doesn't return
     * inverse Modulo, it actually returns the number to which if multiplied
     * and taken modulo mod equals to GCD of a and mod. So if a and mod are
     * coprime then it is inverse modulo other wise this method should return
     * 0 (zero).
     *
     * @param a
     * @param mod
     * @return
     */
    public static long inverseModulo(long a, long mod) {
        a %= mod;
        if (a == 1 || a == mod - 1) return a;

        if (gcd(a, mod) > 1)
            return 0;

        return (mod + extendedEuclid(a, mod)[1]) % mod;
    }

    /**
     * Extended Euclid's algorithm. This method is exact implementation of
     * the algorithm explained in CLRS.
     * For further read refer Alan Baker's A Comprehensive Course in Number
     * Theory, Chapter 1.
     * <br>
     * let's say we want to calculate integer values for x and y for equation
     * <p>
     * ax + by = gcd(a,b)
     * <p>
     * and we know the solution to the following equation
     * <p>
     * bx + (a%b)y = gcd(a,b) which are x1 and y1.
     * <p>
     * It is obvious that x = y1 and y = x1 - (a/b)y1. Proof is left as an exercise.
     *
     * @param a
     * @param b
     * @param res equation gcd(a,b) = a*x + b*y where x is stored at 1st and y is stored at 2nd position.
     */
    private static void xEuclid(long a, long b, long[] res) {
        if (b == 0) {
            res[0] = a;
            res[1] = 1;
            res[2] = 0;
            return;
        }

        xEuclid(b, a % b, res);
        long x = res[1], y = res[2];
        res[1] = y;
        res[2] = x - y * (a / b);
    }

    private static void xEuclid(int a, int b, int[] res) {
        if (b == 0) {
            res[0] = a;
            res[1] = 1;
            res[2] = 0;
            return;
        }

        xEuclid(b, a % b, res);
        int x = res[1], y = res[2];
        res[1] = y;
        res[2] = x - y * (a / b);
    }

    private static long bgcd(long a, long b) {
        if (a == 0)
            return b;

        if (b == 0)
            return a;

        if (a == 1 || b == 1)
            return 1;

        int res = 0;
        int x = Long.numberOfTrailingZeros(a);
        int y = Long.numberOfTrailingZeros(b);
        res = x > y ? y : x;
        a = a >>> x;
        b = b >>> y;

        while (a != b && a != 1) {
            if (a > b) {
                a -= b;
                a = a >>> Long.numberOfTrailingZeros(a);
            } else {
                b -= a;
                b = b >>> Long.numberOfTrailingZeros(b);
            }
        }
        return a << res;
    }

    /**
     * Euclid's Greatest Common Divisor algorithm implementation.
     * For more information refer Wikipedia and Alan Baker's Number Theory.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static int euclid(int a, int b) {
        if (a == 0)
            return b;
        return euclid(b % a, a);
    }

    /**
     * This method is now used only for inverse modulo calculation.
     *
     * @param a
     * @param b
     * @return Greatest Commond Divisor of a and b
     */
    private static long euclid(long a, long b) {
        if (a == 0)
            return b;
        return euclid(b % a, a);
    }

    /**
     * This method calculates count of coprime numbers smaller than a.
     * For more info please visit wikipedia article or refer
     * A Comprehensive Course in Number Theory by Alan Baker.
     *
     * @param a number for which coprime count is to be calculated.
     * @return number of coprimes smaller than a.
     */
    public static int totient(int a) {
        LinkedList<Integer> factors = Prime.primeFactors(a);
        int res = a;

        for (Integer e : factors) {
            res = (res / e) * (e - 1);
        }

        return res;
    }

    /**
     * Returns the array of coprime numbers for numbers 0 to n.
     * Note that the array size is n + 1, so for number n, number
     * of coprimes smaller than n will be phi[n].
     *
     * @param n number range for totient values to be calculated.
     * @return array of integer with totient values.
     */
    public static int[] totientList(int n) {
        boolean[] primes = new boolean[n + 1];
        for (int i = 1; i <= n; i++)
            primes[i] = true;

        int[] phi = new int[n + 1];
        phi[1] = 1;

        for (int i = 2; i <= n; i++)
            phi[i] = i;

        for (int i = 2; i <= n; i++) {
            if (!primes[i])
                continue;

            int num = i - 1;

            for (int j = i; j <= n; j += i) {
                primes[j] = false;

                phi[j] = num * (phi[j] / i);
            }
        }

        return phi;
    }

    public static long chineseRemainderTheorem(int[] remainders, int[] modulos) {
        if (remainders.length != modulos.length)
            throw new RuntimeException("Input size mismatch. Remainders and modulos count should be equal");

        if (!checkCoprimes(modulos))
            throw new RuntimeException("numbers should be coprime");

        long res = 0, multi = Numbers.multiply(modulos);
        for (int i = 0; i < modulos.length; i++) {
            long mi = getMj(modulos, i);

            long x = remainders[i] * inverseModulo(mi, modulos[i]);
            res += mi * x;
            res %= multi;
        }

        return res;
    }

    private static long getMj(int[] modulos, int index) {
        long res = 1;
        int mod = modulos[index];

        for (int e : modulos)
            if (e != mod)
                res = (res * e);

        return res;
    }

    public static boolean checkCoprimes(int a, int b) {
        return euclid(a, b) == 1;
    }

    public static boolean checkCoprimes(long a, long b) {
        return gcd(a, b) == 1;
    }

    public static boolean checkCoprimes(int[] numbers) {
        for (int i = 0; i < numbers.length; i++)
            for (int j = i + 1; j < numbers.length; j++)
                if (gcd(numbers[i], numbers[j]) != 1)
                    return false;

        return true;
    }

    /**
     * Returns the square root of n modulo p, which is r in the following equation:
     * r * r congruent to n (modulo p), where p is prime.
     *
     * @param n
     * @param p
     * @return
     */
    private static long getSquareRoot(long n, int p) {
        int mod8 = p & 7;
        if (mod8 == 3 || mod8 == 7)
            return Power.pow(n, (p + 1) >>> 2, p);

        if (mod8 == 5) {
            long v = Power.pow(n << 1, (p - 5) >>> 3, p);
            long i = 2 * n * (v * v % p) % p;
            return (i - 1) * (n * v % p) % p;
        }

        return shankMethod(n, p);
    }

    /**
     * Shank's method to find square root of n modulo p. Read wikipedia or any other source.
     *
     * @param n
     * @param p prime number
     * @return
     */
    private static long shankMethod(long n, int p) {
        int e = Integer.numberOfTrailingZeros((p - 1)), q = (p - 1) >>> e;
        long x = 2;
        long z;
        while (true) {
            z = Power.pow(x, q, p);
            if (Power.pow(z, 1 << (e - 1), p) != 1)
                break;

            x++;
        }

        long y = z, r = e;
        x = Power.pow(n, (q - 1) >>> 1, p); // (q - 1) / 2 is equivalent to q / 2 as q is odd.
        long v = n * x % p, w = v * x % p;

        while (w != 1) {
            int k = findK(w, p);
            long d = Power.pow(y, Power.pow(2, r - k - 1, p - 1), p);
            y = d * d % p;
            r = k;
            v = d * v % p;
            w = w * y % p;
        }

        return v;
    }

    private static int findK(long w, long p) {
        int k = 1;
        long res = w * w % p;

        while (res != 1) {
            res = res * res % p;
            k++;
        }

        return k;
    }

    /**
     * Returns kth root for n modulo mod.
     *
     * @param n
     * @param mod
     * @param k
     * @return
     */
    public static long moduloRoot(int n, int k, int mod) {
        int phiMod = totient(mod), phiPhiMod = totient(phiMod);
        long power = inverseModulo(k, phiMod);
        return Power.pow(n, power, mod);
    }
}
