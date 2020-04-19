package logic;

import types.*;

/**
 * Created by wailien
 */

public class NonBlockingBinarySearchTree {
    final InternalNode root;

    public NonBlockingBinarySearchTree() { //создаем корень дерева и два листа
        root = new InternalNode(Integer.MAX_VALUE, new InternalNode(Integer.MAX_VALUE - 1, null, null), new InternalNode(Integer.MAX_VALUE, null, null));
    }

    public Search search(Integer k) {
        InternalNode grandParent = null;
        InternalNode parent = root;
        InternalNode leaf = parent.getLeft();
        Integer leafKey = leaf.getKey();

        while (leaf.getLeft() != null) {
            grandParent = parent;
            parent = leaf;
            if (k.compareTo(leafKey) < 0) {
                leaf = leaf.getLeft();
            } else {
                leaf = leaf.getRight();
            }
            leafKey = leaf.getKey();
        }

        Update grandParentUpdate = null;
        Update parentUpdate = parent.getUpdate();
        if (grandParent != null) {
            grandParentUpdate = grandParent.getUpdate();
        }

        return new Search(grandParent, parent, leaf, parentUpdate, grandParentUpdate);
    }

    public InternalNode find(Integer k) {
        Search search = search(k);
        InternalNode leaf = search.getLeaf();
        Integer leafKey = leaf.getKey();

        if (k.compareTo(leafKey) != 0) {
            return null;
        }
        return leaf;
    }

    public boolean insert(Integer k) {
        InternalNode newNode = new InternalNode(k, null, null);
        InternalNode newSibling;
        InternalNode newInternal;

        while (true) {
            Search result = search(k);
            InternalNode parent = result.getParent();
            InternalNode leaf = result.getLeaf();
            Update parentUpdate = result.getParentUpdate();
            Integer leafKey = leaf.getKey();

            if (k.compareTo(leafKey) == 0) {
                return false;
            } else if (parentUpdate != null && parentUpdate.getState() != State.CLEAN) {
                help(parentUpdate);
            } else {
                newSibling = new InternalNode(leafKey, null, null);
                if (k.compareTo(leafKey) < 0) {
                    newInternal = new InternalNode(leafKey, newNode, newSibling);
                } else {
                    newInternal = new InternalNode(k, newSibling, newNode);
                }

                IInfo newInfo = new IInfo(parent, leaf, newInternal);
                final Update newUpdate = new Update(State.INSERT_FLAG, newInfo);
                if (parent.compareAndSetUpdate(parentUpdate, newUpdate)) {
                    helpInsert(newUpdate);
                    return true;
                } else {
                    help(parent.getUpdate());
                }
            }
        }
    }

    public void helpInsert(Update update) {
        IInfo info = (IInfo) update.getInfo();
        InternalNode parent = info.getParent();

        casChild(parent, info.getLeaf(), info.getNewInternalNode());

        parent.compareAndSetUpdate(update, new Update(State.CLEAN, info));
    }

    public boolean delete(Integer k) {
        while (true) {
            Search result = search(k);
            InternalNode grandParent = result.getGrandParent();
            InternalNode parent = result.getParent();
            InternalNode leaf = result.getLeaf();
            Update grandParentUpdate = result.getGrandParentUpdate();
            Update parentUpdate = result.getParentUpdate();
            Integer leafKey = leaf.getKey();

            if (k.compareTo(leafKey) != 0) {
                return false;
            } else if (grandParentUpdate != null && grandParentUpdate.getState() != State.CLEAN) {
                help(grandParentUpdate);
            } else if (parentUpdate != null && parentUpdate.getState() != State.CLEAN) {
                help(parentUpdate);
            } else {

                DInfo newInfo = new DInfo(grandParent, parent, leaf, parentUpdate);
                final Update newUpdate = new Update(State.DELETE_FLAG, newInfo);

                if (grandParent.compareAndSetUpdate(grandParentUpdate, newUpdate)) {
                    if (helpDelete(newUpdate)) {
                        return true;
                    }
                } else {
                    help(grandParent.getUpdate());
                }
            }
        }
    }

    public boolean helpDelete(Update update) {
        DInfo info = (DInfo) update.getInfo();
        InternalNode grandParent = info.getGrandParent();
        InternalNode parent = info.getParent();

        final Update newUpdate = new Update(State.MARK, info);
        if (parent.compareAndSetUpdate(info.getParentUpdate(), newUpdate)) {
            helpMarked(update);
            return true;
        } else {
            help(parent.getUpdate());
            grandParent.compareAndSetUpdate(update, new Update(State.CLEAN, info)); // backtrack
            return false;
        }
    }

    public void helpMarked(Update update) {
        DInfo info = (DInfo) update.getInfo();
        InternalNode grandParent = info.getGrandParent();
        InternalNode parent = info.getParent();
        InternalNode other;

        if (parent.getRight() == info.getLeaf()) {
            other = parent.getLeft();
        } else {
            other = parent.getRight();
        }

        casChild(grandParent, parent, other);

        grandParent.compareAndSetUpdate(update, new Update(State.CLEAN, info));
    }

    public void help(Update update) {
        if (update.getState() == State.INSERT_FLAG) {
            helpInsert(update);
        } else if (update.getState() == State.DELETE_FLAG) {
            helpDelete(update);
        } else if (update.getState() == State.MARK) {
            helpMarked(update);
        }
    }

    public void casChild(InternalNode parent, InternalNode leaf, InternalNode newInternal) {
        if (parent.getLeft() == leaf) {
            parent.compareAndSetLeft(leaf, newInternal);
        } else {
            parent.compareAndSetRight(leaf, newInternal);
        }
    }
}
