const selectCantidadNormal = document.querySelector("#cantidad-normal");
const selectCantidadNino = document.querySelector("#cantidad-nino");
const total_dinero = document.querySelector(".total-dinero");
const ninos = document.querySelector("#ninos");
const normal = document.querySelector("#normal");
const enviar = document.querySelector(".enviar")
let cantidad_normal = 0;
let cantidad_nino = 0;
let total = 0;

selectCantidadNormal.addEventListener("change", (e) => {
    cantidad_normal = e.target.value;

    fetch('/cinexin/api?peticion=precio&cantidad_normal=' + cantidad_normal + '&cantidad_nino=' + cantidad_nino )
    .then(response => response.json())
    .then(data => {

        console.log(data);
        total_dinero.innerHTML= data + "€"
        total = data;
        normal.value = cantidad_normal;

        if(total > 0){
            enviar.disabled= false;
        }
    })


})

selectCantidadNino.addEventListener("change", (e) => {
    cantidad_nino = e.target.value;

    fetch('/cinexin/api?peticion=precio&cantidad_normal=' + cantidad_normal + '&cantidad_nino=' + cantidad_nino )
    .then(response => response.json())
    .then(data => {

        console.log(data);
        total_dinero.innerHTML= data + "€"
        total = data;
        ninos.value = cantidad_nino;
        if(total > 0){
            enviar.disabled= false;
        }
    })


})


