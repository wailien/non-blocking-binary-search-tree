package types;

/**
 * Created by wailien
 */
public class Update {

    final State state;
    final Info info;

    public Update(State state, Info info) {
        this.state = state;
        this.info = info;
    }

    public State getState() {
        return state;
    }

    public Info getInfo() {
        return info;
    }
}
