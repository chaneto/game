import { dashboardPage } from "./view/allUsers.js";
import { loginPage } from "./view/login.js";
import { logout } from "./view/logout.js";
import { registerPage } from "./view/register.js";
import { gamePage } from "./view/continueGamePage.js";
import { allUsersGamePage} from "./view/allUserGames.js"

let welcome = document.getElementById("welcome-name");

let newGameBtn = document.querySelector("#newGame");
newGameBtn.addEventListener("click", gamePage);

let yourGameBtn = document.querySelector("#games");
yourGameBtn.addEventListener("click", allUsersGamePage);

let registerBtn = document.querySelector("#register");
registerBtn.addEventListener("click", registerPage);

let allUsers = document.querySelector("#dashboard");
allUsers.addEventListener("click", dashboardPage);

let loginBtn = document.querySelector("#login");
loginBtn.addEventListener("click", loginPage);

let logoutBtn = document.querySelector("#logout");
logoutBtn.addEventListener("click", logout);


export function updateUserNav() {
    let userdata = JSON.parse(sessionStorage.getItem("userdata"));
    if(userdata){
      document.getElementById("user").style.display = "";
      document.getElementById("guest").style.display = "none";
      welcome.textContent = "Hello " + userdata.username + "!!!";
      document.getElementById("welcome").style.display = "";
    }else{
        document.getElementById("user").style.display = "none";
        document.getElementById("guest").style.display = "";
        document.getElementById("welcome").style.display = "none";
    }
}

//let homeBtn = document.querySelector("#home");
//homeBtn.addEventListener("click", updateUserNav());

function checkCookie() {
    let dataCookie = document.cookie
    if(dataCookie != "") {
    return dataCookie.split("=")[1];
    } else {
    return null;
    }
}

let cookies = checkCookie();

  const userdata = {
   username: cookies
    }

if(cookies != null){
sessionStorage.setItem("userdata", JSON.stringify(userdata));
}

async function onIsLogged(params) {
  let res = await fetch("http://localhost:8000/isLogged");
  return await res.json();
}

let isLogged = await onIsLogged();
if(!isLogged){
  try {
    const res = await fetch("http://localhost:8000/logout");
    if(!res.ok){
        throw new Error("Invalid request!!!");
      }
      sessionStorage.removeItem("userdata");
     
} catch (error) {
    alert(error.message);
}
}
updateUserNav();


