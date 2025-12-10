## 🧵 Sprint 2 – Servidor multicliente con hilos

### 🎯 Objetivo
Desarrollar un servidor TCP que gestione múltiples clientes simultáneamente mediante hilos.

### ✅ Tareas
- [ ] Crear clase `MultiThreadedServer` que acepte conexiones en un puerto.
- [ ] Cada cliente debe ser gestionado por un hilo diferente.
- [ ] Implementar mensajes tipo: "Bienvenido", "Hasta luego", etc.
- [ ] Permitir que los clientes se conecten desde terminal o clase Java.

### 📦 Entregables
- Código funcional en carpeta `/multihilo`.
- Captura de pruebas con 2 o más clientes conectados.
- Documentación breve en README.

### 📅 Fecha de entrega: 2025

# Resolución del issue 2

## DESCRIPCIÓN

Este Proyecto implementa un servidor TCP con la capacidad de gestionar múltiples clientes mediante el uso de hilos, permitiendo conexiones concurrentes, ya que cada cliente se conecta por un hilo independiente.

## ESTRUCTURA

/multihilo
 ├── ClienteSimple
 ├── ProcesadorDeClientes
 └── ServidorMultihilo

 ## EJECUCIÓN

 Inicio el servidor con Java ServidorMultihilo. Posteriormente ya puedo conectarme como cliente mediante Java ClienteSimple

## Capturas

<img width="624" height="276" alt="image" src="https://github.com/user-attachments/assets/90c1aad8-45ab-4f5c-b1a2-368a8f4f4f64" />

