package cm.app.init.login.chat;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;

/**
 * A simple CM client application to test chat messages
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp).
 * <br>Check that the SERVER_ADDR field of the cm-client.conf file is the same as that of
 * the cm-server.conf file.
 * <br>After you run this client, you can also run another client (CMClientChat2).
 * <br>1. the client logs in to the server with the user name "ccslab".
 * <br>2. If you press the enter key, you can test to send chat messages to the specified target.
 * <br>3. If another client sends a chat message, this client prints out the received message.
 * <br>4. If you type "quit" in the chat test loop, CM and the client terminates.
 */
public class CMClientChat1 {
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

        // send chat message
        System.out.println("====== chat");
        System.out.println("(Type \"quit\" to exit the program.)");
        String strTarget;
        String strMessage;
        while(true) {
            System.out.print("target(/b, /s, /g, or /username): ");
            strTarget = scanner.nextLine().trim();
            if(strTarget.equals("quit")) break;
            System.out.print("message: ");
            strMessage = scanner.nextLine().trim();
            if(strMessage.equals("quit")) break;
            clientStub.chat(strTarget, strMessage);
        }

        // terminate CM
        clientStub.terminateCM();
    }
}
