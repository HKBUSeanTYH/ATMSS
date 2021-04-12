package ATMSS.ErrorSimulatorHandler;

import ATMSS.ATMSSStarter;
import ATMSS.HWHandler.HWHandler;
import AppKickstarter.misc.Msg;

public class ErrorSimulatorHandler extends HWHandler {
    public ErrorSimulatorHandler(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
    }

    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case Error:
                processError(msg.getDetails());
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    public void processError(String msg){
        String[] stringToken = msg.split(" ");

        if (stringToken.length < 2 ){
            log.info(id + ": incorrect simulation length" + msg);
            return;
        }

        String token = "";
        //basically if length is 2 it does nothing
        //but if length is 3 or more, we can concatenate the tokens to get token
        for (int i=2; i<stringToken.length; i++){
            if (token.equals("")){
                token = stringToken[i];
            }else{
                token = token+" "+stringToken[i];
            }
        }

        switch(stringToken[0]){
            case "Error":
                switch(stringToken[1].trim()){
                    case "AdvicePrinterHandler":
                    case "BuzzerHandler":
                    case "BAMSThreadHandler":
                    case "DepositSlotHandler":
                    case "DispenserSlotHandler":
                    case "KeypadHandler":
                    case "CardReaderHandler":
                    case "TouchDisplayHandler":
                        log.info(id+" : simulating an error");
                        atmss.send(new Msg(stringToken[1], mbox, Msg.Type.Error, token));
                        break;

                    default:
                        log.info(id+ " :Error Simulator does not recognize second keyword! -- "+msg);
                }
                break;
//            case "Reset":
//                break;
//            case "Shutdown":
//                if (stringToken[1].equals("ALL")){
//                    atmss.send(new Msg(id, mbox, Msg.Type.ShutdownReq, ""));
//                }else{
//                    log.info(id+ " :Error Simulator does not recognize second keyword!");
//                }
//                break;
            default:
                log.info(id+ " : Error Simulator does not recognize first keyword!");
        }
    }

//    protected void alert(String msg) {
//        log.info(id + ": alert -- " + msg);
//    }
}
