import { dashboardPage } from "./view/allUsers.js";
import { loginPage } from "./view/login.js";
import { logout } from "./view/logout.js";
import { registerPage } from "./view/register.js";
import { gamePage } from "./view/continueGamePage.js";
import { allUsersGamePage} from "./view/allUserGames.js"

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
    }else{
        document.getElementById("user").style.display = "none";
        document.getElementById("guest").style.display = "";
    }
}

updateUserNav();

