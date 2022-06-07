import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { dashboardTemplate } from "../html-templates/allUsersTemplate.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/all";
let pagination = document.getElementById("pagination");

export async function dashboardPage() {
    pagination.style.display = "none";
           
    try {
        const res = await fetch(url);
        if(!res.ok){
            throw new Error("Invalid request!!!");
        }
        const users = await res.json();
        render(dashboardTemplate(users), main);
    } catch (error) {
        alert(error.message);
    }
};