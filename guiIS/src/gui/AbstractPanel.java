package gui;


import javax.swing.*;

public abstract class AbstractPanel extends JPanel {

    private final long serialVersionUID;
    private final String LINKDESCRIPTION;
    private final LaTazzaFrame.JPanelsNames PANELNAME;


    protected AbstractPanel(long serialVersionUID, String linkdescription, LaTazzaFrame.JPanelsNames panelname) {
        this.serialVersionUID = serialVersionUID;
        LINKDESCRIPTION = linkdescription;
        PANELNAME = panelname;
    }
}
