# EXAMEN -- PROGRAMACIÓN SEGURA EN JAVA

## 2º DAM -- Parte Teórico-Práctica

### Antonio Rodríguez Cortés -- 2026/02/25

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

1. SQL Injection: el programa concatena las variables user y pass directamente en la consulta SQL, lo que permite a un atacante inyectar código malicioso. (inyector sql: ' OR '1'='1 (este ejemplo lo vi hace poco en un video de youtube, que encontraron una una vulnerabilidad en una pagina del estado con esta inyección sql.))

2. Credenciales: "root" / "root" en el código. (visible para cualquiera que vea el codigo)

3. Uso de usuario root: el programa se conecta a la base de datos con el usuario root, lo que es una mala práctica. Debería usar un usuario con permisos limitados.

4. Contraseñas en texto plano: deberia encriptar las contraseñas en la base de datos.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

- Prevención de SQL Injection\
- Buen manejo de credenciales\
- Buen uso de recursos

````java
Aquí tienes el mismo código con comentarios breves:

```java
import java.sql.*;
import java.util.Scanner;

public class LoginSeguro {
    public static void main(String[] args) {
    // Uso del try para asegurar el cierre de recursos
        try (Scanner sc = new Scanner(System.in);

    // Credenciales fuera del código (variables de entorno)
             Connection con = DriverManager.getConnection(
                 System.getenv("DB_URL"),
                 System.getenv("DB_USER"),
                 System.getenv("DB_PASS"));

    // PreparedStatement evita SQL Injection
             PreparedStatement ps = con.prepareStatement(
                 "SELECT 1 FROM usuarios WHERE nombre=? AND password=?")) {

            System.out.println("Usuario:");
            String user = sc.nextLine();

            System.out.println("Password:");
            String pass = sc.nextLine();

    // Parámetros en lugar de concatenar strings
            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println(rs.next() ? "Acceso concedido" : "Acceso denegado");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión");
        }
    }
}
````

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 2 -- Conceptos de programación segura

Responde a las siguientes preguntas:

### Pregunta 2.1

Explica qué es una vulnerabilidad de tipo SQL Injection y cómo se
previene en Java.

Una vulnerabilidad de tipo SQL Injection ocurre cuando un atacante intenta inyecta código mendiente la entrada de datos en una consulta SQL, lo que le puede llegar a permitir acceder a datos sensibles, modificar la base de datos o incluso ejecutar comandos en el servidor.
un ejemplo de SQL Injection es el siguiente:

```sql
SELECT * FROM usuarios WHERE nombre='admin' AND password='123456'
```

esta consulta es muy comun en los sistemas de varias empresas, ya que son datos que se genera de forma automatica.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
Las contraseñas de texto plano son muy facil de robar, ya que si un atacante logra acceder a la base de datos tendria la informacion de todas las contraseñas facilmente.

¿Qué técnica se debe usar en su lugar?
Lo ideal es utilizar tecnicas de encriptación de contraseñas. (generando una linea de caracteres totalmente incrompresible para el humano)

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

El principio de mínimo privilegio es una práctica de seguridad que consiste en otorgar a los usuarios o procesos solo los permisos necesarios para realizar sus tareas, y no más. Esto reduce el riesgo de que un atacante pueda explotar vulnerabilidades o cometer errores que afecten a todo el sistema.

ejemplo en Java:

En lugar de usar el usuario root, se crea un usuario con permisos limitados.

```java
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost/app", "app_user", "app_pass");
```

Con este codigo solo ese usuario tendria acceso a la base de datos, y no tendria permisos de administrador, lo que reduce el riesgo de daños en caso de un ataque o error.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a) PreparedStatement evita SQL Injection\
verdadera, ya que permite parametrizar las consultas y evita la concatenación de strings.

b) RSA es criptografía simétrica\
falsa, RSA es criptografía asimétrica, ya que utiliza un par de claves (pública y privada) para cifrar y descifrar datos.

c) La clave privada puede compartirse públicamente\
falsa, la clave privada debe mantenerse en secreto siempre, ya que con ella podemos descifrar los datos cifrados con la clave pública o firmar digitalmente.

d) Un certificado digital contiene una clave pública
verdadera, un certificado digital contiene la clave pública del titular, así como información sobre su identidad y la entidad certificadora que lo emitió.

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
Un certificado digital facilita mucho a la poblacion la seguridad en internet, ya que con ella podemos realizar tramites de forma segura, como por ejemplo pedir cita medica en ClickSalud o realizar tramites de oposiciones, ya que con el certificado digital podemos identificarnos de forma segura y realizar transacciones de forma segura.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

1. Clave pública del titular del certificado.
2. Información de identidad del titular (nombre, organización, etc.).
3. Información de la entidad certificadora que emitió el certificado (nombre, firma digital, etc.).

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

- KeyStore\
- clave pública\
- clave privada

KeyStore es una clase de Java que almacena claves y certificados de forma segura. Es como una caja fuerte digital donde se guardan las claves y certificados.
Clave pública es una clave que se utiliza para cifrar datos o verificar firmas digitales. Es pública y se puede compartir con cualquier persona.
Clave privada es una clave que se utiliza para descifrar datos cifrados con la clave pública o para firmar digitalmente. Es privada y debe mantenerse en secreto.

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

RSA usa criptografía asimétrica: genera un par de claves una pública y una privada.
Se cifra con la pública y se descifra con la privada; para firmar, se usa la privada y se verifica con la pública.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a) Cifrar un mensaje confidencial\
Para mensajes confidenciales se utiliza la clave pública, ya que cualquier persona puede cifrar el mensaje, pero solo el propietario de la clave privada puede descifrarlo.

b) Firmar digitalmente
Para firmar digitalmente se utiliza la clave privada, ya que solo el propietario de la clave privada puede crear la firma, y cualquier persona con la clave pública solo puede verificarla, pero no crearla.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

```java
Cipher cipher = Cipher.getInstance("RSA");

//__________.init(Cipher.ENCRYPT_MODE, ________);//
cipher.init(Cipher.ENCRYPT_MODE, pub); //añadiendo cipher y pub para cifrar el mensaje con la clave pública.

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

```java
keyGen.initialize(512);
```

No es segura, los 512 bits se queda muy pobres para una seguridad robusta, se deria usar al menos 2048 bits para una configuracion segura.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
