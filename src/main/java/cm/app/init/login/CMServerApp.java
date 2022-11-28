package cm.app.init.login;

import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.Scanner;

/**
 * A simple CM server application.
 * <h1>Note</h1>
 * Before you run this server, check SERVER_ADDR field of cm-server.conf.
 * <br>After you run this server, the server starts CM.
 * <br>Then, you can run a client application.
 */
public class CMServerApp {
    private CMServerStub m_serverStub;
    private CMServerEventHandler m_eventHandler;

    public CMServerApp() {
        m_serverStub = new CMServerStub();
        m_eventHandler = new CMServerEventHandler(m_serverStub);
    }

    public CMServerStub getServerStub() {
        return m_serverStub;
    }

    public CMServerEventHandler getServerEventHandler() {
        return m_eventHandler;
    }

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

        // terminate CM
        System.out.println("Enter to terminate CM and server: ");
        scanner.nextLine();
        serverStub.terminateCM();
    }
}
