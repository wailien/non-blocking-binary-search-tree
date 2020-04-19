package types;

/**
 * Created by wailien
 */
public class Info {
    final InternalNode parent;
    final InternalNode leaf;

    public Info(InternalNode parent, InternalNode leaf) {
        this.parent = parent;
        this.leaf = leaf;
    }

    public InternalNode getParent() {
        return parent;
    }

    public InternalNode getLeaf() {
        return leaf;
    }
}

