package com.ralph.perf.demo.switchboards;

public class UnSharedSwitchBoard implements SwitchBoard {

    @jdk.internal.vm.annotation.Contended
    private volatile boolean switch1;
    @jdk.internal.vm.annotation.Contended
    private volatile boolean switch2;

    @Override
    public void flipSwitch1(){
        switch1 = !switch1;
    }
    @Override
    public void flipSwitch2(){
        switch2 = !switch2;
    }
}
