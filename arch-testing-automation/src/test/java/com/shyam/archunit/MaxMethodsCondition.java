package com.shyam.archunit;

import static com.tngtech.archunit.lang.SimpleConditionEvent.satisfied;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;

public class MaxMethodsCondition extends ArchCondition<JavaClass> {

  private final int maxMethods;

  public MaxMethodsCondition(int maxMethods) {
    super("have at most " + maxMethods + " method(s)");
    this.maxMethods = maxMethods;
  }

  @Override
  public void check(JavaClass item, ConditionEvents events) {
    int numberOfMethods = item.getMethods().size();
    if (numberOfMethods > maxMethods) {
      String message = String.format("Class %s has %d method(s), which exceeds the limit of %d",
          item.getName(), numberOfMethods, maxMethods);
      events.add(violated(item, message));
    } else {
      events.add(satisfied(item, item.getDescription()));
    }
  }
}