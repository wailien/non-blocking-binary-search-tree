package types;

public class Leaf extends Node {
    volatile Leaf right;
    volatile Leaf left;
    volatile Update update;

    public Leaf(Integer key) {
        super(key);
        this.right = null;
        this.left = null;
        this.update = new Update(State.CLEAN, null);
    }
}
