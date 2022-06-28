package org.example.addressbook.application.Common.Interface;

public interface Output {
    void print(String str);

    void printLn(String str);

    void format(String format, Object ... args);
}
