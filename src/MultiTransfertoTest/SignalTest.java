package MultiTransfertoTest;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-10-20.
 */
public class SignalTest {

    private static int length = 1024*1024*10;   //10MB

    public static void main(String[] args) {

        String path;
        if (args.length > 0)
            path = args[0];
        else
            path = "/root/wang/projects/hiberfil.sys";
//            path = "/home/wang/IdeaProjects/projects/intellij.pdf";

        try {
            FileInputStream inputStream = new FileInputStream(new File(path));
            FileChannel fileChannel = inputStream.getChannel();

//            Thread thread = new Thread(new SingleClient());
//            thread.start();

            SocketChannel out = SocketChannel.open();
            out.connect(new InetSocketAddress("localhost", 8899));

            long start = System.currentTimeMillis();
            long size = 0;
            long l;
            long p = 0;
            while (true){
                l = fileChannel.transferTo(p, length, out);
                if (l <= 0){
                    out.close();
                    fileChannel.close();
                    break;
                }

                size += l;
                p = size;
            }

            long end = System.currentTimeMillis();
//            System.out.println("传输数据的时间为" + (end-start)/1000 + "s");
            System.out.println("传输数据的时间为" + (end-start) + "ms");

            inputStream.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }





    }


}
