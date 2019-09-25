package packageOne;
import org.junit.Test;
import org.junit.Assert;
import packageOne.ControlPanel;

public class easyTest {

    @Test
    public void junitTest() {
        Assert.assertEquals("Hello World", ControlPanel.returnHelloWorld());
    }
}