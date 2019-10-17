package cn.tqktqk.nio.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author ：涂齐康
 * @date ：Created in 2019/10/17 5:11 下午
 * @description：
 * @modified By：
 * @version:
 */
public class ChatClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        //对连接感兴趣
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(8899));

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(selectionKey -> {
                //如果是可连接
                if (selectionKey.isConnectable()) {
                    //获取与服务器端建立的channel
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    //连接操作是否处于正在进行的状态
                    if (client.isConnectionPending()) {
                        try {
                            //完成连接
                            client.finishConnect();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            byteBuffer.clear();
                            String message = LocalDateTime.now() + "-----已经成功连接";
                            byteBuffer.put(message.getBytes());
                            byteBuffer.flip();
                            client.write(byteBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while (true) {
                                    byteBuffer.clear();
                                    InputStreamReader input = new InputStreamReader(System.in);
                                    BufferedReader bufferedReader = new BufferedReader(input);

                                    String sendMessage = bufferedReader.readLine();
                                    byteBuffer.put(sendMessage.getBytes());
                                    byteBuffer.flip();
                                    client.write(byteBuffer);
                                }
                            });
                            client.register(selector,SelectionKey.OP_READ);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }else if (selectionKey.isReadable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int count  = 0;
                    try {
                        count = client.read(readBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (count>0){
                        String message = new String(readBuffer.array(),0,count);
                        System.out.println(message);
                    }
                }

            });
            selectionKeys.clear();
        }


    }


}
