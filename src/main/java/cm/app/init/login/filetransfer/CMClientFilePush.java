package cm.app.init.login.filetransfer;

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
        // TODO: from here
    }
}
