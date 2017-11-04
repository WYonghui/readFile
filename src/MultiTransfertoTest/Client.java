package MultiTransfertoTest;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-9-20.
 */
public class Client {

    public static void main(String[] args) {
        int loaderNum = MultiTest.loaderNum;
        if (args.length >= 1){  //从参数列表读取并行度
            loaderNum = Integer.parseInt(args[0]);
        }

        try {
//            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//            serverSocketChannel.socket().bind(new InetSocketAddress(8899));

            //连接服务器，启动接收线程
            SocketChannel[] socketChannels = new SocketChannel[loaderNum];
            for (int i = 0; i < loaderNum; i++) {
                socketChannels[i] = SocketChannel.open();
                socketChannels[i].connect(new InetSocketAddress("localhost", 8899));
//                SocketChannel socketChannel = serverSocketChannel.accept();

                (new Thread(new Receiver(socketChannels[i]))).start();
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
