package org.example;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotationWith(List.of(Controller.class, Service.class));

        logger.debug("beans: [{}]", beans);

    }

    private Set<Class<?>> getTypesAnnotationWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example"); // org.example패키지 내부에 있는 모든 클래스를 대상으로 Reflection을 사용

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotationWith((annotation))));
        // beans.addAll(reflections.getTypesAnnotatedWith(Controller.class));  // Controller라는 애노테이션이 붙어져있는 대상들을 찾아 HashSet에 모두 담는 코드
        // beans.addAll(reflections.getTypesAnnotatedWith(Service.class));
        return beans;
    }
}
