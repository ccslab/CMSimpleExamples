package cm.app.init.login;

import kr.ac.konkuk.ccslab.cm.event.*;
import kr.ac.konkuk.ccslab.cm.event.filesync.CMFileSyncEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class CMClientEventHandler implements CMAppEventHandler {
    private CMClientStub clientStub;

    public CMClientEventHandler(CMClientStub clientStub) {
        this.clientStub = clientStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
		switch(cme.getType())
		{
			case CMInfo.CM_SESSION_EVENT:
				processSessionEvent(cme);
				break;
			case CMInfo.CM_DATA_EVENT:
				processDataEvent(cme);
				break;
			case CMInfo.CM_INTEREST_EVENT:
				processInterestEvent(cme);
				break;
			case CMInfo.CM_DUMMY_EVENT:
				processDummyEvent(cme);
				break;
			case CMInfo.CM_USER_EVENT:
				processUserEvent(cme);
				break;
			case CMInfo.CM_FILE_EVENT:
				processFileEvent(cme);
				break;
			case CMInfo.CM_FILE_SYNC_EVENT:
				processFileSyncEvent(cme);
				break;
			default:
				break;
		}
    }

	private void processFileSyncEvent(CMEvent cme) {
		CMFileSyncEvent fse = (CMFileSyncEvent)cme;
		switch(fse.getID()) {
			case CMFileSyncEvent.COMPLETE_FILE_SYNC:
				System.out.println("--> The file sync completes.");
				break;
			default:
				break;
		}
	}

	private void processSessionEvent(CMEvent cme) {
		CMSessionEvent se = (CMSessionEvent)cme;
		switch(se.getID())
		{
		case CMSessionEvent.LOGIN_ACK:
			if(se.isValidUser() == 0)
			{
				System.err.println("--> This client fails authentication by the default server!");
			}
			else if(se.isValidUser() == -1)
			{
				System.err.println("--> This client is already in the login-user list!");
			}
			else
			{
				System.out.println("--> This client successfully logs in to the default server.");
			}
			break;
		default:
			break;
		}
	}

	private void processDataEvent(CMEvent cme) {
		CMDataEvent de = (CMDataEvent) cme;
		switch(de.getID())
		{
			case CMDataEvent.NEW_USER:
				System.out.println("--> ["+de.getUserName()+"] enters group("+de.getHandlerGroup()+") in session("
						+de.getHandlerSession()+").");
				break;
			case CMDataEvent.REMOVE_USER:
				System.out.println("--> ["+de.getUserName()+"] leaves group("+de.getHandlerGroup()+") in session("
						+de.getHandlerSession()+").");
				break;
			default:
				return;
		}
	}

	private void processInterestEvent(CMEvent cme) {
		CMInterestEvent ie = (CMInterestEvent) cme;
		switch(ie.getID())
		{
			case CMInterestEvent.USER_TALK:
				System.out.println("--> <"+ie.getUserName()+">: "+ie.getTalk());
				break;
			default:
				return;
		}
	}

	private void processDummyEvent(CMEvent cme) {
		CMDummyEvent due = (CMDummyEvent) cme;
		System.out.println("--> dummy msg ["+due.getSender()+"]: "+due.getDummyInfo());
		return;
	}

	private void processUserEvent(CMEvent cme) {
		CMUserEvent ue = (CMUserEvent) cme;
		switch(ue.getStringID()) {
			case "userInfo":
				System.out.println("--> user event ID: "+ue.getStringID());
				String name = ue.getEventField(CMInfo.CM_STR, "name");
				int age = Integer.parseInt(ue.getEventField(CMInfo.CM_INT, "age"));
				double weight = Double.parseDouble(ue.getEventField(CMInfo.CM_DOUBLE, "weight"));
				System.out.println("--> name: "+name);
				System.out.println("--> age: "+age);
				System.out.println("--> weight: "+weight);
				break;
			default:
				System.err.println("--> unknown CMUserEvent ID: "+ue.getStringID());
		}
	}

	private void processFileEvent(CMEvent cme) {
		CMFileEvent fe = (CMFileEvent)cme;
		switch(fe.getID()) {
			case CMFileEvent.END_FILE_TRANSFER:
			case CMFileEvent.END_FILE_TRANSFER_CHAN:
				System.out.println("--> ["+fe.getFileSender()+"] completes to send file("
						+fe.getFileName()+", "+fe.getFileSize()+" Bytes) to ["
						+fe.getFileReceiver()+"]");
				break;
			case CMFileEvent.END_FILE_TRANSFER_ACK:
			case CMFileEvent.END_FILE_TRANSFER_CHAN_ACK:
				System.out.println("--> ["+fe.getFileReceiver()+"] completes to receive file("
						+fe.getFileName()+", "+fe.getFileSize()+" Bytes) from ["
						+fe.getFileSender()+"]");
				break;
			default:
				break;
		}
	}

}
