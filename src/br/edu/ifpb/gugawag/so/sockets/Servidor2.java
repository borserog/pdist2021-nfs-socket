package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Servidor2 {
    public List<String> arquivos = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        ArquivoService arquivoService = new ArquivoService();

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println(mensagem);
            String[] resposta = mensagem.split(" ");

            switch (resposta[0]) {
                case "1":
                    dos.writeUTF("Listando Arquivos: " + arquivoService.readdir());
                    break;
                case "2":
                    dos.writeUTF("Renomeando '" + resposta[1] + "' para '" + resposta[2] + "'");
                    arquivoService.rename(resposta[1], resposta[2]);
                    break;
                case "3":
                    dos.writeUTF("Criando Novo Arquivo: " + arquivoService.create());
                    break;
                case "4":
                    dos.writeUTF("Apagando: " + resposta[1]);
                    arquivoService.remove(resposta[1]);
                    break;
                default:
                    dos.writeUTF("Comando não reconhecido");
            }


        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }
}
