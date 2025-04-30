package com.gdg.homepage.common.domain;

public interface StatisticsProjection {
    int getCurrent();
    int getPrevious();

    default int increase() {
        return getCurrent() - getPrevious();
    }

    default int change() {
        return getPrevious() - getCurrent();
    }

    default int current() {
        return getPrevious();
    }
}
