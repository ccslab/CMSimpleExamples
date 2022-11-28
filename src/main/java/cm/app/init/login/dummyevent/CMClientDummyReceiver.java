package cm.app.init.login.dummyevent;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;
/**
 * A simple CM client application to test receiving dummy events
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp) and a sender client (CMClientDummySender).
 * <br>After you run this client,
 * <br>1. the client logs in to the server with the user name "mlim".
 * <br>2. If you press the enter key, the client prints out current session and group names.
 * <br>3. Whenever the client receives a dummy event from the sender, it prints out the received event.
 * <br>4. If you press the enter key, CM and the client terminates.
 */
public class CMClientDummyReceiver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CMClientApp client = new CMClientApp();
        CMClientStub clientStub = client.getClientStub();
        CMClientEventHandler eventHandler = client.getClientEventHandler();
        boolean ret = false;

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
        System.out.println("user name: mlim");
        System.out.println("password: mlim");
        ret = clientStub.loginCM("mlim", "mlim");

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

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();
    }
}
