package cm.app.init.login.userevent;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;

/**
 * A simple CM client application to test sending a user event
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp).
 * <br>After you run this client,
 * <br>1. The client logs in to the server with the user name "ccslab".
 * <br>2. If you press the enter key, the client sends a user event to the server.
 * Then, the server prints out the received user event.
 * <br>4. If you press the enter key, CM and the client terminates.
 */
public class CMClientUserEventSender {
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

        // create a user event
        CMUserEvent ue = new CMUserEvent();
        ue.setStringID("userInfo");
        String name = "ccslab";
        int age = 10;
        double weight = 2.7;
        ue.setEventField(CMInfo.CM_STR, "name", name);
        ue.setEventField(CMInfo.CM_INT, "age", String.valueOf(age));
        ue.setEventField(CMInfo.CM_DOUBLE, "weight", String.valueOf(weight));
        System.out.println("user event ID: "+ue.getStringID());
        System.out.println("Type: CMInfo.CM_STR, Field name: name, Value: "+name);
        System.out.println("Type: CMInfo.CM_INT, Field name: age, Value: "+age);
        System.out.println("Type: CMInfo.CM_DOUBLE, Field name: weight, Value: "+weight);
        clientStub.send(ue, "SERVER");

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();

    }
}
