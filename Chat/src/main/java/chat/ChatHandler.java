package chat;

import java.io.*;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

class ChatHandler extends Thread {
    private static final Vector<ChatHandler> handlers = new Vector<>();
    private final Socket s;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;

    ChatHandler(Socket s) throws IOException {
        this.s = s;
        dataInputStream = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    }

    private static void broadcast(String message) {
        synchronized (handlers) {
            Enumeration<ChatHandler> e = handlers.elements();
            while (e.hasMoreElements()) {
                ChatHandler c = e.nextElement();
                try {
                    synchronized (c.dataOutputStream) {
                        c.dataOutputStream.writeUTF(message);
                    }
                    c.dataOutputStream.flush();
                } catch (IOException ex) {
                    c.interrupt();
                }
            }
        }
    }

    public void run() {
        String name = s.getInetAddress().toString();
        try {
            broadcast(name + " has joined.");
            handlers.addElement(this);
            do {
                String msg = dataInputStream.readUTF();
                broadcast(name + " - " + msg);
            } while (true);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            handlers.removeElement(this);
            broadcast(name + " has left.");
            try {
                s.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
