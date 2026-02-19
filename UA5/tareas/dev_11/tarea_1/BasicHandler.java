import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

abstract class BasicHandler implements HttpHandler 
{
    public DataStore store;
    public BasicHandler(DataStore store)
    {
        this.store = store;
    }

    public static Map<String, String> queryToMap(String query)
    {
        Map<String, String> result = new HashMap<String, String>();

        if (query == null || query.isEmpty())
        {
            return result;
        }

        for (String param : query.split("&")) 
        {
            String pair[] = param.split("=");
            if (pair.length>1) 
            {
                result.put(
                    URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                    URLDecoder.decode(pair[1], StandardCharsets.UTF_8)
                );
            }
            else
            {
                result.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8), "");
            }
        }
        return result;
    }
  }
