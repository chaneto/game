import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { gameTemplate } from "../html-templates/gameHistoryTemplate.js"
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games/history/";
let pagination = document.getElementById("pagination");

export async function gamePageHistory(gameId) {
    pagination.style.display = "none";
    let error = false;
           
    try {
        const res = await fetch(url + gameId);
        if(!res.ok){
            error = true;
            const resDataEx = await res.json();
            return render(gameTemplate(resDataEx.description, error), main);
        }
        const resData = await res.json();
        render(gameTemplate(resData, error), main);

    } catch (error) {
        alert(error.message);
    }
};