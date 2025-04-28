package zadanie2;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class CTaskManager {



    // #################################################################################################################
    private ConcurrentHashMap<Integer, CResult> results = new ConcurrentHashMap<>();
    private HashMap<Integer, FutureCTask> tasks = new HashMap<>();
    private HashMap<Integer, Thread> threads = new HashMap<>();


    /**
     * console like interface to manage task
     * write help for commands
     * syntax:
     *      command parameters
     */
    public void runSession() {

        welcome();
        boolean quit = false;
        String command;
        String[] command_spl;
        Scanner scr = new Scanner(System.in);

        while (!quit) {
            System.out.print(">>>");
            command = scr.nextLine();
            command_spl = command.split(" ");

            switch (command_spl[0]) {
                case "quit":
                case "exit":
                    quit = true;
                    break;
                case "run":
                    option_run(command_spl);
                    break;
                case "show":
                case "show_task":
                case "state":
                case "get_state":
                case "show_state":
                    option_show(command_spl);
                    break;
                case "cancel":
                case "cancel_task":
                case "cancel_ctask":
                    option_cancel(command_spl);
                    break;
                case "get":
                case "get_result":
                    option_get(command_spl);
                    break;
                case "help":
                case "/h":
                case "?":
                   option_help();
                    break;
                default:
                    System.out.println(ASCII.RED + "COMMAND UNRECOGNIZED" + ASCII.RESET);
            }

            System.out.println();
        }

        System.out.println(ASCII.PURPLE + "Good bye !" + ASCII.RESET);
    }

    private void option_run(String[] command_spl) {
        for (int i = 1; i < command_spl.length; i++) {
            Integer n = -1;
            try {
                n = Integer.valueOf(command_spl[1]);
            } catch (NumberFormatException ex) {
                System.out.println(ASCII.RED);
                System.out.println(ex.getMessage());
                System.out.println(ASCII.RESET);
            }
            run_ctask(n);
        }
    }

    private void option_show(String[] command_spl) {
        if (command_spl[1].equals("all")) {
            for (Integer n: tasks.keySet()) {
                print_state(n);
            }
        } else  {
            for (int i = 1; i < command_spl.length; i++) {
                Integer n = -1;
                try {
                    n = Integer.valueOf(command_spl[1]);
                } catch (NumberFormatException ex) {
                    System.out.println(ASCII.RED);
                    System.out.println(ex.getMessage());
                    System.out.println(ASCII.RESET);
                }
                print_state(n);
            }
        }
    }

    private void option_get(String[] command_spl) {
        for (int i = 1; i < command_spl.length; i++) {
            Integer n = -1;
            try {
                n = Integer.valueOf(command_spl[1]);
            } catch (NumberFormatException ex) {
                System.out.println(ASCII.RED);
                System.out.println(ex.getMessage());
                System.out.println(ASCII.RESET);
            }
            print_result(n);
        }
    }

    private void option_cancel(String[] command_spl) {
        if (command_spl[1].equals("all")) {
            for (Integer n: tasks.keySet()) {
                cancel(n);
            }
        } else {
            for (int i = 1; i < command_spl.length; i++) {
                Integer n = -1;
                try {
                    n = Integer.valueOf(command_spl[1]);
                } catch (NumberFormatException ex) {
                    System.out.println(ASCII.RED);
                    System.out.println(ex.getMessage());
                    System.out.println(ASCII.RESET);
                }
                cancel(n);
            }
        }
    }

    private void option_help() {
        System.out.println(ASCII.CYAN + ASCII.ITALIC);
        System.out.println("Syntax:");
        System.out.println("    command [args..]");
        System.out.println("    arguments should be separated with space");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("    run <int> - runs CTask");
        System.out.println("    run <int> <int> ... - runs multiple CTasks");
        System.out.println("    show <int> - shows CTask");
        System.out.println("    show <int> <int> ... - shows multiple CTasks");
        System.out.println("    get <int> - gets CResult");
        System.out.println("    get <int> <int> ... - gets multiple CResults");
        System.out.println("    cancel <int> - cancels CTask");
        System.out.println("    cancel <int> <int> ... - cancels multiple CTasks");
        System.out.println("    quit - quits session");
        System.out.println(ASCII.RESET);
    }

    private void welcome() {
        System.out.println("Welcome");
        System.out.println("write " + ASCII.BLINK + ASCII.PURPLE + " help " + ASCII.RESET + " for commands");
    }

    public void test() {
        run_ctask(63_728_127);
        print_result(63_728_127);
        print_state(63_728_127);
        run_ctask(63_728);
        run_ctask(64);
        cancel(63_728);
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        print_result(63_728_127);
        print_result(63_728);
        print_result(64);
        print_state(1);
        print_result(1);
    }

    public void run_ctask(Integer n) {
        FutureCTask ftask = new FutureCTask(new CTask(n, 1000000), results);
        Thread th = new Thread(ftask);
        th.start();

        results.put(n, new CResult(n, -1, -1, CResult.Type.EMPTY));
        tasks.put(n, ftask);
        threads.put(n, th);
    }

    public void print_state(Integer n) {
        FutureCTask task = tasks.get(n);
        if (task != null) System.out.println(task.toString());
        else System.out.println(ASCII.YELLOW + "TASK " + n + " DOESN'T EXIST" + ASCII.RESET);
    }

    public void print_result(Integer n) {
        CResult result = results.get(n);
        if ( result != null ) System.out.println(result.toString());
        else System.out.println(ASCII.YELLOW + "NO RESULT FOR " + n + ASCII.RESET);
    }

    public void cancel(Integer n) {
        FutureCTask task = tasks.get(n);
        if (task != null) task.cancel(true);
        else System.out.println(ASCII.YELLOW + "TASK " + n + " DOESN'T EXIST" + ASCII.RESET);
    }
}
