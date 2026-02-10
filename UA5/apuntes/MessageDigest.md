<div align="center">

# Class MessageDigest:

[Enlace a la documentación oficial](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/security/MessageDigest.html?utm_source=chatgpt.com)

</div>

```sh
java.lang.Object
	java.security.MessageDigestSpi
        java.security.MessageDigest

public abstract class MessageDigest
extends MessageDigestSpi
```

Esta clase `MessageDigest` proporciona a las aplicaciones la funcionalidad de un algoritmo de resumen de mensajes (message digest), como SHA-1 o SHA-256. Los resúmenes de mensajes son funciones hash seguras de una sola vía que toman datos de tamaño arbitrario y devuelven un valor hash de longitud fija.

Un objeto `MessageDigest` se inicia en un estado inicializado. Los datos se procesan a través de él utilizando los métodos [`update()`](<https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/security/MessageDigest.html?utm_source=chatgpt.com#update(byte)>). En cualquier momento se puede llamar a [`reset()`](<https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/security/MessageDigest.html?utm_source=chatgpt.com#reset()>) para restablecer el resumen. Una vez que se han actualizado todos los datos necesarios, se debe llamar a uno de los métodos [`digest()`](<https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/security/MessageDigest.html?utm_source=chatgpt.com#digest()>) para completar el cálculo del hash.

El método `digest` puede llamarse una vez para un número determinado de actualizaciones. Tras llamar a `digest`, el objeto `MessageDigest vuelve` a su estado inicializado.

Las implementaciones son libres de implementar la interfaz Cloneable. Las aplicaciones cliente pueden verificar si un objeto es clonable intentando realizar la clonación y capturando la excepción `CloneNotSupportedException`:

```sh
 MessageDigest md = MessageDigest.getInstance("SHA-256");

 try {
     md.update(toChapter1);
     MessageDigest tc1 = md.clone();
     byte[] toChapter1Digest = tc1.digest();
     md.update(toChapter2);
     ...etc.
 } catch (CloneNotSupportedException cnse) {
     throw new DigestException("couldn't make digest of partial content");
 }
```

Nota: Si una implementación específica no es clonable, todavía es posible calcular resúmenes intermedios instanciando varias instancias, siempre que el número de resúmenes se conozca de antemano.

Ten en cuenta que esta clase es abstracta y extiende de `MessageDigestSpi` por razones históricas. Los desarrolladores de aplicaciones solo deben prestar atención a los métodos definidos en esta clase `MessageDigest`; todos los métodos de la superclase están destinados a los proveedores de servicios criptográficos que deseen suministrar sus propias implementaciones de algoritmos de resumen de mensajes.

Cada implementación de la plataforma Java tiene la obligación de soportar los siguientes algoritmos estándar de `MessageDigest`:

- SHA-1
- SHA-256

Estos algoritmos se describen en la [`sección MessageDigest`](https://docs.oracle.com/en/java/javase/24/docs/specs/security/standard-names.html#messagedigest-algorithms) de la Especificación de Nombres de Algoritmos Estándar de Seguridad de Java. Consulta la documentación de tu implementación para ver si se admiten otros algoritmos.

**Desde: 1.1**

**Ver también**: [`DigestInputStream`](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/security/DigestInputStream.html), [`DigestOutputStream`](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/security/DigestOutputStream.html)
