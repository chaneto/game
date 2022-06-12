import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { gameTemplate } from "../html-templates/gameHistoryTemplate.js"
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games/history/";
let pagination = document.getElementById("pagination");

export async function gamePageHistory(gameId) {
    pagination.style.display = "none";
           
    try {
        const res = await fetch(url + gameId);
        if(!res.ok){
            const resdataEx = await res.json();
            throw new Error(resdataEx.description);
        }
        const resdata = await res.json();
        render(gameTemplate(resdata), main);

    } catch (error) {
        alert(error.message);
    }
};