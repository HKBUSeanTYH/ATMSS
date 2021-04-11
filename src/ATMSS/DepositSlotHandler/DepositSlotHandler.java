package ATMSS.DepositSlotHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;

public class DepositSlotHandler extends HWHandler {
    private static boolean operate = true;

    public DepositSlotHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    } // DepositSlotHandler

    protected void processMsg(Msg msg) {			//this processMsg is for that of DepositSlotMBox (mbox) <== inheritance property
    												//From EmulatorController send to MBox, process it here, then send to atmssMBox
        if (operate) {
            switch (msg.getType()) {                    //Sean: change msg types to ones i need (dont forget to add to enums type in Msg Class!!)
                case Denom_sum:
                    atmss.send(new Msg(id, mbox, Msg.Type.Denom_sum, msg.getDetails()));
                    handleDeposit("CloseSlot");
                    break;
                case Deposit:
                    handleDeposit(msg.getDetails());
                    break;

                case Alert:
                    alert();
                    break;

                default:
                    log.warning(id + ": unknown message type: [" + msg + "]");
            }
        }
    } // processMsg


    //------------------------------------------------------------
    // handleDepositCash
    protected void handleDepositCash() {
        log.info(id + ": cash deposited");
    } // handleCardInsert

    //------------------------------------------------------------
    protected void handleDeposit(String msg) {
    	if (msg.equals("OpenSlot")) {
    		log.info(id + "Opening Deposit Slot");			//atmss sends open command
    	}else if (msg.equals("CloseSlot")) {
    		log.info(id + "Closing Deposit Slot");			//emulator or the hardware sends the close command
    	}
    }

    //------------------------------------------------------------
    protected void alert() {
        log.info(id + ": alert user-- ");
    }

    protected void shutdown() {
        super.shutdown();
        operate = false;
    }

    protected void reset() {
        super.reset();
        operate = true;
    }
}
