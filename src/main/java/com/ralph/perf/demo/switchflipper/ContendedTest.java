package com.ralph.perf.demo.switchflipper;


import com.ralph.perf.demo.executableTask.Task;
import com.ralph.perf.demo.switchboards.FalseSharedSwitchBoard;
import com.ralph.perf.demo.switchboards.SwitchBoard;
import com.ralph.perf.demo.switchboards.UnSharedSwitchBoard;

import static java.lang.String.format;

public class ContendedTest {
    private static final int FLIPS = 100_000_000;

    public static void main(String[] args) throws InterruptedException {

        ContendedTest contendedTest = new ContendedTest();
        contendedTest.testPerformanceDifference();
    }

    private void testPerformanceDifference() throws InterruptedException {
        testFalseSharedPerformance();
        testUnsharedPerformance();
    }

    private void testFalseSharedPerformance() throws InterruptedException {
        SwitchBoard switchBoard = new FalseSharedSwitchBoard();
        System.out.println("Testing false shared switchboard");
        testSwitchBoardPerformance(switchBoard);
        System.out.println("====================================================");
    }
    private void testUnsharedPerformance() throws InterruptedException {
        SwitchBoard switchBoard = new UnSharedSwitchBoard();
        System.out.println("Testing unshared switchboard");
        testSwitchBoardPerformance(switchBoard);
        System.out.println("====================================================");
    }

    private void testSwitchBoardPerformance(SwitchBoard switchBoard) throws InterruptedException {
        Thread thread1 = getRepeaterThread(switchBoard::flipSwitch1);
        Thread thread2 = getRepeaterThread(switchBoard::flipSwitch2);
        long startTime = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long endTime = System.currentTimeMillis();
        System.out.printf("Total time for a %d flips %d milli seconds%n",FLIPS,  (endTime-startTime));
    }

    private Thread getRepeaterThread(Task task) {
        return new Thread(()->{
            for(int i=0; i<FLIPS; i++){
                task.execute();
            }
        });
    }
}
