package com.deploy.sistema.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class LocalShell {

    private static final Logger log = Logger.getLogger(LocalShell.class.getName());
    String saida = "Início> ";

    public void executeCommand(final String command) throws IOException {
        final ArrayList<String> commands = new ArrayList<String>();
        saida += " ; new ArrayList";
        commands.add("/bin/bash");
        saida += " ; add bin bash";
        commands.add("-c");
        commands.add("sudo su");
        saida += " ; add -c";
        commands.add(command);
        saida += " ; add command";
        BufferedReader br = null;
        try {
            final ProcessBuilder p = new ProcessBuilder(commands);
            saida += " ; new ProcessBuilder";
            final Process process = p.start();
            saida += " ; p.start";
            final InputStream is = process.getInputStream();
            saida += " ; getImputStream";
            final InputStreamReader isr = new InputStreamReader(is);
            saida += " ; ImputStreamReader";
            br = new BufferedReader(isr);
            saida += " ; new BufferedReader";
            String line;
            while ((line = br.readLine()) != null) {
                saida += " ; " + line;
                System.out.println("Retorno do comando = [" + line + "]");
            }
        } catch (IOException ioe) {
            log.severe("Erro ao executar comando shell" + ioe.getMessage());
            throw ioe;
        } finally {
            secureClose(br);
        }
    }

    private void secureClose(final Closeable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (IOException ex) {
            log.severe("Erro = " + ex.getMessage());
        }
    }
    
    public String getSaida(){
        return saida;
    }
}
