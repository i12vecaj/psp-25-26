<div align="center">

# **Parte 2 - Manejar errores**

</div>

<div align="justify">

- Modifica el programa para que intente hacer ping a una dirección inexistente (ejemplo: direcccionInexistente.test).

- Redirige la salida de error al flujo estándar y comprueba lo que ocurre.

</div>

<div align="center">

<img src="https://i.imgur.com/kpq2hOJ.png" alt="Captura de la salida de error al flujo estándar"  />

</div>

## _Significado:_

- El comando falló porque la dirección no existe.

- La salida de error se ha redirigido correctamente, así que aparece junto con el resto del texto.

- El código de salida `2` indica un error (por ejemplo, no se pudo resolver el nombre del host).

## _Referencias que he usado para el ejercicio:_

- [Documentación de redirectErrorStream](https://docs.oracle.com/javase/8/docs/api/java/lang/ProcessBuilder.html#redirectErrorStream-boolean-)
