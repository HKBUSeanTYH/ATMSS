package ATMSS.DispenserSlotHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;

public class DispenserSlotHandler extends HWHandler {
    private static boolean operate = true;

    public DispenserSlotHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    } // DepositSlotHandler

    protected void processMsg(Msg msg) {            //this processMsg is for that of DepositSlotMBox (mbox) <== inheritance property
        //From EmulatorController send to MBox, process it here, then send to atmssMBox
        if (operate) {
            switch (msg.getType()) {
                case Denom_sum:            //use this to represent the denominations for dispenser to dispense
                    handleDispenseCash(msg.getDetails());
                    break;
                case Dispense:   //msg type to open or close relevant slot (indicates start or end of withdraw transaction)
                    handleDispense(msg.getDetails());
                    break;

                case DispenseFinish:
                    //send msg to touch display and stop timer
                    atmss.send(new Msg(id, mbox, Msg.Type.DispenseFinish, msg.getDetails()));
                    break;

                case DenomsInventoryUpdate:
                    handleDenomsUpdate(msg.getDetails());
                    break;

                case DenomsInventoryCheck:
                    if (msg.getDetails().equals("")) {
                        handleDenomsInventoryCheck();
                    } else {
                        atmss.send(new Msg(id, mbox, Msg.Type.DenomsInventoryCheck, msg.getDetails()));
                    }
                    break;

                case Error:
                    atmss.send(new Msg(id, mbox, Msg.Type.Error, msg.getDetails()));
                    break;

                default:
                    log.warning(id + ": unknown message type: [" + msg + "]");
            }
        }
    }

    protected void handleDispenseCash(String denoms) {
        log.info(id + ": cash dispensed");
    } // handleCardInsert

    protected void handleDispense(String msg) {
        if (msg.equals("OpenSlot")) {
            log.info(id + ": Opening Dispenser Slot");            //atmss sends open command
        } else if (msg.equals("CloseSlot")) {
            log.info(id + ": Closing Dispenser Slot");            //emulator or the hardware sends the close command
        }
    }

    protected void handleDenomsUpdate(String details) {
        log.info(id + ": denoms increase: " + details);
    }

    protected void handleDenomsInventoryCheck() {
        log.info(id + ": denoms inventory check");
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
