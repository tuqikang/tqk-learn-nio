package cn.tqktqk.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

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
 * @date ：Created in 2019/10/16 7:40 下午
 * @description：
 * @modified By：
 * @version:
 */
public class ChatServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>(16);

    public static void main(String[] args) throws Exception {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        ServerSocket socket = socketChannel.socket();

        socket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);

                            String key = "[" + UUID.randomUUID().toString() + "]";
                            clientMap.put(key, client);
                        } else if (selectionKey.isReadable()) {
                            //你之前注册的什么对象，得到的一定是什么对象，之前我们连接好了是把SocketChannel注册进来
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            if (count > 0) {
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                //charset.decode解码 然后输出为字节数组，通过String.valueOf将字节数组打印
                                String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                                String sendKey = null;
                                System.out.println(client + ":" + receivedMessage);
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (entry.getValue().equals(client)) {
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }

                                ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

                                String sendMessage = sendKey + ":" + receivedMessage;
                                clientMap.values().stream().forEach(p -> {
                                    sendBuffer.clear();
                                    if (!p.equals(client)) {

                                        sendBuffer.put(sendMessage.getBytes());
                                        sendBuffer.flip();
                                        try {
                                            p.write(sendBuffer);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        sendBuffer.put(("我:" + receivedMessage).getBytes());
                                        sendBuffer.flip();
                                        try {
                                            p.write(sendBuffer);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        }
//                        selectionKeys.remove(selectionKey);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                selectionKeys.clear();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


    }
}
