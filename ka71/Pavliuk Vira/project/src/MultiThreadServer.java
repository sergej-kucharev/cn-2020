import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    public static int round = 1;
    public static InputStream inputStream;
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public MultiThreadServer(String word) {
        try {
            ServerSocket server = new ServerSocket(55337);
            Throwable var2 = null;

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                Throwable var4 = null;

                try {
                    while(!server.isClosed()) {
                        if (round > 3) {
                            System.out.println("Game Over!");
                            server.close();
                            break;
                        }

                        Socket client = server.accept();
                        executeIt.execute(new MonoThreadServer(client, word));
                        System.out.print("Connection accepted.");
                    }

                    executeIt.shutdown();
                } catch (Throwable var29) {
                    var4 = var29;
                    throw var29;
                } finally {
                    if (br != null) {
                        if (var4 != null) {
                            try {
                                br.close();
                            } catch (Throwable var28) {
                                var4.addSuppressed(var28);
                            }
                        } else {
                            br.close();
                        }
                    }

                }
            } catch (Throwable var31) {
                var2 = var31;
                throw var31;
            } finally {
                if (server != null) {
                    if (var2 != null) {
                        try {
                            server.close();
                        } catch (Throwable var27) {
                            var2.addSuppressed(var27);
                        }
                    } else {
                        server.close();
                    }
                }

            }
        } catch (IOException var33) {
            var33.printStackTrace();
        }

    }
}
