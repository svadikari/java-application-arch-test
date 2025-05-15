package com.shyam.archunit;

import static com.tngtech.archunit.lang.SimpleConditionEvent.satisfied;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;

public class MaxFieldsCondition extends ArchCondition<JavaClass> {

  private final int maxFields;

  public MaxFieldsCondition(int maxFields) {
    super("have at most " + maxFields + " field(s)");
    this.maxFields = maxFields;
  }

  @Override
  public void check(JavaClass item, ConditionEvents events) {
    int numberOfFields = item.getFields().size();
    if (numberOfFields > maxFields) {
      String message = String.format("Class %s has %d field(s), which exceeds the limit of %d",
          item.getName(), numberOfFields, maxFields);
      events.add(violated(item, message));
    } else {
      events.add(satisfied(item, item.getDescription()));
    }
  }
}