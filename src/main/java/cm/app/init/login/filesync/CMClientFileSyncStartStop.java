package cm.app.init.login.filesync;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.info.enums.CMFileSyncMode;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.nio.file.Path;
import java.util.Scanner;
/**
 * A simple CM client application to start/stop file synchronization
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerFileSyncHome or CMServerApp).
 * <br>Check that the SERVER_ADDR field of the cm-client.conf file is the same as that of
 * the cm-server.conf file.
 * <br>After you run this client, the client starts CM and logs in to the server with the user name "ccslab".
 * <br>1. If you press the enter key, the client prints out the synchronization home directory.
 * <br>2. If you press the enter key, the client starts file-sync with MANUAL mode.
 * You can add files to the sync home or modified a file to check that files are synchronized with the server.
 * <br>3. If you press the enter key, the client prints out the current file-sync mode (MANUAL).
 * <br>4. If you press the enter key, the client stops the file-sync.
 * <br>5. If you press the enter key, the client prints out the current file-sync mode (OFF).
 * <br>6. If you press the enter key, CM and the client terminates.
 */
public class CMClientFileSyncStartStop {

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

        // get file sync home
        System.out.println("=== get file sync home: ");
        Path syncHome = clientStub.getFileSyncHome();
        if(syncHome == null) {
            System.err.println("File sync home is null!");
        }
        else {
            System.out.println("File sync home: "+syncHome);
        }

        // wait before executing next API
        System.out.println("Press enter to execute next API:");
        scanner.nextLine();

        // start file sync
        System.out.println("=== start file sync: ");
        ret = clientStub.startFileSync(CMFileSyncMode.MANUAL);
        if(ret) {
            System.out.println("File sync with manual mode starts.");
        }
        else {
            System.err.println("Start error of file sync with manual mode!");
        }

        // wait before executing next API
        System.out.println("Press enter to execute next API:");
        scanner.nextLine();

        // get current file sync mode
        System.out.println("=== get current file sync mode: ");
        CMFileSyncMode currentMode = clientStub.getCurrentFileSyncMode();
        System.out.println("Current file sync mode: "+currentMode);

        // wait before executing next API
        System.out.println("Press enter to execute next API:");
        scanner.nextLine();

        // stop file sync
        System.out.println("=== stop file sync: ");
        ret = clientStub.stopFileSync();
        if(ret) {
            System.out.println("File sync stops.");
        }
        else {
            System.err.println("Error to stop file sync!");
        }

        // wait before executing next API
        System.out.println("Press enter to execute next API:");
        scanner.nextLine();

        // get current file sync mode
        System.out.println("=== get current file sync mode: ");
        currentMode = clientStub.getCurrentFileSyncMode();
        System.out.println("Current file sync mode: "+currentMode);

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();

    }
}
