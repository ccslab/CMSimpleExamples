package cm.app.init.login.dummyevent;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;
/**
 * A simple CM client application to test sending dummy events
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp).
 * <br>After you run this client, you can also run another receiver client (CMClientDummyReceiver).
 * <br>1. The client logs in to the server with the user name "ccslab".
 * <br>2. If you press the enter key, you can test to send dummy events to the current group.
 * <br>4. If you type "quit" in the test loop, CM and the client terminates.
 */
public class CMClientDummySender {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CMClientApp client = new CMClientApp();
        CMClientStub clientStub = client.getClientStub();
        CMClientEventHandler eventHandler = client.getClientEventHandler();
        boolean ret;

        // initialize CM
        clientStub.setAppEventHandler(eventHandler);
        ret = clientStub.startCM();

        if(ret)
            System.out.println("CM initialization succeeds.");
        else {
            System.err.println("CM initialization error!");
            return;
        }

        // login CM server
        System.out.println("=== login: ");
        System.out.println("user name: ccslab");
        System.out.println("password: ccslab");
        ret = clientStub.loginCM("ccslab", "ccslab");

        if(ret)
            System.out.println("successfully sent the login request.");
        else {
            System.err.println("failed the login request!");
            return;
        }

        // wait before executing next API
        System.out.println("Press enter to execute next API:");
        scanner.nextLine();

        // get myself info
        CMUser myself = clientStub.getMyself();
        System.out.println("current session ("+myself.getCurrentSession()+"), current group: ("
                +myself.getCurrentGroup()+")");

        // send dummy event
        System.out.println("====== send dummy event to default group");
        System.out.println("(Type \"quit\" to exit the program.)");
        String strMessage;
        while(true) {
            System.out.println("message: ");
            strMessage = scanner.nextLine().trim();
            if(strMessage.equals("quit")) break;

            // create a dummy event
            CMDummyEvent due = new CMDummyEvent();
            due.setDummyInfo(strMessage);
            clientStub.cast(due, myself.getCurrentSession(), myself.getCurrentGroup());
        }

        // terminate CM
        clientStub.terminateCM();
    }
}
