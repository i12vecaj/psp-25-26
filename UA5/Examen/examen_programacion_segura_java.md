# EXAMEN -- PROGRAMACIÓN SEGURA EN JAVA

## 2º DAM -- Parte Teórico-Práctica

------------------------------------------------------------------------

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 1 -- Análisis de código inseguro

Analiza el siguiente código Java:


El código aparéntemente intenta acceder a una abse de datos y hacer consulta para iniciar sesión de manera poco segura, divido mi explicación por puntos.

    1.  Tenemos problemas en usuario contraseña, no tenemos ningun tipo de hash cifrado etc... esto provoca una brecha
        de seguridad y en la privacidad del usuario.ç

    2.  No veo ningun trycatch, al no tenerlo cualquier fallo puede dar problemas sobre todo al intentar realizar conexiones, siempre
        es aconsejable usarlos tanto en formularios como en intentos de conexiones o consultas SQL.

        Ejemplo corto:
        try{
            System.out.println("Usuario:");
        String user = sc.nextLine();
        }catch(Exception e){
            System.out.println("Error al introducir usuario: "+e.getMessage);
        }

    3.  Tenemos otro fallo en el acceso, usar ese if puede resultar algo ambiguo, necesitaría tambien un trycatch.
    4.  Usar una base de datos para autentificar no es una buena idea, es mejor utilizar un servidor dedicado a ello
        que utilice algún tipo de criptografía o como mínimo lo mencionado anteriormente, usar almenos un hash para no mandar datos sensibles por la red.

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

    1.  Una de las más típicas y menos imaginadas por nosotros los novatos, dejar información delicada en el código como accesos y claves a APIs o bases de datos, cualquiera que vea el código o lo tenga puede acceder y provocar enormes destrozos y perdidas.

    2.  Confiar en terceros no es una práctica aconsejable, en caso de hacerlo almenos tener algún tipo de respaldo en caso de fallos o alternativas en caso de que no sean fiables.

    3.  Pensar que el usuário nunca cometerá errores, el usuário puede tener comportamientos inesperados que pueden romper el programa, siempre tenemos que pensar que TODO se puede romper e implementar la seguridad al respecto.

    4.  Dentro de nuestra propia base de datos NUNCA debemos guardar información sensibles SIN CIFRAR, no solo por que puedan estar sin cifrar si no que además podemos perderlas si se dan ciertas circunstancias.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos


Mi idea era pasar la contraseña a otro formato, en la base de datos leerlo y de esta manera, no pasa por la red nuestros datos en bruto.


public class Login {

    public static void main(String[] args) throws Exception {

        MessageDigest md;
        Scanner sc = new Scanner(System.in);

        try{
        System.out.println("Usuario:");
        String user = sc.nextLine();

        System.out.println("Password:");
        String pass = sc.nextLine();
        } catch(Exception e){
            System.out.println("Error al introducir usuario: "+e.getMessage);
        }

        try{
            md = MessageDigest.getInstance("SHA-256");
			String texto = "Esto es un texto plano.";
			byte dataBytes[] = texto.getBytes();
			md.update(dataBytes);// SE INTRODUCE TEXTO A RESUMIR
			byte resumen[] = md.digest();

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/app", "root", "root");

            Statement st = con.createStatement();

            String query = "SELECT * FROM usuarios WHERE nombre='"
                    + user + "' AND md='" + pass + "'";

            ResultSet rs = st.executeQuery(query);

            if(rs.next()) {
            System.out.println("Acceso concedido");
        } else {
            System.out.println("Acceso denegado");
        }

        con.close();

        } catch(Exception e){
            System.out.println("Error al recoger información de la base de datos: "+e.getMessage);
        }
        

        
    }
}

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 2 -- Conceptos de programación segura

Responde a las siguientes preguntas:

### Pregunta 2.1

Explica qué es una vulnerabilidad de tipo SQL Injection y cómo se
previene en Java.

