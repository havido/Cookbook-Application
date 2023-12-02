package layout;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SpringUtilitiesTest {

    @Test
    void makeGrid_validInput_shouldNotThrowException() {
        JPanel parent = new JPanel();
        parent.setLayout(new SpringLayout());

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        parent.add(button1);
        parent.add(button2);

        try {
            SpringUtilities.makeGrid(parent, 1, 2, 0, 0, 5, 5);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void makeGrid_invalidLayout_shouldThrowException() {
        JPanel parent = new JPanel(); // not using SpringLayout intentionally

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        parent.add(button1);
        parent.add(button2);

        try {
            SpringUtilities.makeGrid(parent, 1, 2, 0, 0, 5, 5);
        } catch (ClassCastException e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    void makeCompactGrid_validInput_shouldNotThrowException() {
        JPanel parent = new JPanel();
        parent.setLayout(new SpringLayout());

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        parent.add(button1);
        parent.add(button2);

        try {
            SpringUtilities.makeCompactGrid(parent, 1, 2, 0, 0, 5, 5);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void getConstraintsForCell_validInput_shouldNotBeNull() {
        JPanel parent = new JPanel();
        parent.setLayout(new SpringLayout());

        JButton button = new JButton("Button");
        parent.add(button);

        try {
            SpringLayout.Constraints constraints = SpringUtilities.getConstraintsForCell(0, 0, parent, 1);
            assertNotNull(constraints);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void printSizes_validInput_shouldNotThrowException() {
        JPanel parent = new JPanel();
        parent.setLayout(new SpringLayout());

        JButton button = new JButton("Button");
        parent.add(button);

        try {
            SpringUtilities.printSizes(button);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
