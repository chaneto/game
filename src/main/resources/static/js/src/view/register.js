import { updateUserNav } from "../app.js";
import { loginPage } from "./login.js";
import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { registerTemplate } from "../html-templates/registerTemplate.js"
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/register";
let pagination = document.getElementById("pagination");


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
                    }
                   updateUserNav();
                   return loginPage();
                   
               } catch (err) {
                  alert(err.message);
               }
            }
};

