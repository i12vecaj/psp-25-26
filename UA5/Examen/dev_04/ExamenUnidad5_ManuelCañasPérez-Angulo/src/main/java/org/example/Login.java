package org.example;
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
//Pregunta 1.1

//1. Vulnerabilidad de SQL Injection:
// El código construye la consulta SQL conectando directamente las entradas del usuario.
// sin ningún tipo de validación.
// a datos no autorizados o manipular la base de datos.

//2. Almacenamiento de contraseñas en texto plano:
// El código asume que las contraseñas de los usuarios se almacenan en texto plano en la base de datos,
// lo cual es una mala práctica de seguridad.

//3. Uso de credenciales hardcodeadas:
// El codigo tiene las credenciales de la base de datos usuario y contraseña -> harcodeadas

/// *************************************************************************************/
//
//Pregunta 1.2

//Prevención de SQL Injection\
//Uso de PreparedStatement para evitar SQL Injection
// Validación de entradas del usuario
// Uso de hashing para las contraseñas
//Buen manejo de credenciales\
// Uso de variables de entorno para las credenciales de la base de datos
//Buen uso de recursos
///*************************************************************************************/
//
//BLOQUE 2 -- Conceptos de programación segura
//
//Pregunta 2.1
// Esta ocurre cuando un atacante puede insertar consultas SQL a través de la entrada del usuario.
// Esto permite que el atacante pueda acceder a datos no autorizados.
// En java esto se puede prevenir utilizando prepareStatement en lugar de concatenar directamente las entradas
// del usuario en las consultas SQL. as del usuario en la consulta SQL. Evita que el código malicioso sea interpretado
// como parte de la consulta SQL.
///*************************************************************************************/
//
//Pregunta 2.2
//1.¿?
// Almacenar las contraseñas en texto plano es una mala practica porque si esta es atacada,
// el atacante una vez haya comprometido esta base de datos tendrá acceso a todas las contraseñas de los usuarios que estén registrados
//2.¿?
// Uso del hashing para las contraseñas, en vez de guardar la contraseña en texto plano, se almacena un hash de la contraseña.
///*************************************************************************************/
//
//Pregunta 2.3
//¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en bases de datos.
//
///*************************************************************************************/
//
//Pregunta 2.4
//
//a) PreparedStatement evita SQL Injection
// Verdadero
//b) RSA es criptografía simétrica
// Falso, RSA es un algoritmo de criptografía, que utiliza la clave privada y publica, para cifrar y descifrar mensajes.
//c) La clave privada puede compartirse públicamente
// Falso, la clave privada no debe de compartirse, ya que solo se usa para descifrar mensajes cifrados.
//d) Un certificado digital contiene una clave pública
//BLOQUE 3 -- Certificados digitales
//Observa el siguiente código:
//
/// *************************************************************************************/
//
///*************************************************************************************/
//
//Pregunta 3.1
// Es un documento electrónico que utiliza la infraestructura de la clave pública para vincular una clave pública con la identidad de una persona, organización o dispositivo.
// Se utiliza para autenticar la identidades y establecer comunicaciones seguras desde la red, como en el caso de HTTPS, correo y firmas digitales.
///*************************************************************************************/
//
//Pregunta 3.2
//
//1. Clave pública: Contiene la clave pública del titular.
//2. Identidad del titular: Información sobre la identidad del titular, como
// su nombre, organización y dirección de correo electrónico.
///*************************************************************************************/
//
//Pregunta 3.3
//
//KeyStore es un repositorio seguro para guardar claves y certificados digitales.
//Clave pública se utiliza para cifrar mensajes y verificar firmas digitales, esta clave puede ser compartida públicamente.
//Clave privada se utiliza para descifrar mensajes cifrados y crear firmas digitales, esta clave debe no puede ser compartida
// tiene que mantenerse en secreto y solo la puede tener el propietario.
///*************************************************************************************/


///*************************************************************************************/
// Analiza el siguiente código:
//
//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
//keyGen.initialize(1024);
//
//KeyPair pair = keyGen.generateKeyPair();
//
//PublicKey pub = pair.getPublic();
//PrivateKey priv = pair.getPrivate();

//Pregunta 4.1
// RSA es criptografía de tipo asimétrica, en esta se utiliza una clave pública y una clave privada.
// La clave pública sirve para cifrar mensajes, mientras que la clave privada sirve para descifrar los mensajes.
/// *************************************************************************************/
//
//Pregunta 4.2
//
//a) Cifrar un mensaje confidencial
// Para cifrar se utiliza la clave pública, ya que esta es la que se comparte
// y permite que cualquier persona pueda cifrar un mensaje para el propietario de la clave privada.
//b) Firmar digitalmente
// Para firmar digitalmente se utiliza la clave privada, ya que esta es la que solo el propietario
// tiene acceso lo que hace que se confirme su autenticidad.
//
///*************************************************************************************/
//
//Pregunta 4.3

///*************************************************************************************/
//
//Pregunta 4.4
// Esta configuración no es segura, ya que 512 bits es muy corta para RSA,
// lo que hace que sea vulnerable a ataques de fuerza bruta ya que al ser mas corta tiene menos combinaciones que resolver.