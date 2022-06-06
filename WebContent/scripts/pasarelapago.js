var regex = /^(\d\s?){15,16}$/; // 16 digitos o de 4 en 4 separados por espacios.
var regexemail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
var regexcaducidad = /^(0[1-9]|1[0-2])\/?([0-9]{2})$/;
const inputTarjeta = document.querySelector("#numeroTarjeta")
const errorTarjeta = document.querySelector(".errorTarjeta")
const errorEmail = document.querySelector(".errorEmail")
const email = document.querySelector(".email");
const cvv = document.querySelector("#cvv");
const errorCVV = document.querySelector(".errorCVV")
const errorCaducidad = document.querySelector(".errorCaducidad");


const inputemail = document.querySelector("#inputemail");
const inputnumeroTarjeta = document.querySelector("#inputnumeroTarjeta");
const inputcaducidad = document.querySelector("#inputcaducidad");
const inputcvv = document.querySelector("#inputcvv");
const inputtitular = document.querySelector("#inputtitular");
const caducidad = document.querySelector("#caducidad");
const titular = document.querySelector("#titular");

const formulariotarjeta = document.querySelector("#formulariotarjeta");
const buttonEnviar = document.querySelector(".enviar");





function validate() {


    if(regex.test(inputTarjeta.value)){
        errorTarjeta.innerHTML = "";
        inputnumeroTarjeta.value = inputTarjeta.value;
        return true;
    }else{
        errorTarjeta.innerHTML = "Numero de tarjeta incorrecto";
        return false;
    }

    



}

function validateEmail(){



    if(regexemail.test(email.value)){
        errorEmail.innerHTML = "";
        inputemail.value = email.value;
        return true;
    }else{
        errorEmail.innerHTML = "Email incorrecto";
        return false;
    }
    


}

function validateCVV(){

    if(cvv.value.length >2 && cvv.value.length <5){
        errorCVV.innerHTML = "";
        inputcvv.value = cvv.value;
        return true;
    }else{
        errorCVV.innerHTML = "CVV incorrecto";
        return false;
    }

    


}

function validatecaducidad(){

    if(regexcaducidad.test(caducidad.value)){
        errorCaducidad.innerHTML = "";
        inputcaducidad.value = caducidad.value;
        return true;
    }else{
        errorCaducidad.innerHTML = "fecha incorrecta";
        return false;
    }

    


}



inputTarjeta.addEventListener('keyup', validate);
email.addEventListener('keyup', validateEmail );
cvv.addEventListener('keyup', validateCVV );
caducidad.addEventListener('keyup', validatecaducidad );
titular.addEventListener('keyup', (e) => {
    if(titular.value != ""){
        inputtitular.value = titular.value;
    }
})

buttonEnviar.addEventListener("click" , (e) => {
    e.preventDefault();

console.log(inputemail.value)
console.log(inputnumeroTarjeta.value)
console.log(inputcaducidad.value)
console.log(inputcvv.value)
console.log(inputtitular.value)


    if(inputemail.value != "" && inputnumeroTarjeta.value != "" && inputcaducidad.value != "" && inputcvv.value != "" && inputtitular.value != "" ){

        formulariotarjeta.submit();

    }


})