package ATMSS.CardReaderHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// CardReaderHandler
public class CardReaderHandler extends HWHandler {
    private static boolean operate = true;

    //------------------------------------------------------------
    // CardReaderHandler
    public CardReaderHandler(String id, AppKickstarter appKickstarter) {
	super(id, appKickstarter);
    } // CardReaderHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        if (operate) {
            switch (msg.getType()) {
                case CR_CardInserted:
                    atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                    handleCardInsert();
                    break;

                case CR_EjectCard:      //receive msg from ATMSS (write in ATMSS a method to send CR_ejectcard msg to card handler)
                    handleCardEject();
                    break;

                case CR_CardRemoved:
                    handleCardRemove();
                    break;

                case CR_RetainCard:
                    handleCardRetain();
                    break;

                default:
                    log.warning(id + ": unknown message type: [" + msg + "]");
            }
        }
    } // processMsg


    //------------------------------------------------------------
    // handleCardInsert
    protected void handleCardInsert() {
	log.info(id + ": card inserted");
    } // handleCardInsert


    //------------------------------------------------------------
    // handleCardEject
    protected void handleCardEject() {
	log.info(id + ": card ejected");
    } // handleCardEject


    //------------------------------------------------------------
    // handleCardRemove
    protected void handleCardRemove() {
	log.info(id + ": card removed");
    } // handleCardRemove

    protected void handleCardRetain() {
        log.info(id + ": card retained");
    } // handleCardRetain

    protected void shutdown() {
        super.shutdown();
        operate = false;
    }

    protected void reset() {
        super.reset();
        operate = true;
    }

} // CardReaderHandler
