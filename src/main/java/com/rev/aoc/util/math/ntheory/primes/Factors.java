package com.rev.aoc.util.math.ntheory.primes;


import com.rev.aoc.util.math.ntheory.util.Pow;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;

public final class Factors {

    private Factors() {
    }

    public static LinkedHashMap<Long, Long> factorise(long n) {
        LinkedHashSet<Long> cachedPrimes = PrimeCheck.getPrimeCache();
        long largestCachedPrime = 2;
        LinkedHashMap<Long, Long> factors = new LinkedHashMap<>();

        long reducedN = n;

        Iterator<Long> it = cachedPrimes.iterator();
        while (it.hasNext()) {
            long prime = it.next();
            while (reducedN % prime == 0) {
                if (!factors.containsKey(prime)) {
                    factors.put(prime, 0L);
                }
                factors.put(prime, factors.get(prime) + 1);
                reducedN = reducedN / prime;
            }
            largestCachedPrime = prime;
        }

        if (largestCachedPrime == 2) {
            while (reducedN % 2 == 0) {
                if (!factors.containsKey((2L))) {
                    factors.put(2L, 0L);
                }
                factors.put(2L, factors.get(2L) + 1);
                reducedN = reducedN / 2;
            }
            largestCachedPrime = 3;
        }

        long factorToCheck = largestCachedPrime;
        while (reducedN != 1) {
            while (reducedN % factorToCheck == 0) {
                if (!factors.containsKey(factorToCheck)) {
                    factors.put(factorToCheck, 0L);
                }
                factors.put(factorToCheck, factors.get(factorToCheck) + 1);
                reducedN = reducedN / factorToCheck;
            }
            factorToCheck += 2;
        }

        return factors;
    }

    public static long eulerTotient(long n) {

        if (n == 0) {
            return 0;
        }

        long retval = 1;

        Map<Long, Long> factors = factorise(n);
        Iterator<Long> it = factors.keySet().iterator();
        while (it.hasNext()) {
            long prime = it.next();
            long exponent = factors.get(prime);
            retval *= Pow.pow(prime, exponent - 1) * (prime - 1);
        }
        return retval;
    }

    public static long fastEulerTotient(long n) {
        return fastEulerTotient(n, new HashMap<>());
    }

    public static long fastEulerTotient(long n, final Map<Long, Long> totientCache) {

        if (totientCache.containsKey(n)) {
            return totientCache.get(n);
        }


        if (n == 1) {
            totientCache.put(1L, 1L);
            return 1;
        }

        if (n % 2 == 0) {
            long pow = 1;
            long a = 2;
            long b = n / 2;
            while (b % 2 == 0) {
                a = 2 * a;
                b = b / 2;
                pow += 1;
            }

            if (b == 1) {
                return Pow.pow(2, pow - 1);
            }

            long result = fastEulerTotient(b, totientCache) * Pow.pow(2, pow - 1);
            totientCache.put(n, result);
            return result;
        }

        long limit = (long) Math.ceil(Math.sqrt(n));
        for (long i = 3; i <= limit; i += 2) {
            if (n % i == 0) {
                long pow = 1;
                long a = i;
                long b = n / i;
                while (b % i == 0) {
                    a = i * a;
                    b = b / i;
                    pow += 1;
                }

                if (b == 1) {
                    return (i - 1) * Pow.pow(i, pow - 1);
                }

                long result = fastEulerTotient(b, totientCache) * (i - 1) * Pow.pow(i, pow - 1);
                totientCache.put(n, result);
                return result;
            }
        }

        //n is a prime
        long result = n - 1;
        totientCache.put(n, result);
        return result;
    }

    public static void printFactors(long n) {
        HashMap<Long, Long> factors = factorise(n);
        StringBuilder sb = new StringBuilder();
        Iterator<Long> itFactors = factors.keySet().iterator();
        while (itFactors.hasNext()) {
            long prime = itFactors.next();
            long pow = factors.get(prime);
            sb.append(prime + "^" + pow);
            if (itFactors.hasNext()) {
                sb.append(" * ");
            }
        }
        System.out.print(sb);
    }
}
