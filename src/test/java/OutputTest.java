import Common.Interface.Input;
import Common.Interface.Output;
import Controller.InputController;
import Controller.OutputController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OutputTest {
    @Test
    public void outputTest() {
        Output output = new OutputController();

        assertDoesNotThrow(() -> output.printLn("test 2"));
        assertDoesNotThrow(() -> output.format("%s\r\n", "test 3"));
        assertDoesNotThrow(() -> output.print("test 1"));
    }
}
