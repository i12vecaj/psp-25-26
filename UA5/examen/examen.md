# EXAMEN -- PROGRAMACIÓN SEGURA EN JAVA

## 2º DAM -- Parte Teórico-Práctica

------------------------------------------------------------------------

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 1 -- Análisis de código inseguro

Analiza el siguiente código Java:

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

``` java
import java.sql.*;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Usuario:");
        String user = sc.nextLine();

        System.out.println("Password:");
        String pass = sc.nextLine();

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/app", "root", "root");

        Statement st = con.createStatement();

        String query = "SELECT * FROM usuarios WHERE nombre='"
                + user + "' AND password='" + pass + "'";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            System.out.println("Acceso concedido");
        } else {
            System.out.println("Acceso denegado");
        }

        con.close();
    }
}
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.1

Identifica al menos 4 vulnerabilidades o malas prácticas de seguridad en
el código.

Explica brevemente cada una.

1. Se puede hacer SQL Injection ya que no hay sanitización de los parametros introducidos y se coge de manera directa la entrada del usuario.

2. Las credenciales de la DB están expuestas en el código

3. El manejo de excepciones es génerico, no se contempla cada posible caso, vamos que no es una muy buena práctica.

4. Se puede hacer un ataque de fuerza bruta ya que no hay un límite de intentos.

5. No se usa try-with-resources osea no se cierran bien los recursos, aunque al final se cierre la conexión puede ocurrir una excepción antes y chimpún.

6. Contraseñas en texto plano, lo suyo es hashear

7. Statement en vez de un PreparedStatement (Prevenir SQL Injection)

8. También me parece que se puede desbordar el buffer por como está hecho el código

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos

``` java
/* No es el mejor rewrite que se podría hacer pero se capta la idea */
import java.sql.*;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        // Las credenciales se deberían coger desde unas variables de entorno
        // pero no me sé el código que debería ir para cogerlas así que lo dejo así
        String dbUrl;
        String dbUser; 
        String dbPass; 

        // Try-with-resources
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Usuario:");
            String user = sc.nextLine();

            System.out.println("Password:");
            String pass = sc.nextLine();

            // Consulta parametrizada para prevenir SQL Injection
            String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";

            // Try-with-resources
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, user);
                ps.setString(2, pass);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Acceso concedido");
                    } else {
                        System.out.println("Acceso denegado");
                    }
                }
            }
        } catch (SQLException e) {
            // Manejo de excepciones con SQLException y no el genérico de Java
            System.err.println("Error al hacer login.");
        }
    }
}
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 2 -- Conceptos de programación segura

Responde a las siguientes preguntas:

### Pregunta 2.1

Explica qué es una vulnerabilidad de tipo SQL Injection y cómo se
previene en Java.

Una SQL Injection es literalmente inyectar código SQL a través de campos que no están bien sanitizados, esto conlleva a la exposición de la base de datos donde suele haber datos sensibles y credenciales de administrador, vamos que podrías tener acceso completo a un servidor/sistema solamente por un campo mal sanitizado. En resumen es un vulnerabilidad que permite lanzar código SQL.

Para prevenirla en Java se puede usar PreparedStatement y sanitizar/comprobar las consultas en el código, tendrías que comprobar el tipo de input que se espera que el usuario meta, nada de patrones raros y tal.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

Es una mala práctica porque si alguien consigue acceso no autorizado a la base de datos y se exponen todas las contraseñas estando en texto plano pues básicamente las puede leer y utilizar, para evitar esto se usa el Hashing (y por encima se añade salting). El hash es una función que transforma un texto plano en un texto cifrado, y el salting es añadir un valor aleatorio a la contraseña antes de hashearla como medida preventiva. Por ejemplo, si dos usuarios tienen la misma contraseña, al hashearlas con salting tendrán diferentes hashes. Con el hash consigues que las contraseñas no se vean en texto plano y no puedes utilizarlas en un login.

Se suele usar MD5, SHA-1 o SHA-256 normalmente.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

