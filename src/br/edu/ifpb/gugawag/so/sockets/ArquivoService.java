package br.edu.ifpb.gugawag.so.sockets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ArquivoService {
    public List<String> arquivos = new ArrayList<>();
    private static final String HOME = System.getProperty("user.home");

    public String readdir() throws IOException {
        return Arrays.toString(new File(HOME + "/pdist/").listFiles());
    }

    public void rename(String oldName, String newName) throws IOException {
        Path fileToMovePath = Paths.get(HOME + "/pdist/" + oldName + ".txt");
        Path targetPath = Paths.get(HOME + "/pdist/" + newName + ".txt");
        Files.move(fileToMovePath, targetPath);
    }

    public String create() throws IOException {
        String fileName = "arquivo_" + UUID.randomUUID().toString() + ".txt";
        Path p = Paths.get(HOME + "/pdist/" + fileName);
        Files.createFile(p);
        return fileName;
    }

    public void remove(String fileToDelete) throws IOException {
        Path p = Paths.get(HOME + "/pdist/" + fileToDelete + ".txt");
        Files.delete(p);
    }
}
