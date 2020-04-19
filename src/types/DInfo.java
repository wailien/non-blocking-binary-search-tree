package types;

public class DInfo extends Info {
    final InternalNode grandParent;
    final Update parentUpdate;

    public DInfo(InternalNode grandParent, InternalNode parent, InternalNode leaf, Update parentUpdate) {
        super(parent, leaf);
        this.grandParent = grandParent;
        this.parentUpdate = parentUpdate;
    }

    public InternalNode getGrandParent() {
        return grandParent;
    }

    public Update getParentUpdate() {
        return parentUpdate;
    }
}
