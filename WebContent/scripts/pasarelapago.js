var regex = /^(\d\s?){15,16}$/; // 16 digitos o de 4 en 4 separados por espacios.

const inputTarjeta = document.querySelector("#numeroTarjeta")
const errorTarjeta = document.querySelector(".errorTarjeta")

function validate() {
    errorTarjeta.innerHTML = !regex.test(inputTarjeta.value) ? 'Numero de tarjeta incorrecto' : "";
}
validate();
document.addEventListener('keyup', validate);