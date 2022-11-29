package cm.app.init.login.filetransfer;

import cm.app.init.login.CMClientApp;
import cm.app.init.login.CMClientEventHandler;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * A simple CM client application to test sending files
 * <h1>Note</h1>
 * Before you run this client, you must run a server (CMServerApp).
 * <br>Check that the SERVER_ADDR field of the cm-client.conf file is the same as that of
 * the cm-server.conf file.
 * <br>In cm-server.conf and cm-client.conf, enable the PERMIT_FILE_TRANSFER field by setting to 1 for
 * the simplicity of the file transfer management of CM.
 * <br>After you run this client, you can also run another receiver client (CMClientFileReceiver)
 * if you want to send files to the client.
 * <br>1. The client logs in to the server with the user name "ccslab".
 * <br>2. If you press the enter key, the client shows a file chooser where you can select files.
 * <br>3. If you click confirm button, you can type a receiver: "SERVER" for the server or "mlim"
 * for the receiver client.
 * <br>4. If you press the enter key, CM and the client terminates.
 */public class CMClientFilePush {
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

        System.out.println("=== select files to send: ");
        Path transferHome = clientStub.getTransferedFileHome();
        // open file chooser to choose files
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
        fc.setCurrentDirectory(transferHome.toFile());
        int fcRet = fc.showOpenDialog(null);
        if(fcRet != JFileChooser.APPROVE_OPTION) return;
        File[] files = fc.getSelectedFiles();

        for(File file : files)
            System.out.println("selected file = " + file);
        if(files.length < 1) {
            System.err.println("No file selected!");
            return;
        }

        // input file receiver
        System.out.println("Receiver of files: ");
        System.out.println("Type \"SERVER\" for the server or \"mlim\" for client receiver.");
        System.out.println("For \"mlim\", you must run CMClientFileReceiver before the file transfer.");
        String receiver = scanner.nextLine().trim();

        // send files
        for(File file : files)
            clientStub.pushFile(file.getPath(), receiver);

        // terminate CM
        System.out.println("Enter to terminate CM and client: ");
        scanner.nextLine();
        clientStub.terminateCM();
    }
}
