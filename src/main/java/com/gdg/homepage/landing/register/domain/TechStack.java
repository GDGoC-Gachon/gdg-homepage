package com.gdg.homepage.landing.register.domain;

import java.util.Arrays;
import java.util.List;

public enum TechStack {
    // Frontend
    REACT,
    VUE_JS,
    ANGULAR,
    NEXT_JS,
    SVELTE,

    // Backend
    NODE_JS,
    EXPRESS_JS,
    NEST_JS,
    SPRING_BOOT,
    DJANGO,
    FLASK,
    FAST_API,
    RUBY_ON_RAILS,
    SWIFT,

    // Mobile & Language
    KOTLIN,
    FLUTTER,
    REACT_NATIVE,

    // AI / ML
    TENSORFLOW,
    PYTORCH,

    // DevOps
    DOCKER,
    KUBERNETES,

    // Cloud
    AWS,
    AZURE,
    GOOGLE_CLOUD,
    FIREBASE,

    // API
    GRAPHQL,

    // DB
    MYSQL,
    POSTGRESQL,
    REDIS,
    ELASTICSEARCH,
    MONGODB,

    // 기타
    OTHER;

    // TechField에 따른 TechStack 매칭
//    public static List<TechStack> getStacksByField(TechField field) {
//        return switch (field) {
//            case AI -> Arrays.asList(PYTORCH, TENSORFLOW);
//            case BACK -> Arrays.asList(SPRING, DJANGO, NODE);
//            case DATA -> Arrays.asList(HADOOP, SPARK);
//            case FRONT -> Arrays.asList(REACT, VUE);
//            case OTHER -> List.of(OTHER);
//        };
//    }
}
