import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { gameTemplate } from "../html-templates/continueGameTemplate.js";
import { cowsAndBullsTemplate } from "../html-templates/continueGameTemplate.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games/continue/";
const urlCompare = "http://localhost:8000/games/compare";
const urlGameHistory = "http://localhost:8000/games/history/";
const urlCrate = "http://localhost:8000/games/create";
let pagination = document.getElementById("pagination");
let cowsAndBullsPage = "";
let compareBtn = "";
let yourNumber = "";
let currentId = "";
let finish = "";
let status = "";
let currentHistory = "";


export async function gamePage() {
    pagination.style.display = "none";
    finish = false;

const option = {
   method: "post",
   headers: {"Content-Type": "application/json"},
   body: JSON.stringify({})
            }
      
try {
   let res = await fetch(urlCrate, option);
   if(!res.ok){
       throw new Error("Invalid Request!!!");
   }
   let resdata = await res.json();
   currentId = resdata.id;
   render(gameTemplate(resdata, finish), main);
   cowsAndBullsPage = document.querySelector("#cows-and-bulls");
   render(cowsAndBullsTemplate([]), cowsAndBullsPage);
   compareBtn = document.querySelector("#compareBtn");
   yourNumber = document.querySelector("#yourNumber");
   compareBtn.addEventListener("click", compare);
} catch (error) {
   alert(error.message);
}
};


export async function continueGamePage(gameId) {
    currentId = gameId;
    pagination.style.display = "none";
    finish = false;
           
    try {
        const res = await fetch(url + gameId);
        if(!res.ok){
            throw new Error("Invalid Request");
        }
        const resdata = await res.json();
        render(gameTemplate(resdata, finish), main);
        status = "ok";
        cowsAndBullsPage = document.querySelector("#cows-and-bulls");

        try {
            const resGameHistory = await fetch(urlGameHistory + gameId);
            const resdataGameHistory = await resGameHistory.json();
            currentHistory = resdataGameHistory;
            if(!resGameHistory.ok){
                throw new Element("Invalid request!!!");
            }
            render(cowsAndBullsTemplate(resdataGameHistory, currentHistory, status), cowsAndBullsPage);

        } catch (error) {
            alert(error.message);
        }

        compareBtn = document.querySelector("#compareBtn");
        yourNumber = document.querySelector("#yourNumber");
        compareBtn.addEventListener("click", compare);

    } catch (error) {
        alert(error.message);
    }
};


async function compare(event) {
    event.preventDefault();
    status = "ok";
    let data = {
        number: yourNumber.value
                }
            
      const optionCompare = {
              method: "post",
              headers: {"Content-Type": "application/json"},
              body: JSON.stringify(data)
                 }
                 try {
                   const result = await fetch(urlCompare, optionCompare);
                   const cowsAndBulls = await result.json();
                   let arrayLength = cowsAndBulls.length;
                    if(!result.ok){
                     status = "bad request";
                     return render(cowsAndBullsTemplate(cowsAndBulls,currentHistory ,status), cowsAndBullsPage);

                    }
                    if(cowsAndBulls[arrayLength - 1].bulls == 4){
                        const res = await fetch(url + currentId);
                        const resdata = await res.json();
                         finish = true;
                         currentHistory = cowsAndBulls;
                         let second = Math.abs((new Date(resdata.endDate) - new Date(resdata.startDate)) / 1000);
                         let duration = secondsToDhms(second);
                        render(cowsAndBullsTemplate(cowsAndBulls, currentHistory, status), cowsAndBullsPage);
                        render(gameTemplate(resdata, finish, duration), main);
                    }else{
                    window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);
                    currentHistory = cowsAndBulls;
                    render(cowsAndBullsTemplate(cowsAndBulls, currentHistory, status), cowsAndBullsPage);
                }

                } catch (error) {
                    alert(error.message);
                }
                 
}

export function secondsToDhms(seconds) {
    seconds = Number(seconds);
    var d = Math.floor(seconds / (3600*24));
    var h = Math.floor(seconds % (3600*24) / 3600);
    var m = Math.floor(seconds % 3600 / 60);
    var s = Math.floor(seconds % 60);
    
    var dDisplay = d > 0 ? d + (d == 1 ? " d, " : " d, ") : "";
    var hDisplay = h > 0 ? h + (h == 1 ? " h, " : " h, ") : "";
    var mDisplay = m > 0 ? m + (m == 1 ? " m, " : " m, ") : "";
    var sDisplay = s > 0 ? s + (s == 1 ? " s" : " s") : "";
    return dDisplay + hDisplay + mDisplay + sDisplay;
    }

export function getSeconds(endDate, startDate){
return Math.abs((new Date(endDate) - new Date(startDate)) / 1000);
}
