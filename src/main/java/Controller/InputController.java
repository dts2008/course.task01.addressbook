package Controller;

import Common.Interface.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;
import java.util.function.Supplier;

public class InputController implements Input {

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

        return Integer.valueOf(source.get());
    }

    private static String getStringDefault()
    {
        Scanner in = new Scanner(System.in);

        return in.next();
    }
}
