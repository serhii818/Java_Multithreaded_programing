package zadanie1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.LocalTime.now;

class Task extends Thread {
    public String result = "UNFINISHED";
    private boolean isCanceled = false;
    public String name;
    int duration;

    Task(String name, int duration){
        this.name = name;
        System.out.println(name + " utworzony");
        this.duration = duration;
        start();
    }

    @Override
    public void run(){
        System.out.println("Wątek zaczyna pracę o " + now());
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            System.out.println("Wątek " +name+ " przerwany o czasie" + now());
            result = "INTERRUPTED";
            return;
        }
        System.out.println("Wątek "+name+" kończy pracę o " + now());
        result = now()+"";
    }

    public void setCanceled() {
        isCanceled = true;
    }
    public boolean isCanceled(){
        return isCanceled;
    }
}

class TaskManager{
    private List<Task> tasks = new ArrayList<>();

    public void addTask(String name, int duration){
        tasks.add(new Task(name,duration));
    }

    public void showProcessesStatus(){
        System.out.println("Stan procesów na "+now());
        for(int i = 0 ; i < tasks.size() ; i++){
            Task task = tasks.get(i);
            String status = task.isAlive()? "w trakcie " : "zakonczony";
            String interupted = task.isCanceled()? ", przerwany przez uzytkownika" :"";
            System.out.println(task.name + ", Status - " + status + interupted +", czas trwania: " + task.duration);
        }
    }
    public void getStatus(int i){
        Task task = tasks.get(i);
        String status = task.isAlive()? "w trakcie " : "zakonczony";
        String interupted = task.isCanceled()? ", przerwany przez uzytkownika" :"";
        System.out.println(task.name + ", Status - " + status + interupted +", czas trwania: " + task.duration);
    }

    public void cancel(int index){
        if(tasks.size() == 0 || index >= tasks.size() ) throw new IndexOutOfBoundsException("Nie ma takiego procesu");
        tasks.get(index).interrupt();
        tasks.get(index).setCanceled();
    }

    public void getResults(){
        System.out.println("Wyniki procesów na "+now());
        for(int i = 0 ; i < tasks.size() ; i++){
            Task task = tasks.get(i);
            System.out.println(task.name + ", wynik - " + task.result);
        }
    }
}
public class main {
    public static void main(String[] args){

        TaskManager manager = new TaskManager();
        manager.addTask("1",10000);
        manager.addTask("2",5000);
        manager.addTask("3",1000);
        manager.showProcessesStatus();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        manager.getStatus(2);
        manager.cancel(1);
        manager.getResults();
        manager.showProcessesStatus();
        scanner.nextLine();
        manager.showProcessesStatus();
        scanner.nextLine();
        manager.getResults();
    }
}