Una vulnerabilidad SQL Injection es cuando tratamos de introducir información inválida mediante SQL, para ello tenemos que intentar que a la hora de recoger datos en un formulario, los datos se encuentren en el formato correcto.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

No es buena idea almacenarlas en texto plano porque las claves sin cifrar son mas accesibles para las personas malintencionadas, pueden perderse y en caso de estar en un fichero txt por ejemplo, cualquiera es capaz robarlas.

Para solucionar este problema es recomendable utilizar almenos algún hash, de esta manera al ser el hash un cambio en fórmulas matemáticas, el descifrado de vuelta es lo que nos permite utilizarlas.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.
Con esto se refiere a mantener:
    1.  Confidencialidad: asegurar que solo puede entrar las personas responsables.
    2.  Integridad: manejar y asegurar que los datos no se pierdan corrompan o sean robados.
    3.  Disponibilidad: que cualquier usuario tenga acceso a sus datos en el momento que lo requiera.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a)  PreparedStatement evita SQL Injection\                  Verdadera
b)  RSA es criptografía simétrica\                          Falsa
c)  La clave privada puede compartirse públicamente\        Falsa
d)  Un certificado digital contiene una clave pública       Verdadera

Falsa b) Es un tipo de criptografía asimétrica

Falsa c) Una clave privada no puede ser compartida pues nos permite desencriptar, esta clave suele ser compartida por receptor y emisor si estamos hablando de CRIPTOGRAFÍA SIMÉTRICA.

Verdadera d) Un certificado digital tiene una clave pública que nos permite identificar de forma segura al usuario.


/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 3 -- Certificados digitales

Observa el siguiente código:
Este código parece crear una llave JKS, utilizar un archivo y abrir un certificado mediante una llave pública para luego imprimir por consola.
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

Un certificado digital es una identificación propia, se encuentra vinculada a una entidad o persona (como un DNI por así decirlo) y usarla para identificarse en aplicaciones u otro varios que veremos a continuación.

    1.  Realizar trámites administrativos como en agencias tributarias o seguridad social de manera única que asegura integridad de los datos.
    2.  Podemos firmar documentos electrónicos utilizando nuestra firma digital directamente al ser única.
    3.  En casos mas concretos podremos descifrar datos recibidos que solo sean para el usuario que tiene la firma correspondiente.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

Algunos de los elementos más importantes de sus componentes son la firma, entidad y datos del propietario.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

-   KeyStore\

Keystore se refiere a datos lo cuales encriptamos en fórmulas matemáticas.

-   clave pública\

La clave pública es una compartida con todo el mundo pero nadie puede saber su contenido o el contenido del mensaje, para ello es necesario desencriptarlo y es mas bien propia de un emisor que envía un mensaje.

-   clave privada

Esta clave solo es conocida por uno mismo, el receptor, normalemente se utiliza junto la clave pública de manera que llega el emnsaje encriptado y el receptor con su clave privada, puede desencriptar y leer.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 4 -- Criptografía RSA en Java

Analiza el siguiente código:

Este código utiliza cifrado asimétrico RSA, genera una clave RSA y luego en base a esta, genera clave pública y privada.

``` java
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(1024);

KeyPair pair = keyGen.generateKeyPair();

PublicKey pub = pair.getPublic();
PrivateKey priv = pair.getPrivate();
```

### Pregunta 4.1

¿Qué tipo de criptografía utiliza RSA? Explica brevemente cómo funciona.
RSA utiliza la criptografía asimétrica, el emisor lanza su clave privada, llega al receptor y con la clave privada lo recoge, enviandole de vuelta al primero repitiendo el proceso a la inversa.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a)  Cifrar un mensaje confidencial\
    Es mejor simétrica ya que de esta manera, ambas partes tienen solo la clave privada.
b)  Firmar digitalmente
    Utiliza una clave pública por lo tanto, sería mejor usar la asimétrica.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
Cipher cipher = Cipher.getInstance("RSA");

__________.init(Cipher.ENCRYPT_MODE, ________);

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
