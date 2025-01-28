LoginForm = document.getElementById('login_form')

LoginForm.addEventListener('submit', function(event){
    event.preventDefault();

    const username = document.getElementById('login_input').value;
    const senha = document.getElementById('senha_input').value;

    const usuario = {
        username:username,
        senha:senha
    }

    fetch('http://localhost:8080/api/auth/login' , {
        method:'POST',
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
    .then(response=>{
        if(response.ok){
            return response.text();
        }
        else{
            throw new Error('Usuário ou senha inválidos.')
        }
    })
    .then(data=>{
        console.log(data);
        alert('Login bem-sucedido');
    })
    .catch(error=>{
        console.error(error);
        alert(error.message);
    });
});