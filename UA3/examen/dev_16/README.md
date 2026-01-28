## NOTAS / COMENTARIOS

Cambios notables pocos, solamente había que añadir parametros, inicializaciones y tal.

Añadir que el Socket del servidor nunca se cerraba pero lo he cambiado, con un try-with-resources hubiera bastado pero había otra opción más simple.

--------------------------------------------------------

Además de eso ocurre este error a veces (raro): 
- Creas conexiones
- Cierras una de ellas
- ERROR: Connection reset
- Te conectas de nuevo con otro cliente (rápido)
- Error raro de direccion:
Exception in thread "main" java.net.BindException: Address already in use: bind
        at java.base/sun.nio.ch.Net.bind0(Native Method)
        at java.base/sun.nio.ch.Net.bind(Net.java:565)
        at java.base/sun.nio.ch.Net.bind(Net.java:554)
        at java.base/sun.nio.ch.NioSocketImpl.bind(NioSocketImpl.java:636)
        at java.base/java.net.ServerSocket.bind(ServerSocket.java:391)
        at java.base/java.net.ServerSocket.<init>(ServerSocket.java:278)
        at java.base/java.net.ServerSocket.<init>(ServerSocket.java:171)
        at ServidorChat.main(ServidorChat.java:10)

Este error es muy raro pero se ha dado

--------------------------------------------------------

Otros problemas son:
* no se comprueban los nombres, por lo que pueden repetirse estando conectados (Habría que identificar por ID)
* Si te conectas muchas veces hasta llegar al "máximo" de conexiones y las cierras, aún así no te deja entrar con nuevos clientes. Pero el contador de conexiones actuales funciona correctamente. 