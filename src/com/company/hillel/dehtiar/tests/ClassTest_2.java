package com.company.hillel.dehtiar.tests;

import com.company.hillel.dehtiar.annotations.AfterSuit;
import com.company.hillel.dehtiar.annotations.BeforeSuit;
import com.company.hillel.dehtiar.annotations.Test;

/**
 * {@link ClassTest_2} is a testing class
 * <p>Test-methods in this class must be public & have annotation:
 * {@link BeforeSuit}, {@link Test}, {@link AfterSuit}.
 * <p>These annotations are using for priority
 * {@link com.company.hillel.dehtiar.service.TestRunner}.
 * <p>
 *
 * @author Yaroslav Dehtiar on 16.11.2022
 */
public class ClassTest_2 {

  @Test(4)
  public void test4() {
    System.out.println("Reaction from - test 4");
  }

  @Test(4)
  public void test2() {
    System.out.println("Reaction from - test 2");
  }

  @Test(3)
  public void test3() {
    System.out.println("Reaction from - test 3");
  }

  @AfterSuit
  public void afterSuite() {
    System.out.println("Reaction from - AfterSuite !");
  }

  @Test(1)
  public void test1() {
    System.out.println("Reaction from - test 1");
  }

  @Test(1)
  public void test5() {
    System.out.println("Reaction from - test 5");
  }

  @BeforeSuit
  public void beforeSuite() {
    System.out.println("Reaction from - BeforeSuite !");
  }
}
