import { updateUserNav } from "../app.js";
import { loginPage } from "./login.js";
import { html, render } from "../../node_modules/lit-html/lit-html.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/register";
let pagination = document.getElementById("pagination");

const registerTemplate = (status, resData) => html`
   <div id="login-image">
            <img src="../img/index.png" class="img-fluid" alt="Responsive image" style="width: 330px; height: 250px">
        </div>
  <form id="register-form" class="border border-light p-5" method="POST">
            <h1>REGISTER</h1>
             ${status != "ok" ? html`<small id="quantityError" class="form-text bg-danger rounded">${resData}</small>` : null}
            <div class="form-group">
                <label for="username-register">Username</label>
                <input id="username-register" type="text" class="form-control" placeholder="Username" name="username" >
            </div>
            <div class="form-group">
                <label for="password-register">Password</label>
                <input id="password-register" type="password" class="form-control" placeholder="Password" name="password">
            </div>
            <div class="form-group">
            <label for="passwordConfirm">Password Confirm</label>
            <input id="passwordConfirm" type="password" class="form-control" placeholder="Password Confirm" name="passwordConfirm">
            </div>
            <button id="registerBtn" type="submit" class="btn btn-primary">Register</button>
        </form>
`;


export async function registerPage() {
    pagination.style.display = "none";
    let status = "ok";
    render(registerTemplate(), main);
    let username = document.getElementById("username-register");
    let password = document.getElementById("password-register");
    let passwordConfirm = document.getElementById("passwordConfirm");
    let registerBtn = document.getElementById("registerBtn");
    registerBtn.addEventListener("click", onRegister);
  async  function onRegister(event) {
     event.preventDefault();
     let data = {
        username: username.value,
        password: password.value,
        confirmPassword: passwordConfirm.value
               }
           
     const option = {
        method: "post",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
                   }
   
       try {

                   const res = await fetch(url, option);
                   const resData = await res.json();
                  if(!res.ok){
                      status = "bad request";
                      return render(registerTemplate(status, resData.description), main);
                      throw new Error(resData.description);
                    }
                   updateUserNav();
                   return loginPage();
                   
               } catch (err) {
                  alert(err.message);
               }
            }
};

