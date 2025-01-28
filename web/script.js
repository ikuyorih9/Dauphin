LoginForm = document.getElementById('login_form')

LoginForm.addEventListener('submit', function(event){
    event.preventDefault();

    const user = document.getElementById('login_input').value;
    const senha = document.getElementById('senha_input').value;

    console.log(user);
    console.log(senha);
});