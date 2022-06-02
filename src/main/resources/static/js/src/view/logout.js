import { updateUserNav } from "../app.js";
import { dashboardPage } from "./allUsers.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/logout";



export async function logout() {
    let userdata = JSON.parse(sessionStorage.getItem("userdata"));
    const option = {
        method: "get",
        headers: {['x-Authorization']: userdata.token}
    };
   
    try {
        const res = await fetch(url);
        if(res.status != 200){
            throw new Error("invalid request please try again");
          }
          sessionStorage.removeItem("userdata");
          updateUserNav();
          return dashboardPage();
         
    } catch (error) {
        alert(error.message);
    }
};