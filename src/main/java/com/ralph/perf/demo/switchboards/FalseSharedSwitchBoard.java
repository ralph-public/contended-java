package com.ralph.perf.demo.switchboards;

public class FalseSharedSwitchBoard implements SwitchBoard {
    private volatile boolean switch1;

    private volatile boolean switch2;

    @Override
    public void flipSwitch1() {
        switch1 = !switch1;
    }

    @Override
    public void flipSwitch2() {
        switch2 = !switch2;
    }
}