Es una medida de seguridad que consiste en otorgar a los usuarios solo los permisos necesarios para realizar sus tareas. Ej: En una base de datos, un usuario que solo necesita leer datos no debería tener permisos para modificarlos o eliminarlos.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a)  PreparedStatement evita SQL Injection\
b)  RSA es criptografía simétrica\
c)  La clave privada puede compartirse públicamente\
d)  Un certificado digital contiene una clave pública

a) Verdadera, usa parámetros en vez de concatenar strings y pues así "evitas cosas que no deberían entrar"\
b) Falsa, es asimétrica\
c) Falsa, es PRIVADA, logicamente no se debe compartir\
d) Verdadera, contiene la clave pública atada a una persona/entidad y la CA lo valida/certifica

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 3 -- Certificados digitales

Observa el siguiente código:

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

``` java
KeyStore ks = KeyStore.getInstance("JKS");
FileInputStream fis = new FileInputStream("certificado.jks");
ks.load(fis, "123456".toCharArray());

Certificate cert = ks.getCertificate("mi_certificado");
PublicKey publicKey = cert.getPublicKey();

System.out.println(publicKey);
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.1

Explica qué es un certificado digital y para qué se utiliza.

Es un documento que certifica la clave pública de una entidad, emitido por una CA, en España la FNMT, vincula una clave pública a dicha entidad/persona. Se utiliza para verificar la identidad de la entidad/persona y para cifrar y firmar digitalmente datos.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?**

Menciona al menos 3 elementos.

- Versión: normalmente X.509v3
- Número de serie: Identificador numérico único dentro del dominio de la CA
- Algoritmo de firma y parámetros
- Emisor del certificado
- Fechas de inicio y validez
- Nombre del propietario de la clave pública
- Identificador del algoritmo, clave pública y otros parámetros
- Firma digital de la CA (resultado de cifrar el algoritmo simétrico y la clave privada, es el hash resultante)

(literalmente del PDF del tema)

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

-   KeyStore\
-   clave pública\
-   clave privada
-------------
- KeyStore: por su nombre y el código me imagino que es el almacen de claves

En criptografía simétrica:
- Clave pública: no hay
- Clave privada: Solamente la conocen el emisor y el receptor, con ella se cifra y descifra

En criptografía asimétrica:
- Clave pública: Conocida por todos, se usa para cifrar
- Clave privada: Conocida por cada parte para poder descifrar

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 4 -- Criptografía RSA en Java

Analiza el siguiente código:

``` java
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(1024);

KeyPair pair = keyGen.generateKeyPair();

PublicKey pub = pair.getPublic();
PrivateKey priv = pair.getPrivate();
```

### Pregunta 4.1

¿Qué tipo de criptografía utiliza RSA? Explica brevemente cómo funciona.

RSA es un algoritmo muy popular para generar claves que utiliza criptografía asimétrica, utiliza dos claves, una pública y una privada. La pública se utiliza para cifrar y la privada para descifrar. Se basa en la dificultad de factorizar números grandes y es el metodo más extendido de firma digital por su dificultad para romperlo _(nula aunque quién sabe en el futuro con la computación cuántica)_

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a)  Cifrar un mensaje confidencial\
b)  Firmar digitalmente

a) Si usamos criptografía asimetrica se usa la clave pública del emisor y se descifra con la privada del receptor, en simetrica pues la clave privada y ya que es la unica que hay.
b) Se firma con la clave privada del emisor y verifica con la pública del propio emisor

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
Cipher cipher = Cipher.getInstance("RSA");

__________.init(Cipher.ENCRYPT_MODE, ________);

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

Código completado:
``` java
Cipher cipher = Cipher.getInstance("RSA");
c.init(Cipher.ENCRYPT_MODE, clave);
byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```

Pues hombre lo suyo es usar al menos 1024 bits, recomendado 2048 o más, 512 es muy poco y se puede romper con relativa facilidad.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
