import { updateUserNav } from "../app.js";
import { dashboardPage } from "./allUsers.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/logout";
let pagination = document.getElementById("pagination");

export async function logout() {
    pagination.style.display = "none";
   
    try {
        const res = await fetch(url);
        if(!res.ok){
            throw new Error("Invalid request!!!");
          }
          sessionStorage.removeItem("userdata");
          updateUserNav();
          return dashboardPage();
         
    } catch (error) {
        alert(error.message);
    }
};