CadastroForm = document.getElementById('cadastro_form');
CadastroForm.addEventListener('submit', async function(event){
    event.preventDefault();
    const username = document.getElementById('user_input').value;
    const email = document.getElementById('email_input').value;
    const nome = document.getElementById('nome_input').value;
    const senha = document.getElementById('senha_input').value;

    const usuario = {
        username:username,
        email:email,
        nome:nome,
        senha:senha,
        sexo:null,
        idade:null
    };

    try{
        const response = await fetch("http://localhost:8080/api/usuarios/cadastrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(usuario)
        });

        console.log(response);

        if (response.ok){
            console.log("[RESPONSE OK]");
            const result = await response.json()
            alert(result.message);
            console.log(result.message);
        }
        else{
            console.log("[ERROR]");
            const error = await response.json();
            alert(error.message);
            console.error(error.message);
        }
    }
    catch(error){
        console.error("Erro no servidor:" + error);
        alert(error);
    }
});