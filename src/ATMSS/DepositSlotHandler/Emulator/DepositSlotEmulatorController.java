package ATMSS.DepositSlotHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DepositSlotEmulatorController {

    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private DepositSlotEmulator DepositSlotEmulator;
    private MBox DepositSlotMBox;
    
    private boolean deposit = false;
    private int denom100 = 0;
    private int denom500 = 0;
    private int denom1000 = 0;

    public TextField amtField;
    public TextField DepositSlotStatusField;
    public TextArea DepositSlotTextArea;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, DepositSlotEmulator DepositSlotEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.DepositSlotEmulator = DepositSlotEmulator;
        this.DepositSlotMBox = appKickstarter.getThread("DepositSlotHandler").getMBox();
    } // initialize
    
    public void buttonPressed(ActionEvent actionEvent) {
    	//probability of unverified money notes
		//detect unverified money notes
    	Button btn = (Button) actionEvent.getSource();

    	switch (btn.getText()) {
    	    case "$ 100":
    	    	denom100 = denom100+1;
    	    	amtField.setText(""+(denom100*100 + denom500*500+denom1000*1000));
    	        break;

    	    case "$ 500":
	    	    denom500 = denom500+1;
	    	    amtField.setText(""+(denom100*100 + denom500*500+denom1000*1000));
	    		break;

    	    case "$ 1000":
	    	    denom1000 = denom1000+1;
	    	    amtField.setText(""+(denom100*100 + denom500*500+denom1000*1000));
	    		break;
	    		
    	    case "Deposit":
    	    	deposit(false);
    	    break;
    	    
    	    //handle remove
    	    case "Remove":				//this "removes" cash from amtField so you can choose the appropriate denominations
    	    	if (amtField.getText().length() != 0 ) {
    	    		amtField.setText("");
        	    	denom100 = denom500 = denom1000 = 0;
    	    	}
    		break;

    	    default:
    	        log.warning(id + ": unknown button: [" + btn.getText() + "]");
    		break;
    	}
    } // buttonPressed
    
    //------------------------------------------------------------
    // updateCardStatus
    public void updateCardStatus(String status) {
    	DepositSlotStatusField.setText(status);
    } // updateCardStatus
    
    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
    	DepositSlotTextArea.appendText(status+"\n");
    } // appendTextArea
    
    public void setTransactionStatus(String msg) {
    	if(msg.equals("CloseSlot")) {
    		deposit = false;
    	}else if (msg.equals("OpenSlot")){
    		deposit = true;
    	}
    }

    boolean deposit(boolean diagnostic) {
		if (deposit && (amtField.getText().length() != 0 )) {
			DepositSlotTextArea.appendText("Depositing " + amtField.getText()+"\n");
			amtField.setText("");
			if (!diagnostic) {
				DepositSlotMBox.send(new Msg(id, DepositSlotMBox, Msg.Type.Denom_sum, (""+denom100+" "+denom500+" "+denom1000))); //cardNumField.getText()
			}
			DepositSlotMBox.send(new Msg(id, DepositSlotMBox, Msg.Type.Deposit, ("CloseSlot")));		//when finish deposit close the slot
			denom100 = denom500 = denom1000 = 0;
			return true;
		}else {
			DepositSlotTextArea.appendText("Slot not open! Unable to deposit!\n");
			return false;
		}
	}
    
}
