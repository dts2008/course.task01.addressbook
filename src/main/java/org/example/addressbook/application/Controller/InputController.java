package org.example.addressbook.application.Controller;

import lombok.Setter;
import org.example.addressbook.application.Common.Interface.Input;
import org.example.addressbook.application.Common.Interface.Output;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Supplier;

public class InputController implements Input {

    private static int defaultInt = 0;

    private final Output output;

    @Setter
    private Supplier<String> source;

    public InputController(Output output) {
        this.output = output;

        source = InputController::getStringDefault;
    }

    @Override
    public Character GetChar(String description) {
        if (description != null)
            output.print(description);

        return source.get().charAt(0);
    }

    @Override
    public String GetString(String description) {
        if (description != null)
            output.print(description);

        return source.get();
    }

    @Override
    public int GetInt(String description) {
        if (description != null)
            output.print(description);

        try {
            return Integer.valueOf(source.get());
        } catch (NumberFormatException e) {
            return defaultInt;
        }
    }

    private static String getStringDefault()
    {
        Scanner in = new Scanner(System.in);

        return in.next();
    }
}
