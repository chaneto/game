import { html, render } from "../../node_modules/lit-html/lit-html.js";
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


const gameTemplate = (game, finish, duration) => html`
${finish ? html`<h1 class="text-center">${duration}</h1>` : html`<h1 class="text-center">${game.startDate}</h1>`}
${finish ? html`<h3 style="color:red;" class="text-center">${game.serverNumber}</h3>` : html`<h3 style="color:red;" class="text-center">? ? ? ?</h3>` }
<div id="cows-and-bulls" ></div>
${!finish ? html`
<div class="text-center">
<input id="yourNumber" type="text"   placeholder="enter your four digits" name="your number" >
<button id="compareBtn" type="submit" class="btn-primary">Compare</button>
</div>` : html`<h3 style="color:green;" class="text-center">Congratulations!!!</h3>
<h3 style="color:green;" class="text-center">Attempts: ${game.numberOfAttempts}</h3>`}
</div>
`;

const cowsAndBullsTemplate = (cowsAndBulls) => html`
${cowsAndBulls.length == 0 ? null : html`
<div class="text-center">
${cowsAndBulls.map(p => cowsAndBullsCard(p))}
</div>`}
`;

const cowsAndBullsCard = (cowsAndBulls) => html`
 <p id="result" style="color:blue;" class="text-center" >${cowsAndBulls.number} --------Bulls: ${cowsAndBulls.bulls} ------- Cows: ${cowsAndBulls.cows}</p>
`;


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

        cowsAndBullsPage = document.querySelector("#cows-and-bulls");

        try {
            const resGameHistory = await fetch(urlGameHistory + gameId);
            const resdataGameHistory = await resGameHistory.json();
            if(!resGameHistory.ok){
                throw new Element("Invalid request!!!");
            }
            render(cowsAndBullsTemplate(resdataGameHistory), cowsAndBullsPage);

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
                        throw new Error(cowsAndBulls.description);
                    }
                    if(cowsAndBulls[arrayLength - 1].bulls == 4){
                        const res = await fetch(url + currentId);
                        const resdata = await res.json();
                         finish = true;
                         let second = Math.abs((new Date(resdata.endDate) - new Date(resdata.startDate)) / 1000);
                         let duration = secondsToDhms(second);
                        render(cowsAndBullsTemplate(cowsAndBulls), cowsAndBullsPage);
                        render(gameTemplate(resdata, finish, duration), main);
                    }else{
                    window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);    
                    render(cowsAndBullsTemplate(cowsAndBulls), cowsAndBullsPage);
                }

                } catch (error) {
                    alert(error.message);
                }
                 
}

function secondsToDhms(seconds) {
    seconds = Number(seconds);
    var d = Math.floor(seconds / (3600*24));
    var h = Math.floor(seconds % (3600*24) / 3600);
    var m = Math.floor(seconds % 3600 / 60);
    var s = Math.floor(seconds % 60);
    
    var dDisplay = d > 0 ? d + (d == 1 ? " day, " : " days, ") : "";
    var hDisplay = h > 0 ? h + (h == 1 ? " hour, " : " hours, ") : "";
    var mDisplay = m > 0 ? m + (m == 1 ? " minute, " : " minutes, ") : "";
    var sDisplay = s > 0 ? s + (s == 1 ? " second" : " seconds") : "";
    return dDisplay + hDisplay + mDisplay + sDisplay;
    }