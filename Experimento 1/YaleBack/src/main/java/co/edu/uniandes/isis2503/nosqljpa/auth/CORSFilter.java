package co.edu.uniandes.isis2503.nosqljpa.auth;

import java.util.logging.Level;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext crc, ContainerResponseContext crc1) {
        //TODO Edit to localhost:3000
        /**try {
            String webappDirLocation = "src/main/webapp/";
            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "3000";
            }
            Server server = new Server(Integer.valueOf(webPort));
            WebAppContext root = new WebAppContext();
            root.setContextPath("/");
            root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
            root.setResourceBase(webappDirLocation);
            root.setParentLoaderPriority(true);
            server.setHandler(root);
            server.start();
            server.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
        
        
        crc1.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        crc1.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        crc1.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        crc1.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization");
    }
}