package ATMSS.TouchDisplayHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// TouchDisplayHandler
public class TouchDisplayHandler extends HWHandler {
    private static boolean operate = true;
    //------------------------------------------------------------
    // TouchDisplayHandler
    public TouchDisplayHandler(String id, AppKickstarter appKickstarter) throws Exception {
	super(id, appKickstarter);
    } // TouchDisplayHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        if (operate) {
            switch (msg.getType()) {
                case TD_MouseClicked:
                    atmss.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
                    break;

                case TD_UpdateDisplay:
                    handleUpdateDisplay(msg);
                    break;

                case TD_SelectAccount:
                    accountSelect(msg.getDetails());
                    break;

                case TextTyped:
                    amountFieldChange(msg.getDetails());
                    break;

                case GetAccount:
                    atmss.send(new Msg(id, mbox, Msg.Type.GetAccount, ""));
                    break;

                case Selected_Acc:
                    atmss.send(new Msg(id, mbox, Msg.Type.Selected_Acc, msg.getDetails()));
                    break;

                case DepositResult:
                    DepositResult(msg.getDetails());
                    break;

                case MoneyTransferResult:
                    moneyTransferResult(msg.getDetails());
                    break;

                case Dispense:
                    cashDispense(msg.getDetails());
                    break;

                case DispenseFinish:
                    cashDispenseFinish(msg.getDetails());
                    break;

                case EnquiryResult:
                    accountEnquiry(msg.getDetails());
                    break;

                case Denom_sum:
                    cashDeposit(msg.getDetails());
                    break;

                case LoggedIn:
                    if (msg.getDetails().equals("Success")){
                        handleLogin();
                    }
                    break;

                case Error:
                    handleErrorPage(msg.getDetails());
                    break;

                case ErrorRedirect:
                    atmss.send(new Msg(id, mbox, Msg.Type.ErrorRedirect, msg.getDetails()));
                    break;

                default:
                    log.warning(id + ": unknown message type: [" + msg + "]");
            }
        }
    } // processMsg


    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
        log.info(id + ": update display -- " + msg.getDetails());
    } // handleUpdateDisplay

    protected void handleLogin(){
        log.info(id+": changing login status");
    }

    protected void accountSelect(String acc) {
        log.info(id + ": select account");
    }

    protected void cashDeposit(String amount) {
        log.info(id + ": cash deposit");
    }

    protected void DepositResult(String amount) {
        log.info(id + "Cash Deposit Result");
    }

    protected void moneyTransferResult(String details) {
        log.info(id + "money transfer");
    }

    protected void cashDispense(String amount) {
        log.info(id + "cash dispense");
    }

    protected void cashDispenseFinish(String amount) {
        log.info(id + "cash dispense finish");
    }

    protected void accountEnquiry(String amount) {
        log.info(id + ": account enquiry");
    }

    protected void amountFieldChange(String typed) {
        log.info(id + ": Amount Field Change");
    }

    protected void handleErrorPage(String details) {
        log.info(id + ": Error Message Received: " + details);
    }

    protected void shutdown() {
        super.shutdown();
        operate = false;
    }

    protected void reset() {
        super.reset();
        operate = true;
    }
} // TouchDisplayHandler
