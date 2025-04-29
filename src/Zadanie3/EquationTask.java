package Zadanie3;

import java.util.concurrent.Callable;


class EquationTask implements Callable<String> {
    private final String rownanie;

    public EquationTask(String rownanie) {
        this.rownanie = rownanie;
    }

    @Override
    public String call() throws Exception {
        ONP onp = new ONP();
        String rownanieOnp = onp.przeksztalcNaOnp(rownanie);
        String wynik = onp.obliczOnp(rownanieOnp);
        return wynik;
    }
}
