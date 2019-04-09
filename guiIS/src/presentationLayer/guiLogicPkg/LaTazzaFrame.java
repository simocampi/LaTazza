package presentationLayer.guiLogicPkg;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.*;
//import com.apple.eawt.Application;//todo qui non so se va bene è una lib di apple
import presentationLayer.guiConfig.structurePanelsPropertiesPkg.ContentPaneProperties;
import presentationLayer.guiConfig.structurePanelsPropertiesPkg.LaTazzaFrameProperties;
import presentationLayer.guiConfig.structurePanelsPropertiesPkg.MenuPaneProperties;
import presentationLayer.guiLogicPkg.contentsPanelsPkg.*;
import presentationLayer.guiLogicPkg.structurePanelsPkg.MenuPane;
import presentationLayer.guiLogicPkg.structurePanelsPkg.TopBarPane;


public class LaTazzaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
    private LaTazzaFrameProperties laTazzaFrameProperties=new LaTazzaFrameProperties();

    private ContentPane contentPane;
    private MenuPane menuPane;
    private TopBarPane topBarPanePane=new TopBarPane(this);

    public  enum JPanelsNames{
        STATOPANE,
        REGVENDITEPANE,
        REGPAGAMENTOPANE,
        REGRIFORNIMENTOPANE,
        GESTIONEPERSONALEPANE

    }

	private Map<JPanelsNames,AbstractPanel> jPanelsMap =new EnumMap<>(JPanelsNames.class);

	/**
	 * Create the frame.
	 */
	public LaTazzaFrame() {

	    this.contentPane=new ContentPane(this);
        ContentPaneProperties.initContentPane(contentPane);

        //Application.getApplication().setDockIconImage((ResourcesClassLoader.getIconTazzaBrown()).getImage());

        this.setContentPane(contentPane);
        this.add(topBarPanePane);

		//todo check return value
        //inizializza i pannelli e li aggiune alla jPanelsMap
		jPanelsMap.put(JPanelsNames.STATOPANE,new StatoPane());
		jPanelsMap.put(JPanelsNames.REGVENDITEPANE,new RegistraVendite());
		jPanelsMap.put(JPanelsNames.REGPAGAMENTOPANE,new RegistraPagamento());
		jPanelsMap.put(JPanelsNames.REGRIFORNIMENTOPANE,new RegistraRifornimento());
		jPanelsMap.put(JPanelsNames.GESTIONEPERSONALEPANE,new GestionePersonale());

        jPanelsMap.forEach((k,v)->this.add(v));//aggiunge tutti i pannelli al frame

        menuPane=new MenuPane(this);//va lasciato per ultimo perchè devono essere init prima i contentpanes
        MenuPaneProperties.initPanel(menuPane);
        this.add(menuPane);
	}


    /**
     * Questo metodo peremette di settare la visibilità del pannello con nome panelName.
     * @param panelName il nome del pannello da settare come visibile
     * @param panelVisibilityState lo stato (true/false) della visibilità del pannello
     */
	public void setJPanelVisibleState(JPanelsNames panelName,boolean panelVisibilityState){
	    jPanelsMap.get(panelName).setVisible(panelVisibilityState);
	    
    }

    public AbstractPanel getPanelByName(JPanelsNames name){
	    return jPanelsMap.get(name);
    }


    public void setLocationCenter() {

        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - getWidth()) / 2;
        int iCoordY = (objDimension.height - getHeight()) / 2;
        setLocation(iCoordX, iCoordY);

    }

}
