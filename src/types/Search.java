package types;

/**
 * Created by wailien
 */
public class Search { // дополнительный тип для реализации рекурсивного поиска
    InternalNode grandParent;
    InternalNode parent;
    InternalNode leaf;
    Update parentUpdate;
    Update grandParentUpdate;

    public Search(InternalNode grandParent, InternalNode parent, InternalNode leaf, Update parentUpdate, Update grandParentUpdate) {
        this.grandParent = grandParent;
        this.parent = parent;
        this.leaf = leaf;
        this.parentUpdate = parentUpdate;
        this.grandParentUpdate = grandParentUpdate;
    }

    public InternalNode getGrandParent() {
        return grandParent;
    }

    public void setGrandParent(InternalNode grandParent) {
        this.grandParent = grandParent;
    }

    public InternalNode getParent() {
        return parent;
    }

    public void setParent(InternalNode parent) {
        this.parent = parent;
    }

    public InternalNode getLeaf() {
        return leaf;
    }

    public void setLeaf(InternalNode leaf) {
        this.leaf = leaf;
    }

    public Update getParentUpdate() {
        return parentUpdate;
    }

    public void setParentUpdate(Update parentUpdate) {
        this.parentUpdate = parentUpdate;
    }

    public Update getGrandParentUpdate() {
        return grandParentUpdate;
    }

    public void setGrandParentUpdate(Update grandParentUpdate) {
        this.grandParentUpdate = grandParentUpdate;
    }
}
