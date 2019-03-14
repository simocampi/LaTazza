package guiLogicPkg.structurePanelsPkg;

import java.awt.Color;
import javax.swing.JPanel;
import guiConfig.KGradientPanel;
import guiConfig.ResourcesClassLoader;
import guiConfig.structurePanelsPropertiesPkg.MenuPaneProperties;
import guiLogicPkg.LaTazzaFrame;
import utils.LaTazzaColors;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import static guiConfig.structurePanelsPropertiesPkg.MenuPaneProperties.*;
import static guiConfig.structurePanelsPropertiesPkg.RowPanelProperties.*;


public class MenuPane extends KGradientPanel {

    private static final long serialVersionUID = 1L;
    private final LaTazzaFrame laTazzaFrame;
    private Map<LaTazzaFrame.JPanelsNames, RowPanelLink> linkMap=new HashMap<>();


    private JPanel panelSeparator=new JPanel();
    private JLabel labelTitolo=new JLabel("LaTazza");
    private JLabel labelIconaTazza=new JLabel();





    //create the Menu Panel that contains five links
	public MenuPane(LaTazzaFrame laTazzaFrame) {

		this.laTazzaFrame = laTazzaFrame;

		setBounds(MenuPaneProperties.DEFAULTX,MenuPaneProperties.DEFAULTY,MenuPaneProperties.DEFAULT_WIDTH,MenuPaneProperties.DEFAULT_HEIGHT);
		setLayout(null);
		setkEndColor(LaTazzaColors.CAFFE);
		setkStartColor(LaTazzaColors.CAPPUCCINO);
		//setkGradientFocus(menuPaneProperties.getGradient());
		






        panelSeparator.setBounds(DEFAULTX_SEPARATOR, DEFAULTY_SEPARATOR, DEFAULT_WIDTH_SEPARATOR, DEFAULT_HEIGHT_SEPARATOR);
		panelSeparator.setBackground(Color.WHITE);

		labelTitolo.setFont(new Font("Tahoma", Font.BOLD, 25));
		labelTitolo.setBounds(DEFAULTX_TITOLO, DEFAULTY_TITOLO, DEFAULT_WIDTH_TITOLO, DEFAULT_HEIGHT_TITOLO);

		labelIconaTazza.setBounds(DEFAULTX_ICONATITOLO, DEFAULTY_ICONATITOLO, DEFAULT_WIDTH_ICONATITOLO, DEFAULT_HEIGHT_ICONATITOLO);

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));//+" Class path:"+System.getProperty("java.class.path"));


		labelIconaTazza.setIcon(ResourcesClassLoader.getIconTazza());


        this.add(panelSeparator);
        this.add(labelTitolo);
        this.add(labelIconaTazza);


        int j=0;
        for(LaTazzaFrame.JPanelsNames i:LaTazzaFrame.JPanelsNames.values()){
            linkMap.put(i,//todo check return value
                    new RowPanelLink(
                            laTazzaFrame.getPanelByName(i).getLINKDESCRIPTION(),
                            ROWPANEL_DEFAULTX_BUTTON, ROWPANEL_DEFAULTX_ICON,ROWPANEL_DEFAULTY+ROWPANEL_DEFAULT_GAP*j++,ResourcesClassLoader.getIconStatoW25(), ResourcesClassLoader.getIconStatoB25())
                    
            );//todo trovare un modo per passare labels "Stato","Registra Vendita Cialde","Registra pagamento","Registra rifornimento","Getsione perosnale"
            
        }


        RowPanelLink linkTemp;

        for(LaTazzaFrame.JPanelsNames i:LaTazzaFrame.JPanelsNames.values()){

			linkTemp=linkMap.get(i);
            add(linkTemp.getButton());
            add(linkTemp.getIcon());
            linkTemp.getButton().addMouseListener(
                    new MouseAdapter() {
                        private final LaTazzaFrame.JPanelsNames panelName=i;
                        @Override
                        public void mouseClicked(MouseEvent e){
                            setLink(panelName);
                        }
                    }

            );

            if(i.equals(LaTazzaFrame.JPanelsNames.STATOPANE)){//il pannello iniziale è statoPane
                linkTemp.setLinesW();
            }else{
                linkTemp.setLinesB();
            }
        }



	}


    /**
     * Questo metodo permette di evidenziare di bianco il RowPanelLink con il nome corrsipondente ad lName e
     * colorare di nero tutti gli altri RowPanelLinks.
     * Rende inoltre visibile la "sottopagina" corrispondente al link.
     * @param lName il link da evidenziare
     */
	private void setLink(LaTazzaFrame.JPanelsNames lName){


	    for(LaTazzaFrame.JPanelsNames i:LaTazzaFrame.JPanelsNames.values()){

            if(i.equals(lName)){
                (linkMap.get(i)).setLinesW();
                laTazzaFrame.setJPanelVisibleState(lName,true);
            }else{
                (linkMap.get(i)).setLinesB();
                laTazzaFrame.setJPanelVisibleState(lName,false);
            }
        }

	}


}
