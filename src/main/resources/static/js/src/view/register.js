import { updateUserNav } from "../app.js";
import { loginPage } from "./login.js";
import { html, render } from "../../node_modules/lit-html/lit-html.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/register";
let pagination = document.getElementById("pagination");

const registerTemplate = () => html`
  <form id="register-form" class="text-center border border-light p-5" method="POST">
            <h1>REGISTER</h1>
            <div class="form-group">
                <label for="username">Username</label>
                <input id="username-register" type="text" class="form-control" placeholder="Username" name="username" >
            </div>
            <div class="form-group">
                <label for="password">Password</label>
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
    render(registerTemplate(), main);
    let username = document.getElementById("username");
    let password = document.getElementById("password");
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
                      throw new Error(resData.description);
                    }
                   updateUserNav();
                   return loginPage();
                   
               } catch (err) {
                  alert(err.message);
               }
            }
};

