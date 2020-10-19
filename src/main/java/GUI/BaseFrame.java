package GUI;

import javax.swing.*;

public abstract class BaseFrame extends JFrame {
    public BaseFrame(){
        super();
        initialize();
    }
    abstract void initialize();
    public void showFrame(){
        pack();
        setVisible(true);
    }
}
