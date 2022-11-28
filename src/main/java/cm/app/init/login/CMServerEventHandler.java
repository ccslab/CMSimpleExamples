package cm.app.init.login;

import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class CMServerEventHandler implements CMAppEventHandler {
    private CMServerStub m_serverStub;

    public CMServerEventHandler(CMServerStub serverStub) {
        m_serverStub = serverStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
        switch(cme.getType()) {
            case CMInfo.CM_SESSION_EVENT:
                processSessionEvent(cme);
                break;
            case CMInfo.CM_USER_EVENT:
                processUserEvent(cme);
                break;
            default:
                break;
        }
    }

    private void processSessionEvent(CMEvent cme) {
        CMSessionEvent se = (CMSessionEvent) cme;
        switch(se.getID()) {
            case CMSessionEvent.LOGIN:
                System.out.println("--> ["+se.getUserName()+"] requests login.");
                break;
            default:
                return;
        }
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
}
