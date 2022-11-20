package org.example;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotationWith(List.of(Controller.class, Service.class));

        logger.debug("beans: [{}]", beans);

    }

    @Test
    void load() throws ClassNotFoundException {
        Class<User> clazz = User.class; // 힙 영역에 로드되어있는 클래스타입의 객체를 가져올 수 있다. 첫번째 방법

        // 두번째 방법
        User user = new User("serverwizard", "김재령");
        Class<? extends User> clazz2 = user.getClass();

        // 세번째 방법
        Class<?> clazz3 = Class.forName("org.example.model.User");

        logger.debug("clazz: [{}]", clazz);
        logger.debug("clazz2: [{}]", clazz2);
        logger.debug("clazz3: [{}]", clazz3);

        // clazz, clazz2, clazz3이 같은것인지 확인하는 것
        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz == clazz3).isTrue();
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));  // User에 선언된 모든 필드를 출력하는 것
        logger.debug("User all declared constructors: [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));  // User에 선언된 생성자를 가져온다.
        logger.debug("User all declared methods: [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));    // User에 선언된 메소드를 가져온다.
    }

    private Set<Class<?>> getTypesAnnotationWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example"); // org.example패키지 내부에 있는 모든 클래스를 대상으로 Reflection을 사용

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));
        // beans.addAll(reflections.getTypesAnnotatedWith(Controller.class));  // Controller라는 애노테이션이 붙어져있는 대상들을 찾아 HashSet에 모두 담는 코드
        // beans.addAll(reflections.getTypesAnnotatedWith(Service.class));
        return beans;
    }
}
