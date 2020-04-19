package types;

public class IInfo extends Info {
    final InternalNode newInternalNode;

    public IInfo(InternalNode parent, InternalNode newInternalNode, InternalNode leaf) {
        super(parent, leaf);
        this.newInternalNode = newInternalNode;
    }

    public InternalNode getNewInternalNode() {
        return newInternalNode;
    }
}
