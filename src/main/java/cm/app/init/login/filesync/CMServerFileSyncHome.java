package cm.app.init.login.filesync;

import cm.app.init.login.CMServerApp;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.nio.file.Path;
import java.util.Scanner;
/**
 * A simple CM server application to test file synchronization.
 * <h1>Note</h1>
 * Before you run this server, check SERVER_ADDR field of cm-server.conf.
 * <br>After you run this server, the server starts CM.
 * <br>1. Then, you can run a client app. (CMClientFileSyncXXX)
 * <br>2. If you press the enter key, the server prints out the synchronization home directory of the user "ccslab".
 * <br>3. If you press the enter key, CM and server terminates.
 */
public class CMServerFileSyncHome {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CMServerApp server = new CMServerApp();
        CMServerStub serverStub = server.getServerStub();
        serverStub.setAppEventHandler(server.getServerEventHandler());

        // start CM
        boolean ret = serverStub.startCM();

        if(ret) {
            System.out.println("CM initialization succeeds.");
        }
        else {
            System.err.println("CM initialization error!");
        }

        // wait before executing next API
        System.out.println("Enter to execute next API:");
        scanner.nextLine();

        System.out.println("=== File sync home of \"ccslab\":");
        Path syncHome = serverStub.getFileSyncHome("ccslab");
        if(syncHome == null) {
            System.err.println("File sync home is null!");
        }
        else {
            System.out.println("File sync home: "+syncHome);
        }

        // terminate CM
        System.out.println("Enter to terminate CM and server: ");
        scanner.nextLine();
        serverStub.terminateCM();
    }
}
