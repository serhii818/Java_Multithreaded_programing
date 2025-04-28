package zadanie2;

public class CResult {
    public enum Type {
        SUCCESS,
        INTEGER_OVERFLOW,
        INTERRUPTED,
        BAD_INPUT,
        EMPTY,
        REACHED_LIMIT
    }

    public Integer n0;
    public Integer max;
    public Integer size;
    public Type type;

    public CResult() {
        this(-1, -1, -1, Type.EMPTY);
    }

    public CResult(Integer n0, Integer max, Integer size, Type type) {
        this.n0 = n0;
        this.max = max;
        this.size = size;
        this.type = type;
    }


    public static CResult make(Integer n0, Integer max, Integer size, Type type) {
        return new CResult(n0, max, size, type);
    }

    @Override
    public String toString() {
        String color = "";
        switch (type) {
            case Type.BAD_INPUT:
            case Type.REACHED_LIMIT:
            case Type.INTERRUPTED:
            case Type.INTEGER_OVERFLOW:
                color = ASCII.RED;
                break;
            case Type.EMPTY:
                color = ASCII.YELLOW;
                break;
            case Type.SUCCESS:
                color = ASCII.GREEN;
                break;
        }

        return ASCII.BLUE + "RESULT " + ASCII.RESET + " : n0=" + n0 +
                " ,max term=" + max +
                " , sequence length=" + size + " : " +
                color  + type + ASCII.RESET;
    }
}
