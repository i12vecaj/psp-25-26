# EXAMEN -- PROGRAMACIÓN SEGURA EN JAVA

## 2º DAM -- Parte Teórico-Práctica

### Pablo Rodríguez Casado
#### 25/02/2026

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

Explica brevemente cada una:

- Una de las primeras vulnerabilidades que veo, sería que el código no está preparado para evitar inyecciones de SQL, en este caso, el código construye una consulta SQL concatenando directamente las entradas del usuario, lo que hace que sea vulnerable a este tipo de ataques.

--------------------------

- La siguiente vulnerabilidad que veo sería la mala gestion de credenciales, ya que el código utiliza credenciales de base de datos hardcodeadas, lo que es una mala práctica de seguridad. Esto significa que las credenciales están directamente escritas en el código fuente, lo que las hace fácilmente accesibles para cualquier persona que tenga acceso al código. Si un atacante obtiene acceso al código, también obtendrá acceso a las credenciales de la base de datos, lo que puede resultar en un compromiso de seguridad.

-------------------------

- Otra vulnerabilidad que veo sería el mal uso de recursos, ya que el código no utiliza un bloque try-with-resources para manejar la conexión a la base de datos. Esto significa que si ocurre una excepción antes de que se cierre la conexión, la conexión podría quedar abierta indefinidamente, lo que puede resultar en una fuga de recursos y que la base de datos se quede agotada en conexiones.
-------------------------

- Y una cuarta vulnerabilidad que veo sería la falta de manejo de excepciones, ya que el código no maneja adecuadamente las excepciones que pueden ocurrir durante la conexión a la base de datos o la ejecución de la consulta SQL. Esto puede resultar en una aplicación inestable y puede proporcionar información útil a un atacante si se produce una excepción no controlada.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos

He dejado el fragmento de código reescrito en el archivo "Login.java"

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 2 -- Conceptos de programación segura

Responde a las siguientes preguntas:

### Pregunta 2.1

Explica qué es una vulnerabilidad de tipo SQL Injection y cómo se
previene en Java.

- Las inyecciones SQL, son un tipo de vulnerabilidad que permite a un atacante ejecutar código SQL en la base de datos que esté atacando, con lo que puede obtener datos sensibles, modificar la base de datos o hasta eliminarla.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

- Inyectar contraseñas en texto plano es una mala práctica, ya que si un atacante obtiene acceso a la base de datos, podrá ver todas las contraseñas de los usuarios sin ningún tipo de cifrado o protección. Además de que si los usuarios usan la misma contraseña para varios servicios el atacante podrá usarlas para acceder a sus cuentas.
- En su lugar la técnica que habría que usar es el hashing de contraseñas, sirve para transformar la contraseña original en una cadena de texto cifrada, que no se puede revertir a la contraseña original.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

- Es una práctica de seguridad que consiste en dar a los usuarios o procesos solo los permisos necesarios para realizar las cosas que tienen que hacer y nada más.
----
- Por ejemplo si en java tenemos una aplicación que necesita acceder a una base de datos, en lugar de darle permisos de administrador a la cuenta, se les da solo los permisos necesarios para hacer las consultas y operaciones que tiene que hacer, como por ejemplo solo permisos de lectura o escritura en ciertas tablas.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

- a)  PreparedStatement evita SQL Injection\ 
  - La afirmación es verdadera, ya que PreparedStatement utiliza parámetros en lugar de concatenar directamente las entradas del usuario en la consulta SQL.
- b)  RSA es criptografía simétrica\
  - La afirmación es falsa, RSA es un algoritmo de criptografía asimétrica, ósea que utiliza una clave pública y una clave privada para cifrar y descifrar datos, en lugar de una sola clave compartida como en la criptografía simétrica.
- c) La clave privada puede compartirse públicamente\
  - La afirmación es falsa, la clave privada debe mantenerse en secreto y no debe compartirse públicamente, ya que es la clave que se utiliza para descifrar los datos cifrados con la clave pública.
- d)  Un certificado digital contiene una clave pública
  - La afirmación es verdadera, un certificado digital si contiene una clave pública, junto con información sobre el propietario del certificado y la entidad emisora. La clave pública se utiliza para cifrar datos o verificar firmas digitales.
  
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
- Un certificado digital es un documento electrónico que utiliza la infraestructura PKI para vincular una clave pública con la identidad de una persona, organización o dispositivo. El certificado digital es emitido por una autoridad de certificación y contiene información como el nombre del propietario del certificado, la clave pública, la fecha de vencimiento y la firma digital de la CA.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.
- Tiene el nombre del propietario del certificado.
- Tiene la clave pública del propietario del certificado.
- Tiene la fecha de vencimiento del certificado.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre

-   KeyStore\ 
-   clave pública\
-   clave privada

-------------------

- La diferencia entre KeyStore, clave publica y clave privada es la siguiente:
  - KeyStore es como un almacén de claves que puede contener tanto claves públicas como privadas y certificados digitales.
  - La clave pública es una parte de un par de claves que se usa en criptografía asimétrica. Se utiliza para cifrar datos o verificar firmas digitales.
  - La clave privada es la otra parte del par de claves utilizada en criptografía asimétrica. Se usa para descifrar datos cifrados con la clave pública o para crear firmas digitales

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

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

- RSA utiliza criptografía asimétrica, ósea que utiliza la clave pública y la clave privada.
- El funcionamiento de RSA se basa en la dificultad de factorizar grandes números primos, lo que la hace muy segura para el cifrado y la firma digital.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

- a) Cifrar un mensaje confidencial\
  - Para cifrar un mensaje confidencial se utiliza la clave pública, ya que cualquier persona puede usar la clave pública para cifrar un mensaje, pero solo el propietario de la clave privada correspondiente puede descifrarlo.
- b) Firmar digitalmente
  - Para firmar digitalmente se utiliza la clave privada, ya que el propietario de la clave privada puede crear una firma digital que se puede verificar con la clave pública correspondiente. Esto garantiza la autenticidad e integridad del mensaje.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
// Supongo que ya tenemos el par de claves generadas y almacenadas en las variables "pub" y "priv"
Cipher cipher = Cipher.getInstance("RSA");

Cipher.init(Cipher.ENCRYPT_MODE,pub);

byte[] cifrado = cipher.doFinal("Hola".getBytes());

```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```

- La configuración puesta no es segura, ya que 512 bits es una longitud de clave muy corta para RSA, lo que la hace vulnerable a ataques de fuerza bruta. De normal se usan unos 2048 bits o más para que sea realmente segura.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
