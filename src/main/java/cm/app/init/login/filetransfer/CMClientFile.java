package cm.app.init.login.filetransfer;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;

/**
 * A simple CM client application to test client-client file transfer
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp).
 * <br>Check that the SERVER_ADDR field of the cm-client.conf file is the same as that of
 * the cm-server.conf file.
 * <br>In cm-server.conf and cm-client.conf, enable the PERMIT_FILE_TRANSFER field by setting to 1 for
 * the simplicity of the file transfer management of CM.
 * <br>Before you run this client, you should also run another file-transfer client
 * (CMClientFilePush or CMClientFilePull) at a different device.
 * <br>1. The client logs in to the server with the user name "mlim".
 * <br>2. If you press the enter key, CM and the client terminates.
 */
public class CMClientFile {
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
        System.out.println("user name: mlim");
        System.out.println("password: mlim");
        ret = clientStub.loginCM("mlim", "mlim");

        if(ret)
            System.out.println("successfully sent the login request.");
        else {
            System.err.println("failed the login request!");
            return;
        }

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();
    }
}
