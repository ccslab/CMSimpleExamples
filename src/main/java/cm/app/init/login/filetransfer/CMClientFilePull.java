package cm.app.init.login.filetransfer;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;

/**
 * A simple CM client application to test pulling a file
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp).
 * <br>Check that the SERVER_ADDR field of the cm-client.conf file is the same as that of
 * the cm-server.conf file.
 * <br>In cm-server.conf and cm-client.conf, enable the PERMIT_FILE_TRANSFER field by setting to 1 for
 * the simplicity of the file transfer management of CM.
 * <br>After you run this client, you can also run another receiver client (CMClientFile) at
 * a different device if you want to receive files from the client.
 * <br>1. The client logs in to the server with the user name "ccslab".
 * <br>2. If you press the enter key, the client asks you to type a file name to be received.
 * <br>3. The client also asks you to type a file owner: "SERVER" for the server or "mlim"
 * for the owner client. The owner should have the requested file in the file-transfer home directory.
 * <br>4. If you press the enter key, CM and the client terminates.
 */
public class CMClientFilePull {
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

        // input file name
        System.out.print("Type file name: ");
        String fileName = scanner.nextLine().trim();
        // input file owner
        System.out.println("Type file owner: ");
        System.out.println("Type \"SERVER\" for the server or \"mlim\" for client owner.");
        System.out.println("For \"mlim\", you must run CMClientFile before the file transfer.");
        System.out.println("The file owner should have the requested file in the file-transfer home.");
        String fileOwner = scanner.nextLine().trim();

        // request file
        clientStub.requestFile(fileName, fileOwner);

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();
    }
}
