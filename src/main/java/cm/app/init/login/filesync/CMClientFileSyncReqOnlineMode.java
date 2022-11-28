package cm.app.init.login.filesync;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.info.enums.CMFileSyncMode;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
/**
 * A simple CM client application to change file mode to online mode for file synchronization
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerFileSyncHome or CMServerApp).
 * <br>After you run this client, the client starts CM and logs in to the server with the user name "ccslab".
 * <br>1. If you press the enter key, the client prints out the synchronization home directory.
 * <br>2. If you press the enter key, the client starts file-sync with MANUAL mode.
 * You can add files to the sync home or modified a file to check that files are synchronized with the server.
 * <br>3. If you press the enter key, the client prints out the current list of local mode files.
 * <br>4. If you press the enter key, the client shows a file chooser window. You can select files
 * to be changed to online mode. You can see that the selected files are changed to the online mode by
 * checking the file size 0.
 * <br>5. If you press the enter key, CM and the client terminates.
 */
public class CMClientFileSyncReqOnlineMode {

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

        // get local mode files
        System.out.println("=== get local mode files: ");
        List<Path> localModeFiles = clientStub.getLocalModeFiles();
        if(localModeFiles == null) {
            System.err.println("Return value is null!");
        }
        else {
            System.out.println("Local mode files: ");
            for(Path path : localModeFiles) System.out.println(path);
        }

        // wait before executing next API
        System.out.println("Press enter to execute next API:");
        scanner.nextLine();

        // get file sync home
        System.out.println("=== request file mode change to online mode: ");
        Path syncHome = clientStub.getFileSyncHome();
        // open file chooser to choose files
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
        fc.setCurrentDirectory(syncHome.toFile());
        int fcRet = fc.showOpenDialog(null);
        if(fcRet != JFileChooser.APPROVE_OPTION) return;
        File[] files = fc.getSelectedFiles();

        for(File file : files)
            System.out.println("selected file = " + file);
        if(files.length < 1) {
            System.err.println("No file selected!");
            return;
        }

        // request online mode
        ret = clientStub.requestFileSyncOnlineMode(files);
        if(ret) {
            System.out.println("Successfully requests to change to online mode.");
        }
        else {
            System.err.println("Error to request to change to online mode!");
        }

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();
    }
}
