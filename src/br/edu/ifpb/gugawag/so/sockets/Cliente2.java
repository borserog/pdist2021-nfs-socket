package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("== Cliente ==");

        // configurando o socket
        Socket socket = new Socket("127.0.0.1", 7001);
        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do cliente
        while (true) {
            System.out.println("================ NFS Options ================");
            System.out.println("1 - listar conteúdo");
            System.out.println("2 - renomear arquivo");
            System.out.println("3 - cria um arquivo");
            System.out.println("4 - remove um arquivo");

            Scanner opcao = new Scanner(System.in);
            String opcaoString = opcao.nextLine();

            switch (opcaoString) {
                case "2":
                    System.out.println("Nome do arquivo a ser renomeado (sem extensão):");
                    Scanner arquivoParaRenomear = new Scanner(System.in);
                    String arquivoParaRenomearInput = arquivoParaRenomear.nextLine();

                    System.out.println("Novo nome de arquivo (sem extensão):");
                    Scanner novoNome = new Scanner(System.in);
                    String novoNomeInput = novoNome.nextLine();

                    dos.writeUTF(opcaoString + " " + arquivoParaRenomearInput + " " + novoNomeInput);
                    break;
                case "4":
                    System.out.println("Nome do arquivo a ser removido (sem extensão):");
                    Scanner arquivoParaDeletar = new Scanner(System.in);
                    dos.writeUTF(opcaoString + " " + arquivoParaDeletar.nextLine());
                    break;
                default:
                    // escrevendo para o servidor
                    dos.writeUTF(opcaoString);
            }


            // lendo o que o servidor enviou
            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: " + mensagem);
        }
        /*
         * Observe o while acima. Perceba que primeiro se escreve para o servidor (linha 27), depois se lê do canal de
         * entrada (linha 30), vindo do servidor. Agora observe o código while do Servidor2. Lá, primeiro se lê,
         * depois se escreve. De outra forma, haveria um deadlock.
         */
    }
}
