import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/hash/*")
public class HashController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener el parámetro 'text' de la URL
        String text = request.getParameter("text");
        if (text == null) text = "";

        // 2. Generar el Hash y el tiempo de respuesta
        String hashResult = HashUtils.getMd5(text);
        long responseTime = System.currentTimeMillis();

        // 3. Configurar la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // 4. Construir el JSON exactamente como pide el ejercicio
        String jsonResponse = "{\n" +
            "  \"header\": {\n" +
            "    \"api_name\": \"My API Name\",\n" +
            "    \"api_version\": \"1.0.0\",\n" +
            "    \"api_user\": \"guest\",\n" +
            "    \"api_endpoint\": \"api/hash/\",\n" +
            "    \"http_request_method\": \"GET\",\n" +
            "    \"http_request_parameters\": {\n" +
            "      \"algorithm\" : \"md5\",\n" +
            "      \"text\": \"" + text + "\"\n" +
            "      },\n" +
            "    \"http_response_status\": 200,\n" +
            "    \"http_response_message\": \"OK\",\n" +
            "    \"response_time\": " + responseTime + "\n" +
            "  },\n" +
            "  \"body\" :\n" +
            "  {\n" +
            "    \"algorithm\" : \"md5\",\n" +
            "    \"text\" : \"" + text + "\",\n" +
            "    \"hash\" : \"" + hashResult + "\"\n" +
            "  }\n" +
            "}";

        out.print(jsonResponse);
        out.flush();
    }
}
