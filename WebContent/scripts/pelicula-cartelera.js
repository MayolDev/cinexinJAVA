const horas = document.querySelector('#horas')
const fechas = document.querySelector('#fecha')
const entradas = document.querySelector('.entradas')


const HandleClick = (e) => {

    e.preventDefault();

    const value = (JSON.parse(e.target[0].value));

    fetch('/cinexin/api?peticion=sesion2&id_pelicula=' + value.id_pelicula + '&id_sala=' + value.id_sala + '&fecha=' + value.fecha)
    .then(response => response.json())
    .then((data) => {
        
        horas.innerHTML= "";


        data.map((sesion) => {
            if(sesion !== null){
                const option = document.createElement('option');
                option.value = '{"id" : ' + sesion.id +' , "id_sala":"'+value.id_sala+'" , "fecha":"'+value.fecha+'" , "hora_entrada":"'+sesion.hora_entrada+'"}';
                option.innerHTML = sesion.hora_entrada;
                horas.appendChild(option);
            }

        })

        if(data != null){
            entradas.disabled = false;
        }
    
    })


}