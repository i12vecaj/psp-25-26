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

1. En caso de fallar, da error y el programa se cierra automáticamente.
2. El usuario y la contraseña son texto plano, no están cifrados.
3. Ambos campos pueden quedarse vacíos, no hay nada que controle eso.
4. No se usa "try-catch".

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos

```
import java.sql.*;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) throws Exception {

        try { //Ahora utiliza un try-catch

            Scanner sc = new Scanner(System.in);

                System.out.print("Usuario:");
                String user = sc.nextLine();

                    /* Mejora en como se muestra por pantalla la
                    recogida de datos*/

                System.out.print("\nPassword:");
                String pass = sc.nextLine();


                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost/app", "root", "root");

                Statement st = con.createStatement();

                String query = "SELECT * FROM usuarios WHERE nombre='"
                        + user + "' AND password='" + pass + "'";

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {

                    System.out.println("\nAcceso concedido");

                } else {

                    System.out.println("\nAcceso denegado, intentelo de nuevo");

                }

                con.close();
                
                sc.close(); // Se cierra el scanner al finalizar

        } catch (Exception e){ // En caso de fallar se dice cual es el error

            System.out.println("\nError: " + e);

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

- Una vulnerabilidad de este tipo implica que un usuario (que no debería poder usar SQL) pueda ejecutarlo, pudiendo "romper" el programa o recopilar datos privados.

- En java, se utiliza PreparedStatement para evitar que el usuario pueda ejecutar SQL.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

- Es una mala práctica debido a que cualquiera que pueda acceder al documento puede ver las contraseñas almacenadas, lo que es una grave vulneración de seguridad. Por lo tanto, 
lo que se debe hacer es encriptar la contraseña para que lo que se vea en el archivo sea ilegible.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.


- Este principio dice que a cada usuario se le debe dar la cantidad mínima de permisos necesarios para evitar problemas, ya sean intencionados o no. Por ejemplo:

  Si a una persona que solo debe de buscar información en una BBDD sql (SELECT) le das permiso para que pueda también borrar datos de esta, es posible que borre accidentalmente
  los datos de la BBDD al intentar acceder a estos por accidente.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a)  PreparedStatement evita SQL Injection\ -Verdadero, PreparedStatement puede usarse para evitar SQL Injection.

b)  RSA es criptografía simétrica\ - Falso, es criptografía asimétrica, ya que tiene tanto clave pública como clave privada.

c)  La clave privada puede compartirse públicamente\ -Falso, la clave privada solo debe tenerla aquel/aquellos que el emisor quiera que puedan desencriptar el mensaje.

d)  Un certificado digital contiene una clave pública - Verdadero, contiene tanto clave publica como privada (criptografía asimétrica).

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

- Un certificado digital es un documento que acredita que el usuario es quien afirma ser, este documento debe de estar validado por un tercero de confianza, que debe ser una Autoridad de Certificación (CA).

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

Contiene:
	
- Un número de serie.
- Una entidad emisora.
- Los datos del propietario.
- Un periodo de validez.
- Un algoritmo de firma.
- Etc.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

-   KeyStore\  - Almacena las claves.

-   clave pública\  - Criptografía asimétrica. El emisor usa una clave pública para encriptar los mensaje y el receptor usa una clave privada para desencriptarlos.

-   clave privada  - Criptografía simétrica. El emisor y el receptor usan una misma clave privada para cifrar y descifrar el mensaje.

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

- Usa criptografía asimétrica, primero genera una clave pública, la cual se utiliza para encriptar el mensaje, y luego se utiliza una clave privada para desencriptarlo.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a)  Cifrar un mensaje confidencial\ - Se usa una clave pública.
b)  Firmar digitalmente - Se usa una clave privada.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
Cipher cipher = Cipher.getInstance("RSA");

cipher.init(Cipher.ENCRYPT_MODE, cifrado);

byte[] cifrado = cipher.doFinal("Hola".getBytes());

```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```

- No, no lo es, para empezar, el formato debería ser: "keyGen.initialize(512, "numero");", además, en vez de ser 512, podría ser mayor, como 2048. Así sería más seguro.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
