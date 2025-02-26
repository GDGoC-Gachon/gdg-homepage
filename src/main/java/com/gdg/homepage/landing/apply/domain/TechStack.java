package com.gdg.homepage.landing.apply.domain;

import java.util.Arrays;
import java.util.List;

public enum TechStack {
    SPRING, DJANGO, NODE,
    PYTORCH, TENSORFLOW,
    REACT, VUE,
    HADOOP, SPARK,
    OTHER;

    // TechField에 따른 TechStack 매칭
    public static List<TechStack> getStacksByField(TechField field) {
        return switch (field) {
            case AI -> Arrays.asList(PYTORCH, TENSORFLOW);
            case BACK -> Arrays.asList(SPRING, DJANGO, NODE);
            case DATA -> Arrays.asList(HADOOP, SPARK);
            case FRONT -> Arrays.asList(REACT, VUE);
            case OTHER -> List.of(OTHER);
        };
    }
}
