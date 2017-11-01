package MultiTransfertoTest;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-9-20.
 */
public class Server implements Runnable {


    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8899));

            //接收socket连接
            for (int i = 0; i < MultiTest.loaderNum; i++) {
                SocketChannel socketChannel = serverSocketChannel.accept();

                (new Thread(new Receiver(socketChannel))).start();
            }

            serverSocketChannel.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8899));

            //接收socket连接
            for (int i = 0; i < MultiTest.loaderNum; i++) {
                SocketChannel socketChannel = serverSocketChannel.accept();

                (new Thread(new Receiver(socketChannel))).start();
            }

            serverSocketChannel.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
