# EXAMEN -- PROGRAMACIÓN SEGURA EN JAVA

## 2º DAM -- Parte Teórico-Práctica

---

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 1 -- Análisis de código inseguro

Analiza el siguiente código Java:

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

```java
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

Este código es un login a una aplicación. Para ello recoge un usuario y una contraseña, las cuales se almacenan en sendas variables. Luego hace conexión con la BBDD en mysql en local, crea el query de la BBDD donde busca por usuario y contraseña y, si lo encuentra dice Acceso concedido y, si falla, acceso denegado. Por último, cierra la conexión con la BBDD

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.1

Identifica al menos 4 vulnerabilidades o malas prácticas de seguridad en
el código.

Explica brevemente cada una.

1. No se cifra la contraseña: La contraseña, que debería estar almacenada de forma encriptada, se compara de 1 a 1 desde la propia variable, sin pasar por ningún proceso de encriptación.

2. No se hace un uso específico del try/catch: es decir, aunque se utilice el throws Exception, no hay un control de cada tipo de error.

3. Se toman decisiones de acceso según variables (usuario y contraseña) en tiempo de ejecución.

4. En la conexion a la BBDD se almacena tanto el usuario y la contraseña de MySQL en el propio código, en lugar de, o pedirla por consola, o cifrarla y descifrarla.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

- Prevención de SQL Injection\
- Buen manejo de credenciales\
- Buen uso de recursos

```java
import java.sql.*;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Usuario: ");
        String user = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();


        String url = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        String sql = "SELECT 1 FROM usuarios WHERE nombre = ? AND password = ?";


        try (Connection con = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement ps = con.prepareStatement(sql)) {


            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println(rs.next() ? "Acceso concedido" : "Acceso denegado");
            }

        } catch (SQLException e) {
            System.out.println("Error de conexión o consulta.");
        } finally {
            sc.close();
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

Una SQL Injection es una vulnerabilidad donde un atacante mete código SQL malicioso en datos de entrada (formularios, parámetros URL, etc.) para que la aplicación ejecute consultas no previstas.

Para prevenirla en Java se utiliza PreparedStatement, validando la entrada y sin utilizar concatenaciones. (cosa que tambien se hace en el código de ejemplo)

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

Porque cualquiera puede acceder al valor de esa variable desde el propio programa. Lo que se debe hacer es encriptar las contraseñas mediante un protocolo seguro, como AES-256 para cifrado de datos (vía JCE).

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

Este principio lo que nos dice es que a cada tipo de usuario solamente se le tienen que otorgar los privilegios necesarios para el tipo de trabajo que va a ejercer en nuestra aplicación. Por ejemplo, en la gestión de una BBDD, no le vamos a otorgar privilegios de DELETE a una persona que solamente se va a dedicar al registro de nuevos usuarios. En los programas, pues si el servicio de desuscripción de nuestra aplicación lo hace un protocolo en concreto, que ese protocolo solamente pueda usar DELETE en la BBDD. Si otorgamos permisos de superadmin a todo el mundo, pues sería un caos en términos de responsabilidad

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a) PreparedStatement evita SQL Injection\

Verdadero a medias. Si que lo previene si se emplea bien, con parámetros (?). Si concatenas el texto (con +), entonces no lo hace.

b) RSA es criptografía simétrica\

Falso. Usa criptografía asimétrica, ya que utiliza una clave pública para poder enviar mensajes al participante, mientras que la clave privada es conocida por el receptor del mismo, que usará para desencriptar los mensajes.

c) La clave privada puede compartirse públicamente\

Falso. Su propio nombre lo dice, la clave privada debe ser individual de cada usuario. De compartirse alguna, esta sería la pública.

d) Un certificado digital contiene una clave pública

Verdadero. Contiene la clave pública del titular, junto con datos de identidad y la firma de la autoridad certificadora.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 3 -- Certificados digitales

Observa el siguiente código:

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

```java
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

Un certificado digital es un documento que certifica la clave pública de una entidad. Se utilizan para:

▪ Asegurar el tratamiento de datos de una forma confiable, puesto que estos documentos están certificados por Autoridades de Certificación.
▪ Autentificar la identidad del usuario de forma electrónica ante terceros
▪ Realizar trámites administrativos online (Agencia Tributaria, Seguridad Social, SAS…)
▪ Trabajar con otras facturas o servicios electrónicos
▪ Firmar electrónicamente todo tipo de documentos
▪ Cifrar datos para que sólo el destinatario pueda acceder a su contenido

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

Contiene la clave pública del titular, junto con datos de identidad y la firma de la autoridad certificadora.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

- KeyStore\
- clave pública\
- clave privada

KeyStore: es un contenedor/archivo seguro (en Java) donde guardas material criptográfico, como claves y certificados, protegido por contraseña.
Clave pública: se puede compartir; sirve para cifrar datos para el dueño de la clave privada o para verificar firmas.
Clave privada: debe permanecer secreta; sirve para descifrar lo cifrado con la pública y para firmar digitalmente.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 4 -- Criptografía RSA en Java

Analiza el siguiente código:

```java
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(1024);

KeyPair pair = keyGen.generateKeyPair();

PublicKey pub = pair.getPublic();
PrivateKey priv = pair.getPrivate();
```

### Pregunta 4.1

¿Qué tipo de criptografía utiliza RSA? Explica brevemente cómo funciona.

Utiliza criptografía asimétrica. El El emisor emplea una clave pública, definida previamente por el receptor, para encriptar. El receptor emplea la clave privada correspondiente para desencriptar el mensaje. De esta forma, solo el receptor puede desencriptar el mensaje. Estos algoritmos implican que cada participante tenga un par de claves

▪ La clave pública es conocida por todos (para poder enviarle mensajes)
▪ La clave privada es conocida solo por cada interesado (para poder desencriptar los mensajes)

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a) Cifrar un mensaje confidencial\

Para cifrar un mensaje confidencial (en criptografía asimétrica), se usa la clave pública del destinatario.

b) Firmar digitalmente

Para firmar digitalmente, se usa la clave privada del emisor.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

```java
Cipher cipher = Cipher.getInstance("RSA");

cipher.init(Cipher.ENCRYPT_MODE, clavepublica);

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

```java
keyGen.initialize(512);
```

En mi opinión, nosotros en clase en los ejemplos que hemos ido haciendo, las encriptaciones que se han hecho han sido mínimo de 1024 bits. Como a mayor cantidad de bits que se utilicen, más dificil es vulnerar la encriptación, yo imagino que es bastante vulnerable. No obstante, supongo que en ciertos dispositivos que cuenten con un almacenamiento ínfimo y que la vulneración de su clave no sea una cuestión de vida o muerte, pues supongo que habrán casos en los que 512 bits sean la única solución.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
