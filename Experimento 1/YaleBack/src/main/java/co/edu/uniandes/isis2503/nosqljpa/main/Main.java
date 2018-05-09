/*
 * The MIT License
 *
 * Copyright 2016 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.main;

import co.edu.uniandes.isis2503.nosqljpa.logic.AlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.InmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ResidenciaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.InmuebleDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.ResidenciaDTO;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.*;

/**
 *
 * @author Luis Felipe Mendivelso Osorio <lf.mendivelso10@uniandes.edu.co>
 */
public class Main {
    static ServerSocket ss;
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String agrs[]) {
        try {
            String webappDirLocation = "src/main/webapp/";
            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "8080";
            }
            Server server = new Server(Integer.valueOf(webPort));
            WebAppContext root = new WebAppContext();
            root.setContextPath("/");
            root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
            root.setResourceBase(webappDirLocation);
            root.setParentLoaderPriority(true);
            server.setHandler(root);
            server.start();
            Manejador handler = new Manejador();
            ss = new ServerSocket(10000);
            handler.start();
            server.join();
        } catch (InterruptedException ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        } catch (Exception ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        }

    }
    private static class Manejador extends Thread {
        
        public Manejador(){
            super();
        }
        
        @Override
        public void run() {
            try {
                ResidenciaLogic rl = new ResidenciaLogic();
                InmuebleLogic inl = new InmuebleLogic();
                AlarmaLogic all = new AlarmaLogic();
                System.out.println("Esperando conexión de cliente...");
                Socket sc = ss.accept();
                while(true){
                    DataInputStream in = new DataInputStream(new BufferedInputStream(sc.getInputStream()));
                    int count;
                    byte[] buffer = new byte[8192]; // or 4096, or more
                    while ((count = in.read(buffer)) > 0)
                    {
                        JSONObject obj = new JSONObject(new String(buffer, "utf-8"));
                        ResidenciaDTO residencia = rl.find(obj.getJSONObject("alarma").getString("residencia"));
                        InmuebleDTO inmueble = inl.find(obj.getJSONObject("alarma").getString("inmueble"));
                        if (residencia==null && inmueble==null)
                            System.out.println("La siguiente alarma se guardará sin residencia ni inmueble.");
                        all.add(new AlarmaDTO(obj.getJSONObject("alarma").getString("alarma"), residencia, inmueble));
                        System.out.println("Alarma guardada.");
                        buffer = new byte[8192];
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}