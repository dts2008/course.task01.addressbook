package org.example.addressbook.application.Common.Interface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MenuItem {
    public  String Name() default "Item";

    public int Order() default Integer.MAX_VALUE;

    public char Key();
}
