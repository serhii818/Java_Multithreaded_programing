package zadanie2;

import java.util.concurrent.Callable;

/**
 * Calculates Collatz sequence, and writes it length and highest number in sequence
 * RULES:
 *      if n is even : n = n/2
 *      if n is odd  : n = 3n + 1
 * Eventually sequence reaches cycle 1 > 4 > 2 > 1
 * There is not proof that all natural numbers end up in this cycle or go to infinity
 */
public class CTask implements Callable<CResult> {

    private final Integer n0;
    private Integer max;
    private Integer size;
    private final Integer limit;

    public CTask(Integer n0, Integer limit) {
        this.n0 = n0;
        this.max = n0;
        this.size = 1;
        this.limit = limit;
    }

    public CResult getRawData() {
        return new CResult(n0, max, size, CResult.Type.EMPTY);
    }

    public Integer getLimit() {
        return limit;
    }

    @Override
    public CResult call() throws Exception {

        Integer n = n0;

        if (n0 <= 0) {
            return new CResult(n0, -1, -1, CResult.Type.BAD_INPUT);
        }

        while (n != 1) {
            Thread.sleep(100);
            size++;

            if (n % 2 == 0) {
                n /= 2;
            } else {
                try {
                    n = Math.addExact(Math.multiplyExact(n, 3), 1);
                } catch (ArithmeticException ex) {
                    return new CResult(n0, max, size-1, CResult.Type.INTEGER_OVERFLOW);
                }
            }

            if (n > max) max = n;

            if (size > limit) {
                return new CResult(n0, max, size, CResult.Type.REACHED_LIMIT);
            }
        }


        return new CResult(n0, max, size, CResult.Type.SUCCESS);
    }
}
