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

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.1

Identifica al menos 4 vulnerabilidades o malas prácticas de seguridad en
el código.

Explica brevemente cada una.

1. **Inyección SQL**: En este código podemos ver cómo se concatenan directamente las variables `user` y `pass` en la sentencia SQL. Esto permite que un posible atacante manipule la consulta, pudiendo saltarse la autenticación sin conocer las credenciales reales.

2. **Se almacena la contraseña en texto plano**: En la consulta se puede ver cómo no se siguen estándares de seguridad (debería hacerse mediante funciones hash) y se busca la contraseña directamente en la tabla de usuarios, comparándola con el texto introducido.

3. **Manejo inseguro de credenciales de base de datos**: El código incluye el usuario (`"root"`) y la contraseña (`"root"`) directamente en el código fuente (hardcoded). Esto expone las credenciales a cualquier persona con acceso al código y dificulta la gestión segura de accesos.

4. **Falta de gestión de recursos y excepciones**: No se utiliza un bloque try o finally para asegurar el cierre de la conexión en caso de error. Además, lanza `Exception` de forma genérica en el `mai`n, lo que no permite un control específico de los fallos.

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

public class LoginSeguro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Usuario:");
        String user = sc.nextLine();
        System.out.println("Password:");
        String pass = sc.nextLine();

        // Uso de try para la gestión automática de recursos, estas claves jamás las subiría, pero simulan lo que contendría. Se alojarían en el .env
        String url = "jdbc:mysql://localhost:3306/app_db";
        String dbUser = "admin_prod"; // En un entorno real, usaríamos variables de entorno como las que estoy aplicando
        String dbPass = "K9#fL2z!pX8vQ";

        String query = "SELECT password_hash FROM usuarios WHERE nombre=?";

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Aquí se debería comparar el hash (ej. BCrypt) no el texto plano
                    // Por brevedad del ejercicio, simulo la validación
                    System.out.println("Usuario encontrado, verificando hash...");
                } else {
                    System.out.println("Acceso denegado");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión o consulta");
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

Una vulnerabilidad de tipo SQL Injection se da cuando un atacante inserta comandos SQL maliciosos en los campos de entrada de una aplicación. El programa no diferencia entre los datos del usuario y las instrucciones del programador, por lo que el atacante puede leer, modificar o borrar la BBDD a su gusto.

En Java se previene utilizando la interfaz `PreparedStatement`. Esta técnica utiliza placeholders que separan la estructura de la consulta de los datos. De esta forma, el motor de la BBDD trata la entrada del usuario estrictamente como un valor literal y no como código ejecutable (neutralizando cualquier intento de manipulación).

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

Almacenar contraseñas en texto plano es una muy mala práctica porque el atacante puede obtener acceso a cualquiera de las cuentas si la BBDD se ve comprometida. Además, muchos usuarios suelen reutilizar las contraseñas, por lo que la identidad del usuario puede verse comprometida en otras plataformas.

La técnica que se debe utilizar es mediante funciones Hash (como SHA-256). Un hash es una "huella digital" de una sola vía en la que el usuario intenta loguearse, se calcula el hash de la contraseña introducida y después se compara con el hash que se ha almacenado. Esto garantiza la integridad y confidencialidad, ya que es prácticamente imposible revertir el hash al texto original.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

El principio de mínimo privilegio implica que cualquier usuario, proceso o módulo debe tener acceso únicamente a su información y recursos necesarios para la función que fue creada. El límite de esos permisos reduce su exposición en caso de que el sistema se vuelva vulnerable.

Un ejemplo que podemos encontrar en BBDD es al crear un usuario específico para una aplicación que solo tenga permisos de SELECT e INSERT en tablas concretas, en lugar de usar el usuario "root" que tiene el control total sobre el servidor. Concretamente, en la App Luna lo usábamos poro ejemplo para dar permisos específicos a los profesores únicamente para poder publicar, modificar o borrar un post en la pizarra.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a) **PreparedStatement evita SQL Injection**

_Verdadero_. Separa los datos de la lógica de la consulta haciendo uso de parámetros y evitando que la entrada se interprete como código SQL.

b) **RSA es criptografía simétrica**

_Falso_. RSA es un algoritmo de criptografía asimétrica por usar un par de claves (pública y privada) matemáticamente relacionadas, pero a su vez distintas.

c) **La clave privada puede compartirse públicamente**

_¡¡Falso!!_. La clave privada es la base de la seguridad asimétrica y solo debe ser conocida por su usuario para poder desencriptar los mensajes o firmar.

d) **Un certificado digital contiene una clave pública**

_Verdadero_. El certificado digital vincula y certifica la clave pública de una entidad con su identidad.

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

Un certificado digital es un documento electrónico, que vincula de forma segura una una identidad (persona, empresa o dispositivo) con una clave pública. Actúa como un "DNI digital" emitida oficialmente en la que ambas partes confían.

Se utiliza principalmente para garantizar la autenticidad (saber que alguien es quien dice ser, con la confidencialidad), la integridad de los datos (que no hayan sido alterados), que no sea rechazado y que estén disponibles los datos. Es esencial en comunicaciones HTTPS, trámites administrativos online y firmas electrónicas de documentos para darles validez legal.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

Un certificado digital contiene información crítica para establecer confianza. Incluye datos sobre la entidad y la clave.

Algunos elementos que contiene:

1. **La clave pública del propietario**: El componente que se necesita para que otros puedan cifrar mensajes dirigidos a él o verificar si es su firma.
2. **Nombre del emisor**: La entidad de confianza que ha validado y firmado el certificado.
3. **Período de validez**: Las fechas de inicio y de fin que indican la duración legítima del certificado.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

- KeyStore\
- clave pública\
- clave privada

La **KeyStore** es fichero donde se guardan las claves y certificados de forma protegida. En el caso de la **clave pública**, las claves se distribuyen libremente para que los demás cifren datos dirigidos a otros o para verificar la firma digital, mientras que en la **clave privada** la clave es secreta y se usa para desencriptar lo que otros han cifrado con la clave pública o para realizar firmas digitales.

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

RSA utiliza criptografía asimétrica. Se basa en la generación de un par de claves: una pública y otra privada. Su seguridad parte de la complejidad matemática de factorizar números primos muy grandes. Lo que se cifra con una clave solo puede descifrarse con la otra del mismo par.

Su funcionamiento consiste en que un emisor usa la clave pública del receptor para cifrar el mensaje. Después, solo el que posee la clave privada (el receptor) puede recuperar el texto original, garantizando así la confidencialidad.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a) **Cifrar un mensaje confidencial**. Se usa la clave pública del destinatario.

b) **Firmar digitalmente**. Se usa la clave privada del emisor (el que firma).

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

Esta configuración no es segura. Un tamaño de clave de 512 bits se considera obsoleto y vulnerable debido a la capacidad de cómputo actual, lo que permite que un atacante pueda obtener la clave privada a partir de la pública mediante la "fuerza bruta".

Para que la configuración RSA se considere robusta actualmente habría que usar un mínimo de 2048 bits aunque en entornos de aprendizaje se suele usar 1024 bits para equilibrar rendimiento y aprendizaje.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
