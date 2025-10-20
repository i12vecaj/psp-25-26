from reportlab.lib.pagesizes import A4
from reportlab.pdfgen import canvas
import os
import shutil

# IVA fijo
IVA = 0.21

def generar_factura(numero_factura, items):
    subtotal = sum(cant * precio for _, cant, precio in items)
    iva_total = subtotal * IVA
    total = subtotal + iva_total

    # Nombre del archivo PDF
    archivo = f"factura_{numero_factura}.pdf"

    # Crear lienzo PDF
    c = canvas.Canvas(archivo, pagesize=A4)
    c.setFont("Helvetica", 12)

    # Encabezado
    c.drawString(50, 800, f"Factura N° {numero_factura}")
    c.drawString(50, 780, "Detalle de items:")

    # Escribir los items
    y = 760
    for nombre, cant, precio in items:
        linea = f"{nombre} | Cant: {cant} | Precio: {precio:.2f}€ | Subtotal: {cant*precio:.2f}€"
        c.drawString(50, y, linea)
        y -= 20

    # Totales
    c.drawString(50, y-20, f"Subtotal: {subtotal:.2f}€")
    c.drawString(50, y-40, f"IVA ({IVA*100:.0f}%): {iva_total:.2f}€")
    c.drawString(50, y-60, f"TOTAL: {total:.2f}€")

    # Guardar PDF
    c.save()

    # Crear carpetas para copias
    carpetas = ["./Copias", "./Clientes"]
    for carpeta in carpetas:
        os.makedirs(carpeta, exist_ok=True)
        shutil.copy(archivo, carpeta)

    print(f"✅ Factura generada: {archivo}")
    print("Copias guardadas en: ./Copias y ./Clientes")

# -------------------
# Ejemplo de uso
items = [
    ("Producto A", 2, 50.0),
    ("Producto B", 1, 30.0),
    ("Producto C", 3, 15.0),
]

generar_factura("0001", items)
