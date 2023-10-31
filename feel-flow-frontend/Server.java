import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        // Crear el servidor en el puerto 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Manejador para las solicitudes
        server.createContext("/", new MyHandler());
        
        // Iniciar el servidor
        server.start();
        
        System.out.println("Servidor iniciado en http://localhost:8000/");
    }
    
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "¡Hola! Esta es una respuesta del servidor.";
            
            // Configurar la respuesta como HTML
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            
            // Enviar la respuesta al cliente
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
