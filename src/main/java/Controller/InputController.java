package Controller;

import Common.Interface.*;

import java.util.Scanner;

public class InputController implements Input {

    private final Output output;

    public InputController(Output output)
    {
        this.output = output;
    }

    @Override
    public Character GetChar(String description) {
        if (description != null)
            output.print(description);

        Scanner in = new Scanner(System.in);
        return in.next().charAt(0);
    }

    @Override
    public String GetString(String description) {
        if (description != null)
            output.print(description);

        Scanner in = new Scanner(System.in);

        return in.next();
    }

    @Override
    public int GetInt(String description) {
        if (description != null)
            output.print(description);

        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
}
