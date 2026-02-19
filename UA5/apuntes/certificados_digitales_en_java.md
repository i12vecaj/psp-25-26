<div align="center">

<a href="https://www.youtube.com/watch?v=yiMGZRg_hI8">
    <img src="https://i.imgur.com/XLytJLD.png" style="max-width: 400px; width: 100%;">
</a>

[Enlace al vídeo de Youtube](https://www.youtube.com/watch?v=yiMGZRg_hI8)

</div>

# Certificados Digitales en Java

## 1. El Dilema de la Confianza

- **Fundamento**: En el mundo digital, el gran problema es saber con certeza si estamos interactuando con quien creemos (banco, tienda, usuario). Necesitamos un equivalente al DNI o pasaporte físico.
- **Solución**: El **Certificado Digital**. Es un documento electrónico que ata de forma segura una clave pública a una identidad (persona o empresa), actuando como un puente de confianza.
- **Analogía**: Si la clave pública es la ranura de una caja fuerte (donde cualquiera puede meter mensajes) y la privada es la llave para abrirla, el certificado es el documento pegado a la puerta que verifica, sin dudas, quién es el dueño de esa caja.

## 2. Criptografía: El Motor oculto

- La criptografía moderna va más allá de ocultar mensajes; garantiza autenticidad e integridad basándose en tres pilares:
  1. **Funciones Hash**: Una huella dactilar única para los datos.
  2. **Criptografía Simétrica**: Una única clave para cifrar y descifrar.
  3. **Criptografía Asimétrica**: Un par de claves relacionadas matemáticamente (Pública y Privada).

- **Mecanismo de Firma**:
  - No se cifra el documento entero. Se genera su hash y se cifra solo esa huella con la clave privada del firmante.
  - Cualquiera con la clave pública puede descifrar la huella y compararla para verificar la autoría y que el documento no ha sido alterado

## 3. Implementación en Java (JCA)

- **Framework**: Java utiliza la **JCA (Java Cryptography Architecture)**, una estructura modular basada en proveedores que permite cambiar implementaciones de algoritmos sin tocar el código,.
- **Proceso de Firma (3 Pasos)**:
  1. Inicializar el objeto Signature en modo firma con la clave privada.
  2. Pasar los datos a firmar mediante el método update.
  3. Generar la firma con sign.
- **Proceso de Verificación**: Es el proceso espejo. Se inicializa en modo verificación con la clave pública, se pasan los datos originales y se comprueba la firma con verify, obteniendo un true o false.

### Protección del Dato: Los Tres Pilares

---

- **Objetivo**: Una vez los datos están dentro, hay que garantizar tres aspectos:
  1. Confidencialidad: Solo accede quien debe.
  2. Integridad: Nadie altera los datos sin que nos demos cuenta.
  3. Disponibilidad.
- **Herramientas**:
  - El **cifrado** protege la confidencialidad.
  - Las **firmas digitales y hashes** aseguran la integridad.

## 4. Casos de Uso Real

- **HTTPS**: El candado en el navegador indica el uso de certificados para comunicaciones seguras.
- **Firma de Código**: Garantiza que un software proviene de un desarrollador de confianza.
- **Legalidad e IoT**: Se utiliza para dar validez legal a facturas electrónicas y para identificar dispositivos en el Internet de las Cosas

## 5. Herramientas Clave en Java:

| Clase / Componente   | Descripción / Función                                                                                                         |
| :------------------- | :---------------------------------------------------------------------------------------------------------------------------- |
| **JCA**              | Java Cryptography Architecture. Framework estándar incluido en la JVM que ofrece una API modular para seguridad.              |
| **Signature**        | Clase protagonista que orquesta todo el proceso, tanto para generar la firma como para verificarla.                           |
| **KeyPairGenerator** | Herramienta para generar el par de claves (pública y privada) necesarias para la criptografía asimétrica.                     |
| **RSA**              | Algoritmo estándar utilizado habitualmente por el generador de claves, basado en la dificultad de factorizar números grandes. |
