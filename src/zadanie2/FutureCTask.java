package zadanie2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureCTask extends FutureTask<CResult> {
    private final ConcurrentHashMap<Integer, CResult> results;
    private final CTask task;

    public FutureCTask(CTask ctask, ConcurrentHashMap<Integer, CResult> hashmap) {
        super(ctask);
        task = ctask;
        results = hashmap;
    }

    @Override
    protected void done() {
        CResult result = new CResult();

        if (isCancelled()) {
            result = task.getRawData();
            result.type = CResult.Type.INTERRUPTED;

        } else if (isDone()) {
            try {
                result = get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        results.put(result.n0, result);
    }

    @Override
    public String toString() {
        CResult data = task.getRawData();
        State state = state();
        return ASCII.CYAN + "Task " + ASCII.RESET + " : " + data.n0 + ", size reached : " + data.size + ", state : " +
                ((state == State.CANCELLED || state == State.FAILED) ? ASCII.RED : ASCII.GREEN) +
                state + ASCII.RESET;
    }
}
