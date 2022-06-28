package org.example.addressbook.application.Controller;

import org.example.addressbook.application.Common.Interface.Output;
import org.springframework.stereotype.Component;

public class OutputController implements Output {

    @Override
    public void print(String str) {
        System.out.print(str);
    }

    @Override
    public void printLn(String str) {
        System.out.println(str);
    }

    @Override
    public void format(String format, Object... args) {
        System.out.format(format, args);
    }
}
