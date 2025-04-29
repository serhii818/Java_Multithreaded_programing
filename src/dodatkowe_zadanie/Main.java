package dodatkowe_zadanie;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static void infinite_loop_exemple() {
        System.out.println(BLUE + "przyklad nieskonczonej petli" + RESET);
        int num_of_threads = 3;
        AtomicInteger num_active = new AtomicInteger(num_of_threads);

        for (int i = 0; i < num_of_threads; i++) {
            int finalI = i;
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(name + " zaczyna pracę");
                try {
                    Thread.sleep((int)(3000 + finalI *1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // sygnal o zakonczeniiu
                num_active.getAndDecrement();

                System.out.println(name + " kończy pracę pracę, zostało : " + num_active);

            }, "TH-" + i).start();
        }

        while (!(num_active.get() == 0)) {
            // czeka na zakonczenie
        }
        System.out.println("Wszystkie wątki skończyły pracę");
        System.out.println(BLUE + "przyklad nieskonczonej petli, koniec\n" + RESET);
    }

    public static void cyclic_barier_exemple() throws BrokenBarrierException, InterruptedException {
        System.out.println(BLUE + "przyklad cyklicznej bariery" + RESET);
        int num_of_threads = 3;
        CyclicBarrier cb = new CyclicBarrier(num_of_threads+1);

        for (int i = 0; i < num_of_threads; i++) {
            int finalI = i;
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(name + " zaczyna pracę");
                try {
                    Thread.sleep((int)(3000 + finalI *1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " kończy pracę pracę");
                try {
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }




            }, "TH-" + i).start();
        }

        cb.await();
        System.out.println("Wszystkie wątki skończyły pracę");
        System.out.println(BLUE + "przyklad cyklicznej bariery, koniec\n" + RESET);
    }

    public static void count_down_latch_exemple() throws InterruptedException {
        System.out.println(BLUE + "przyklad ConutDownLatch" + RESET);
        int num_of_threads = 3;
        CountDownLatch latch = new CountDownLatch(num_of_threads);

        for (int i = 0; i < num_of_threads; i++) {
            int finalI = i;
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(name + " zaczyna pracę");
                try {
                    Thread.sleep((int)(3000 + finalI *1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " kończy pracę pracę");
                latch.countDown();



            }, "TH-" + i).start();
        }

        latch.await();
        System.out.println("Wszystkie wątki skończyły pracę");
        System.out.println(BLUE + "przyklad ConutDownLatch, koniec\n" + RESET);
    }

    public static void main(String[] argc) throws BrokenBarrierException, InterruptedException {
        infinite_loop_exemple();
        cyclic_barier_exemple();
        count_down_latch_exemple();
    }
}
