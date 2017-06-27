package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // tree object
        AVLTreeMatrix avlt = new AVLTreeMatrix();

        // random number generator
        Random rand = new Random();

        // timers to time the operations
        long timea = 0;
        long timeb = 0;
        long timeI = 0;
        long timeD = 0;

        // number of operations

        for (int ops = 10; ops < 10000000; ops *= 10) {
            System.out.println();
            System.out.println();
            System.out.println("ops = " + ops);
            for (int i = 0; i < ops; ++i) {
                if (rand.nextInt() % 2 == 0) {
                    if (avlt.count < 50) {
                        timea = System.nanoTime();
                        avlt.insert(rand.nextInt(300));
                        timeb = System.nanoTime();
                        timeI += timeb - timea;
                        continue;
                    }
                }
                if (avlt.count > 0) {
                    timea = System.nanoTime();
                    avlt.delete(rand.nextInt(300));
                    timeb = System.nanoTime();
                    timeD += timeb - timea;
                } else {
                    if (avlt.count < 50) {
                        timea = System.nanoTime();
                        avlt.insert(rand.nextInt(300));
                        timeb = System.nanoTime();
                        timeI += timeb - timea;
                        continue;
                    }
                }
            }
            System.out.println("insertions: " + (timeI / 1000000000.0) + " seconds");
            System.out.println("count: " + avlt.count);
            System.out.println("deletions: " + (timeD / 1000000000.0) + " seconds");
            System.out.println("count: " + avlt.count);
            System.out.println("\nAVLTree Tree Test");
        /*  Display tree  */
            System.out.print("\nPost order : ");
            avlt.postorder();
            System.out.print("\nPre order : ");
            avlt.preorder();
            System.out.print("\nIn order : ");
            avlt.inorder();
        }
    }
}
