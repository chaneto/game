import { updateUserNav } from "../app.js";
import { dashboardPage } from "./allUsers.js";
const main = document.getElementById("home-page");
let pageNumber = document.getElementById("pageNumber");
const url = "http://localhost:8000/logout";
let pagination = document.getElementById("pagination");

export async function logout() {
    pagination.style.display = "none";
   
    try {
        const res = await fetch(url);
        if(!res.ok){
            throw new Error("Invalid request!!!");
          }
          localStorage.removeItem("userdata");
          document.cookie = "username=; max-age=0";
          pageNumber.textContent = 1;
          updateUserNav();
          return dashboardPage();
         
    } catch (error) {
        alert(error.message);
    }
};