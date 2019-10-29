
import java.io.*;
import java.net.*;

interface Player {
  void    send(String msg);
  boolean isRuning();
  void    close();
  void    join();
}

class User implements Player, Runnable {
  Socket s;
  ChatServer ref;
  volatile boolean runing = true;
  PrintWriter writer;
  BufferedReader reader;
  Thread t;

  User(Socket _s, ChatServer _ref) throws IOException {
    s = _s;
    ref = _ref;
    writer = new PrintWriter(s.getOutputStream(), true);
    reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
    t = new Thread(this);
    t.start();
    System.out.println("User created");
  }

  public boolean isRuning() {
    return runing;
  }

  public void close() {
    runing = false;
    send("end connection");
  }

  public void join() {
    try {
      t.join();
    } catch (InterruptedException e) {}
    ref.remove(this);
  }

  public void run() { // thread function
    System.out.println("Thread is runing...");
    try {
      while (runing == true) {
        String l = reader.readLine();
        if (l == null) {
          // possibly error connection or RQ
          // System.out.println("unexpected null received. End of Connection");
          break;
        }
        if (l.equals("end") == true) {
          break;
        } else {
          System.out.println("receive: "+l);
          send("message received.");
        }
      }
    } catch (IOException e) {
      System.out.println("unexpected error: "+e.getMessage());
    }
    close();
    System.out.println("End connection.");
  }

  public void send(String msg) {
    writer.println(msg);
  }
}