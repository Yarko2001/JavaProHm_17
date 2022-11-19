package com.company.hillel.dehtiar.service;

import com.company.hillel.dehtiar.annotations.AfterSuit;
import com.company.hillel.dehtiar.annotations.BeforeSuit;
import com.company.hillel.dehtiar.annotations.Test;
import com.company.hillel.dehtiar.tests.ClassTest_1;
import com.company.hillel.dehtiar.tests.ClassTest_2;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link TestRunner} is using for testing some methods
 * <p>Public method {@code start} takes an object of the class-type object.
 * <p>{@code  Class classTest} contains some testing methods.
 * Testing methods must be only public. Testing methods starts only with priority level of
 * annotations:
 * <p>{@link AfterSuit} annotation has the lowest priority level, and can be set only once.
 * If not throws {@exception IllegalAccessException}.
 *
 * <p>{@link Test} annotation has a priority value (1...10).
 * Method won't run if value will go beyond these aisles (1...10)
 *
 * <p>{@link BeforeSuit} has the highest priority for running method, and can be set only once.
 * If not throws {@exception IllegalAccessException}.
 * <p>
 *
 * @author Yaroslav Dehtiar on 16.11.2022
 */
public class TestRunner {

  private final static int MIN = 0;
  private final static int MAX = 10;

  public static void main(String[] args) {
    try {
      TestRunner.start(ClassTest_1.class);
      TestRunner.start(ClassTest_2.class);
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static void start(Class<?> classTest)
      throws InstantiationException, IllegalAccessException {

    Object instance = classTest.newInstance();
    List<Method> listOfBeforeSuits = getAnnotationMethod(instance, BeforeSuit.class);
    List<Method> test = getSortTestMethods(instance, MIN, MAX);
    List<Method> listOfAfterSuites = getAnnotationMethod(instance, AfterSuit.class);
    List<Method> collectedMethods = getAllSortedMethods(listOfBeforeSuits, test, listOfAfterSuites);
    startAnnotationOfTheMethod(instance, collectedMethods);

  }

  private static List<Method> getAnnotationMethod(Object testClass,
      Class<? extends Annotation> annotation) {

    List<Method> methods = Arrays.asList(testClass.getClass().getMethods());

    List<Method> methodList = methods.stream()
        .filter(method -> method.isAnnotationPresent(annotation))
        .collect(Collectors.toList());

    moreThanOneAnnotationException(methodList);

    return methodList;
  }

  private static List<Method> getSortTestMethods(Object testClass, int min, int max) {
    List<Method> methods = Arrays.asList(testClass.getClass().getMethods());
    return methods.stream()
        .filter(method -> method.isAnnotationPresent(Test.class))
        .filter(method -> method.getAnnotation(Test.class).value() > min
            && method.getAnnotation(Test.class).value() <= max)
        .sorted(Comparator.comparingInt(method -> method.getAnnotation(Test.class).value()))
        .collect(Collectors.toList());
  }

  private static List<Method> getAllSortedMethods(List<Method> beforeSuites, List<Method> test,
      List<Method> afterSuites) {
    List<Method> sortedMethods = new ArrayList<>();
    sortedMethods.addAll(beforeSuites);
    sortedMethods.addAll(test);
    sortedMethods.addAll(afterSuites);
    return sortedMethods;
  }

  private static void startAnnotationOfTheMethod(Object testClass, List<Method> sortedMethods) {
    for (Method method : sortedMethods) {
      try {
        method.invoke(testClass);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static void moreThanOneAnnotationException(List<Method> methods) {
    String warning = "Incorrect ! It's possible to use once annotation to method " + methods;
    if (methods.size() > 1) {
      throw new IllegalStateException(warning);
    }
  }

}
