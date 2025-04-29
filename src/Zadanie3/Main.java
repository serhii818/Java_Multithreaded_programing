package Zadanie3;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition canRead = lock.newCondition();
    final Condition canWrite = lock.newCondition();
    private boolean readyToRead = false;
    private final File plik = new File("plik.txt");
    String wynik = null;
    String rownanie = null;

    public void write() throws InterruptedException, IOException {
        lock.lock();
        try {
            while (!readyToRead) {
                canWrite.await();
            }

            try (RandomAccessFile writer = new RandomAccessFile(plik, "rw")) {
                writer.seek(writer.length());
                writer.writeBytes(wynik + System.lineSeparator());
            }

            readyToRead = false;
            canRead.signal();
        } finally {
            lock.unlock();
        }
    }

    public void read() throws InterruptedException, IOException {
        lock.lock();
        try {
            while (readyToRead) {
                canRead.await();
            }

            try (Scanner reader = new Scanner(plik);) {
                String line;
                while (reader.hasNextLine()) {
                	line = reader.nextLine();
                    rownanie = line;
                }
            }

            if (rownanie != null) {
                System.out.println("Odczytano: " + rownanie);
            }

            readyToRead = true;
            canWrite.signal();
        } finally {
            lock.unlock();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer buff = new BoundedBuffer();
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        
        Runnable czytelnik = new Thread(() -> {
            try {
            	buff.read();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        executor.submit(czytelnik);
        Thread.sleep(100);
        
        //pisarz
        FutureTask<String> pisarz = new FutureTask<>(new EquationTask(buff.rownanie)) {
            @Override
            protected void done() {
                try {
                	buff.wynik = get();
                    buff.write();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        

        executor.submit(pisarz);
        executor.shutdown();
    }
}