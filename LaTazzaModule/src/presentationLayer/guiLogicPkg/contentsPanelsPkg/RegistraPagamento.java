package presentationLayer.guiLogicPkg.contentsPanelsPkg;

import javax.swing.*;

import businessLogicLayer.ControllerPersonale;
import businessLogicLayer.commandPkg.PagamentoDebitoCommand;
import dataAccessLayer.rowdatapkg.clientPkg.Personale;
import presentationLayer.guiConfig.contentsPanelsPropertiesPkg.RegPagamentoProperties;
import presentationLayer.LaTazzaApplication;
import utils.Euro;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import static presentationLayer.guiConfig.contentsPanelsPropertiesPkg.RegPagamentoProperties.*;
import static businessLogicLayer.ObserverSubscriptionType.PERSONALELIST;


public class RegistraPagamento extends AbstractPanel {

    private JButton buttonConferma;
    private JButton buttonAnnulla;
    private JComboBox<String> nomePersonaleMenu;
    private JFormattedTextField textFieldAmmontare;
    private JLabel labelTitolo;
    private JLabel labelNomePersonale;
    private JLabel labelAmmontare;


	public RegistraPagamento() {

		super(1L,DEFAULT_LINKDESCRIPTION,DEFAULT_PANELNAME);
		RegPagamentoProperties.initRegistraPagamentoPanel(this);
		add(labelTitolo=RegPagamentoProperties.createAndInitLabelTitolo());
		add(labelNomePersonale=RegPagamentoProperties.createAndInitLabelNomePersonale());
		add(nomePersonaleMenu=RegPagamentoProperties.createAndInitJComboBoxNomePersonaleMenu());
		add(labelAmmontare=RegPagamentoProperties.createAndInitLabelAmmonatare());
		add(textFieldAmmontare=RegPagamentoProperties.createAndInitJFormattedTextFieldAmmontare());
		add(buttonConferma=RegPagamentoProperties.createAndInitJButtonConferma());
		add(buttonAnnulla=RegPagamentoProperties.createAndInitJButtonAnnulla());
		buttonAnnulla.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						annulla();
					}
				}
		);

		buttonConferma.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						conferma();
					}
				}
		);

		LaTazzaApplication.backEndInvoker.addObserver(PERSONALELIST,this);

	}


    private void setComboBoxNomePersonale(List<Personale> lista){
		nomePersonaleMenu.removeAllItems();

		for(Personale i:lista){
            this.nomePersonaleMenu.addItem(
                    i.getNome()+" "+i.getCognome()
            );
        }
    }


	private void annulla()
    {
        nomePersonaleMenu.setSelectedIndex(0);
        textFieldAmmontare.setValue(null);
    }

    private void conferma(){

		Euro importo;
		long euro;
		int cent;
		String value= textFieldAmmontare.getText().replaceAll("\\'",""), strCent=null;
		String personale= (String) nomePersonaleMenu.getSelectedItem();
		String[] nomeCognome;

        if(value.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "Inserire un valore per il pagamento", "warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if( personale==null){
            JOptionPane.showMessageDialog(null,
                    "Personale selezionato non valido", "warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        nomeCognome= personale.split(" ");

        try {
			if (value.contains(".")) {
				String[] euroCent = value.split("\\.");
				euro = Long.valueOf(euroCent[0]);
				strCent = euroCent[1];
				if (euroCent[1].length() == 1) strCent = strCent.concat("0");
				cent = Integer.valueOf(strCent);
				importo = new Euro(euro, cent);
			} else {
				euro = Long.valueOf(value);
				importo = new Euro(euro);
			}
		}catch (IllegalArgumentException e){
			JOptionPane.showMessageDialog(null,
					"Inserire quantità euro positiva.", "error", JOptionPane.ERROR_MESSAGE);
			annulla();
			return;
		}
		PagamentoDebitoCommand command= new PagamentoDebitoCommand(nomeCognome[0],nomeCognome[1],importo,LaTazzaApplication.backEndInvoker);


		switch (LaTazzaApplication.backEndInvoker.executeCommand(command)){
			case IMPORTOMAGGIOREDIDEBITO:
				JOptionPane.showMessageDialog(null,
						"Debito totale minore dell'importo.", "alert", JOptionPane.WARNING_MESSAGE);
				break;
			case NOERROR:
				JOptionPane.showMessageDialog(null,
						"Debito pagato correttamente.", "success", JOptionPane.INFORMATION_MESSAGE);
				break;
			default:
				JOptionPane.showMessageDialog(null,
						"Impossibile pagare debito", "alert", JOptionPane.ERROR_MESSAGE);
				break;

		}
		annulla();

	}

    @Override
    public void update(Observable o, Object arg) {
        if(arg ==PERSONALELIST){
            setComboBoxNomePersonale(((ControllerPersonale)o).getCopyList());
        }
    }
}
