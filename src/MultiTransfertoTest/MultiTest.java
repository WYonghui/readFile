package MultiTransfertoTest;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-10-20.
 */
public class MultiTest {

    public static int length = 1024*1024*1;
    public static int loaderNum = 1;

    public static void main(String[] args) {

        String path;
        if (args.length > 0)
            path = args[0];
        else {
            path = "/root/wang/projects/hiberfil.sys";
//            path = "/home/wang/IdeaProjects/projects/intellij2.pdf";
        }

        if (args.length >= 2){  //从参数列表中读取并行度
            loaderNum = Integer.parseInt(args[1]);
        }

        File file = new File(path);

        long start = System.currentTimeMillis();

        //启动服务器线程
//        Thread client = new Thread(new Client());
//        client.start();

        //启动服务器端接口并监听
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(8899));

            //接收socket连接并启动文件传输
            Thread[] loaders = new Thread[loaderNum];
            SocketChannel[] socketChannels = new SocketChannel[loaderNum];
            for (int i = 0; i < loaderNum; i++) {
                socketChannels[i] = server.accept();

//                (new Thread(new Receiver(socketChannel))).start();
                loaders[i] = (new Thread(new Loader(file, socketChannels[i])));
                loaders[i].start();
            }

            //等待所有文件传输线程传输完成
            for (int i = 0; i < loaderNum; i++) {
                loaders[i].join();
                socketChannels[i].close();
            }

            server.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //连接服务器
//        SocketChannel socketChannel = null;
//        try {
//            socketChannel = SocketChannel.open();
//            socketChannel.connect(new InetSocketAddress("localhost", 8899));
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//        Thread[] loaders = new Thread[loaderNum];
//        for (int i = 0; i < loaderNum; i++) {
//            loaders[i] = new Thread(new Loader(file, socketChannel));
//            loaders[i].start();
//        }

        //等待传输结束
//        try {
//            for (int i = 0; i < loaderNum; i++) {
//                loaders[i].join();
//            }
//
//            socketChannel.close();
////            server.join();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        long end = System.currentTimeMillis();
//        System.out.println("传输数据的时间为" + (end-start)/1000 + "s.");
        System.out.println("传输数据的时间为" + (end-start) + "ms.");
    }

}
