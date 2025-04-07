
n = 2
n1 = 0
n2 = 0

# Definimos la función sumarDosNumeros
def sumarDosNumeros(n1, n2):
    return n1 + n2

# Definimos la función main
def main():
    # Pedimos al usuario que ingrese dos números
    n1 = int(input("Ingrese el primer número: "))
    n2 = int(input("Ingrese el segundo número: "))

    # Llamamos a la función sumarDosNumeros y mostramos el resultado
    resultado = sumarDosNumeros(n1, n2)
    print(f"La suma de {n1} y {n2} es: {resultado}")