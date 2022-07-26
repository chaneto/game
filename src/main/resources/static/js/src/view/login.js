import { updateUserNav } from "../app.js";
import { allUsersGamePage } from "./allUserGames.js";
import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { loginTemplate } from "../html-templates/loginTemplate.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/login";
let pagination = document.getElementById("pagination");

export async function loginPage() {
    pagination.style.display = "none";
    let status = "ok";
    render(loginTemplate(status), main);
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    let loginBtn = document.getElementById("loginBtn");
    loginBtn.addEventListener("click", onLogin);
  async  function onLogin(event) {
     event.preventDefault();
    let data = {
        username: username.value,
        password: password.value
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
                       return render(loginTemplate(status, resData.description), main);
                     }
                     
                     const userdata = {
                        username: resData.username,
                        id: resData._id,
                        token: resData.accessToken
                    }

                    sessionStorage.setItem("userdata", JSON.stringify(userdata));
                    document.cookie = "username=" + resData.username + "; path=/; max-age=" + 30 * 60;
                    updateUserNav();
                    username.value = "";
                    password.value = "";
                    return allUsersGamePage();
                    
                } catch (err) {
                   alert(err.message);
                }
            }
};
