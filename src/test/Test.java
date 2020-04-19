package test;

import logic.NonBlockingBinarySearchTree;

/**
 * Created by wailien
 */

public class Test {
    public static void main(String [] args) {
        NonBlockingBinarySearchTree nonBlockingBinarySearchTree = new NonBlockingBinarySearchTree();

        int numberOfThreads = 16;
        int values = 10000;

        Thread[] insertThreads = new Thread[numberOfThreads];
        Thread[] findThreads = new Thread[numberOfThreads];
        Thread[] deleteThreads = new Thread[numberOfThreads];

        for (int i = 0; i < insertThreads.length; i++) {
            int k = i;
            insertThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = (values*k)/insertThreads.length; j < (values*(k+1))/insertThreads.length; j++) {
                        nonBlockingBinarySearchTree.insert(j);
                    }
                }
            });
        }

        for (int i = 0; i < findThreads.length; i++) {
            int k = i;
            findThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = (values*k)/findThreads.length; j < (values*(k+1))/findThreads.length; j++) {
                        nonBlockingBinarySearchTree.find(j);
                    }
                }
            });
        }

        for (int i = 0; i < deleteThreads.length; i++) {
            int k = i;
            deleteThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = (values*k)/deleteThreads.length; j < (values*(k+1))/deleteThreads.length; j++) {
                        nonBlockingBinarySearchTree.delete(j);
                    }
                }
            });
        }

        long start = System.currentTimeMillis();
        for (Thread insertThread : insertThreads) {
            insertThread.start();
        }
        try {
            for (Thread insertThread : insertThreads) {
                insertThread.join();
            }
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("INSERT_OPERATION: " + time);
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception");
        }

        start = System.currentTimeMillis();
        for (Thread findThread : findThreads) {
            findThread.start();
        }
        try {
            for (Thread findThread : findThreads) {
                findThread.join();
            }
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("FIND_OPERATION: " + time);
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception");
        }

        start = System.currentTimeMillis();
        for (Thread deleteThread : deleteThreads) {
            deleteThread.start();
        }
        try {
            for (Thread deleteThread : deleteThreads) {
                deleteThread.join();
            }
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("DELETE_OPERATION: " + time);
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception");
        }
    }
}
