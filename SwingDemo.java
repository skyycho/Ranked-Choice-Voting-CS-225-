import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Demonstrates JButton, JSlider, JComboBox and JTextField.  This program doesn't
 * really do anything interesting beyond showing how to use these Swing components.
 * 
 */
public class SwingDemo 
{
    /* Note the "implements" phrase above.  We need to implement ActionListener in order
     * to handle events associated with the JButton, the JComboBox and the JTextField.  We
     * need to implement ChangeListener to handle the events associated with the JSlider.
     */

    // The last text item created.
    private JLabel lastText;
    
    // The button the user can click to insert text into the canvas.
    private JButton clickButton;
    
    // A menu that the user can use to select text to display.
    private JComboBox menu;
    
    // A slider that is used to position the text.
    private JSlider numSlider;
    
    // A field where the user can enter text to display.
    private JTextField name;
    
    // A checkbox the user can click to select or deselect
    private JCheckBox box;
    
    /**
     * Create the display.  The Swing components are arranged around objectdraw's canvas.
     */
    public SwingDemo() {
        JFrame f = new JFrame();
        
        // Create a text field large enough to hold 2 characters.
        name = new JTextField (20);
        
        // Create a checkbox with a label describing it.
        box = new JCheckBox();
        JLabel label = new JLabel ("Bold face");
        
        // Put the text field, check box and label in the same panel
        JPanel northPanel = new JPanel();
        northPanel.add (name);
        northPanel.add(box);
        northPanel.add(label);
        f.add (northPanel, BorderLayout.NORTH);
        
        // Create a vertical slider whose minimum value is 0, maximum value is 100 and
        // initial value is 50.  Place it to the left of the canvas.
        numSlider = new JSlider (JSlider.VERTICAL, 0, 100, 10);
        JPanel westPanel = new JPanel();
        westPanel.add(numSlider);
        f.add(westPanel, BorderLayout.WEST);
        
        // Create the JLabel that will get modified
        lastText = new JLabel("");
        f.add(lastText, BorderLayout.CENTER);
        
        // Create a menu with 3 entries.
        menu = new JComboBox();
        menu.addItem ("alpha");
        menu.addItem ("beta");
        menu.addItem ("gamma");
        
        // Create a button labeled "Click me".
        clickButton = new JButton ("Click me");
        
        // Put the menu and button below the canvas.
        JPanel southPanel = new JPanel();
        southPanel.add (menu);
        southPanel.add (clickButton);
        f.add(southPanel, BorderLayout.SOUTH);
        
        // Tell the 4 Swing components that this class is providing the event handlers
        // for those components.
        name.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
                lastText.setText (name.getText());
            }
        });
        box.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (box.isSelected()) {
                    lastText.setFont (lastText.getFont().deriveFont(Font.BOLD));
                }
                else {
                    lastText.setFont (lastText.getFont().deriveFont(Font.PLAIN));
                }
            }
        });
        clickButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                lastText.setText ("Clicked");
            }
        });
        numSlider.addChangeListener (new ChangeListener() {
            public void stateChanged (ChangeEvent evt) {
                lastText.setFont (lastText.getFont().deriveFont((float) numSlider.getValue()));
            }
        });
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                lastText.setText ((String) menu.getSelectedItem());
            }
        });
        
        f.setSize(400, 400);
        f.setVisible(true);
    }
    
    /**
     * Handle events caused by the user clicking the button, using the enter key in the text
     * field, or selecting a value using the menu.
     * @param evt information about the user action being handled
     */
    public void actionPerformed (ActionEvent evt) {
        // Find out which Swing component the user interacted with
        Object source = evt.getSource();
        
        // When the user clicks on the button.  Create a new text item on the canvas
        // using the value currently selected in the menu and using the slider value
        // to position the text.
        if (source == clickButton) {
            createText (menu.getSelectedItem().toString());
        }
        
        // Make the text bold face if the box is checked.
        else if (source == box) {
     //       lastText.setBold (box.isSelected());
        }
        
        // When the user types the enter key in the text field, create a new text item
        // using the value in the field and using the slider value to position the text.
        else if (source == name) {
            createText (name.getText());    
        }
        
        // When the user changes the menu selection, change the text of the previous text 
        // item created.  If none has been created yet, do nothing.
        else if (lastText != null) {
     //       lastText.setText (menu.getSelectedItem());
        }
    }
    
    /**
     * Create a new text item positioning it using the slider value.
     * @param value the text to display.
     */
    private void createText (String value) {
//        int percent = 100 - numSlider.getValue();
        //lastText = new Text (value, canvas.getWidth() * percent / 100, canvas.getHeight() * percent / 100, canvas);    
    }
    
    /**
     * When the user moves the slider, move the last text item.  If no text has been
     * created yet, do nothing.
     * @param evt Information about the user's interaction with the slider.
     */
    public void stateChanged (ChangeEvent evt) {
        if (lastText != null) {
            int percent = 100 - numSlider.getValue();
     //       lastText.moveTo (canvas.getWidth() * percent / 100, canvas.getHeight() * percent / 100);
        }
    }
    
    public static void main(String[] args) {
        new SwingDemo();
    }
}
