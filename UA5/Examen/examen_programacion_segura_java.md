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
# Respuesta de la pregunta 1
Es un programa que pide por pantalla al usuario el nombre, la contraseña. 

Crea una conexion con una base de datos llamada **app**, y la contraseña y el usuario es "root", después de tener la conexión prepara una consulta que selecciona toda la información de la tabla usuarios donde el nombre y la contraseña concuerden con lo que ha escrito el usuario por pantalla. 

Ejecuta la query y si el resultado es el correcto es decir, si el nombre de usuario y la contraseña son "root", permite el acceso pero si esa query falla y no encuentra datos de esa tabla de usuarios, no te permite el acceso.

/
\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.1

Identifica al menos 4 vulnerabilidades o malas prácticas de seguridad en
el código.

Explica brevemente cada una.

## Respuesta de la pregunta 1.1
- En primer lugar, una vulnerabilidad grave es tener como nombre de usuario y contraseña de la base de datos la palabra "root", ya que es la palabra común, y es muy simple para poder entrar en la base de datos.
- En segundo lugar, en la base de datos, cuando hace una consulta tiene las contraseñas desencriptadas, es decir si llegase el caso de que el sistema esta corrupto, quien entrase en esa base de datos tiene la contraseña y el nombre de usuario de cualquiera.
- En tercer lugar, no hay un control de excepciones cuando se realiza una conexión a la base de datos, ya que puede ocurrir de haber escrito mal el nombre de usuario por ejemplo, y que salte un error a la hora de crear la conexión, si eso pasase, el error lo que produciría es que el programa se corte por ese error.
- En cuarto lugar, cuando se ejecuta la query, tampoco hay un control de exepciones, ya que esa query puede saltar un error por no estar bien escrita o por equivocación a la hora de escribir algo. Eso también provocaría que el sistema falle y se pare. 
  
En conclusión, por parte de vulnerabilidades he encontrado dos muy importantes, y de malas prácticas. 

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos
-   
## Resultado de la pregunta 1.2
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
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/app", "root", "root");
            try{
                Statement st = con.createStatement();

                String query = "SELECT * FROM usuarios WHERE nombre='"
                        + user + "' AND password='" + pass + "'";

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    System.out.println("Acceso concedido");
                } else {
                    System.out.println("Acceso denegado");
                }
                con.close();
            } catch (Exception e) {
                System.err.println("Error al cargar consulta");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar de la base de datos");
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

## Respuesta pregunta 2.1
Es cuando por ejemplo se contruye una consulta concatenando entrada del usario sin parametrizar. 
Este ejemplo se podría colocar en un login.


/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

## Respuesta pregunta 2.2
Si, porque si tenemos vulnerabilidades en el sistema, y tengamos un sistema corrupto, si no nos anticipamos antes, podríamos tener problemas de que esas contraseñas se hagan públicas. Y además que no garantizariamos una conficiencialidad en los usuarios que entren a nuestra aplicación. 

La técnica sería la criptografía para poder encriptar esas contraseñas, con librerias como JCA o JCE, para poder encriptar datos importantes como contraseñas, y además usar criptografía asimétrica, donde se utiliza tanto una clave pública comouna privada y además con funciones hash.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

## Respuesta pregunta 2.3

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a)  PreparedStatement evita SQL Injection\
b)  RSA es criptografía simétrica\ 
c)  La clave privada puede compartirse públicamente\
d)  Un certificado digital contiene una clave pública

## Resultado de pregunta 2.4
a)Verdadero, con un control de excepciones además controla ese error y esa vulnerabilidad
b)Falsa, porque RSA se utiliza para criptografía asimétrica, donde se generan dos claves, una privada y una pública
c)Falsa, la clave privada no se debe de compartir, ya que nuestro sistema dejaría de ser seguro
d)Verdadero, contiene una clave pública donde el receptor calcula el hash del mensaje recibido, descifra la firma usando la clave publica del emisor y después compara ambos hashes


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

## Respuesta pregunta 3.1
Explica qué es un certificado digital y para qué se utiliza.
El certificado digital es un documento que certifica la clave pública de una entidad. Puede pertenecer a una persona, empresa etc. Se usa para identificarse como persona etc, puede llegar a servir para identificarse, firmar para presentar unos documentos y certificar tu identidad.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

## Respuesta pregunta 3.2
- Nombre del propietario del certificado digital
- El número de serie
- La clave pública del propietario

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

-   KeyStore\
-   clave pública\
-   clave privada
  
## Respuesta pregunta 3.3
La clave pública es la clave la cual puede saber las demás personas, que servirá para poder comparar por ejemplo en el certificado digital, por otro lado esta la clave privada la cual si hay que tener más cuidado ya que si esa clave se hiciera pública el sistema estaría vulnerable y habría peligro de futuros ataques al sistema.

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
## Respuesta pregunta 4
En este código se crea una clave privada, con el algoritmo "RSA", y después pone un tamaño de 1024.
Después de crear la clave privada, crea una pública. 
Y una vez creadas las dos claves, las guarda en dos variables: **pub** y **priv**.

La conclusión sería que este código crea dos claves, una pública y otra privada.

### Pregunta 4.1

¿Qué tipo de criptografía utiliza RSA? Explica brevemente cómo funciona.

## Respuesta pregunta 4.1
Una criptografía asimétrica.

Esta criptografía consite en que cada participante tiene un par de claves:
  - **Clave pública**: se puede compartir.
  - **Clave privada**: solo la conoce el propietario.
Se cifra la clave pública y se descifra con la privada. En esta criptografía permite por ejemplo la autentificación en un sistema

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a)  Cifrar un mensaje confidencial\
b)  Firmar digitalmente


## Respuesta pregunta 4.2
a)la función hash
b)criptografía asimétrica
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
Cipher cipher = Cipher.getInstance("RSA");

__________.init(Cipher.ENCRYPT_MODE, ________);

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

## Respuesta pregunta 4.3
En este código le hace falta dos cosas, primero para cifrar el mensaje llamar a la variable cipher, y después hay que pasarle como argumentos, el modo de encripación y por otro lado la clave para poder encriptar ese mensaje recibido. 

```java
Cipher cipher = Cipher.getInstance("RSA");

cipher.init(Cipher.ENCRYPT_MODE, KeyGenerator.getInstance("RSA").generateKey());

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```
## Respuesta pregunta 4.4
En mi opinión creo que no. Este trozo de código lo que hace es colocarle el tamaño fijo de 512, y normalmente se le suele poner un tamaño de 2048, y contra más pequeño el tamaño la clave es más corta y es más fácil atacarla.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
