package types;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class InternalNode extends Node{ // чтобы типы не приводить, обойдемся без leaf
//    volatile Leaf right;
//    volatile Leaf left;
    volatile InternalNode right;
    volatile InternalNode left;
    volatile Update update; //хранит состояние и дополнительную информацию

    public InternalNode(Integer key, InternalNode right, InternalNode left) {
        super(key);
        this.right = right;
        this.left = left;
        this.update = new Update(State.CLEAN, null);
    }

//    public InternalNode(Integer key, Leaf right, Leaf left) {
//        super(key);
//        this.right = right;
//        this.left = left;
//        this.update = new Update(State.CLEAN, null);
//    }

    public Integer getKey() {
        return super.key;
    }

    public InternalNode getRight() {
        return right;
    }

    public InternalNode getLeft() {
        return left;
    }

    public Update getUpdate() {
        return update;
    }

    final AtomicReferenceFieldUpdater<InternalNode, InternalNode> leftUpdater =
            AtomicReferenceFieldUpdater.newUpdater(InternalNode.class, InternalNode.class, "left");
    final AtomicReferenceFieldUpdater<InternalNode, InternalNode> rightUpdater =
            AtomicReferenceFieldUpdater.newUpdater(InternalNode.class, InternalNode.class, "right");
    final AtomicReferenceFieldUpdater<InternalNode, Update> updateUpdater =
            AtomicReferenceFieldUpdater.newUpdater(InternalNode.class, Update.class, "update");

    public boolean compareAndSetLeft(InternalNode expect, InternalNode update) {
        return leftUpdater.compareAndSet(this, expect, update);
    }

    public boolean compareAndSetRight(InternalNode expect, InternalNode update) {
        return rightUpdater.compareAndSet(this, expect, update);
    }

    public boolean compareAndSetUpdate(Update expect, Update update) {
        return updateUpdater.compareAndSet(this, expect, update);
    }
}
