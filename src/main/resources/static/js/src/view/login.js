import { updateUserNav } from "../app.js";
import { allUsersGamePage } from "./allUserGames.js";
import { html, render } from "../../node_modules/lit-html/lit-html.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/login";
let pagination = document.getElementById("pagination");

const loginTemplate = (status, resData) => html`

        <div id="login-image">
            <img src="../img/index.png" class="img-fluid" alt="Responsive image" style="width: 330px; height: 200px">
        </div>

  <form id="login-form" class="border border-light p-5" method="POST">
            <h1>LOGIN</h1>
            ${status != "ok" ? html`<small id="quantityError" class="form-text bg-danger rounded">${resData}</small>` : null}
            <div class="form-group">
                <label for="username">Username</label>
                <input id="username" type="text" class="form-control" placeholder="Username" name="username" >
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input id="password" type="password" class="form-control" placeholder="Password" name="password">
            </div>
            <button id="loginBtn" type="submit" class="btn btn-primary">Login</button>
        </form>
`;

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
                       throw new Error(resData.description);
                     }
                     
                     const userdata = {
                        username: resData.username,
                        id: resData._id,
                        token: resData.accessToken
                    }
                    sessionStorage.setItem("userdata", JSON.stringify(userdata));
                    updateUserNav();
                    username.value = "";
                    password.value = "";
                    return allUsersGamePage();
                    
                } catch (err) {
                   alert(err.message);
                }
            }
};
