import org.example.addressbook.application.Common.Interface.Output;
import org.example.addressbook.application.Controller.InputController;
import org.example.addressbook.application.Controller.OutputController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputTest {

    private InputController input;

    private Output output;

    @BeforeEach
    public  void Init()
    {
        output = new OutputController();
        input = new InputController(output);
    }

    @Test
    public void GetChar() {
        input.setSource(() -> "c");

        System.out.println(input.GetChar(null));
        assertEquals(input.GetChar(null), 'c');
        assertEquals(input.GetChar("test"), 'c');
    }

    @Test
    public void GetString() {
        input.setSource(() -> "Str");

        assertEquals(input.GetString(null), "Str");
        assertEquals(input.GetString("test"), "Str");
    }

    @Test
    public void GetInt() {
        input.setSource(() -> "10");

        assertEquals(input.GetInt(null), 10);
        assertEquals(input.GetInt("test"), 10);
    }
}
