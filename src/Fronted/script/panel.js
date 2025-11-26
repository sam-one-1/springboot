const token = localStorage.getItem("jwt");

if(!token){
    window.location.href = "index.html";

}

async function loadPanel(){
    try{
        const res = await fetch("http://localhost:8081/admin/panel", {
            headers:{
                "Authorization": "Bearer "+token
            }
        });

        if(!res.ok){
            document.getElementById("panelMsg").innerText = "Acceso denegado.";
            return;
        }

        const data = await res.json();

        const form = document.getElementById("jsonForm");
        form.innerHTML = "";

        for(const key in data){
            const label = document.createElement("label");
            label.innerText = key;

            const input = document.createElement("input");
            input.value = data[key];
            input.readOnly = true;

            form.appendChild(label);
            form.appendChild(input);
        }




    }catch(e){
        document.getElementById("panelMsg").innerText = "Error conectando al servidor.";
    }
}

function logout(){
    localStorage.removeItem("jwt");
    window.location.href = "index.html";
}

loadPanel();