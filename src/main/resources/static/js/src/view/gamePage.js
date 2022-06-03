import { html, render } from "../../node_modules/lit-html/lit-html.js";
const urlContinue = "http://localhost:8000/games/continue/";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games/create";
const urlCompare = "http://localhost:8000/games/compare";
let pagination = document.getElementById("pagination");

const gameTemplate = (game, finish, duration) => html`
${finish ? html`<h1 class="text-center">${duration}</h1>` : html`<h1 class="text-center">${game.startDate}</h1>`}
${finish ? html`<h3 class="text-center">${game.serverNumber}</h3>` : html`<h3 class="text-center">? ? ? ?</h3>` }
<div id="cows-and-bulls" ></div>
${!finish ? html`
<div>
<input id="yourNumber" type="text" class="form-control" placeholder="enter your four digits" name="your number" >
<button id="compareBtn" type="submit" class="btn btn-primary">Compare</button>
</div>` : html`<h3 class="text-center">congratulations</h3>
<h3 class="text-center">Attempts: ${game.numberOfAttempts}</h3>`}
`;

const cowsAndBullsTemplate = (cowsAndBulls) => html`
${cowsAndBulls.length == 0 ? null : html`
${cowsAndBulls.map(p => cowsAndBullsCard(p))}`}
`;

const cowsAndBullsCard = (cowsAndBulls) => html`
     <p>${cowsAndBulls.number} ------Bulls: ${cowsAndBulls.bulls} ----- Cows: ${cowsAndBulls.cows}</p>
`;


export async function gamePage() {
         pagination.style.display = "none";
         let finish = false;

    const option = {
        method: "post",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({})
                 }
           
    try {
        let res = await fetch(url, option);
        if(!res.ok){
            throw new Error("Invalid Request!!!");
        }
        let resdata = await res.json();
        render(gameTemplate(resdata, finish), main);
        const cowsAndBullsPage = document.querySelector("#cows-and-bulls");
        render(cowsAndBullsTemplate([]), cowsAndBullsPage);
        const compareBtn = document.querySelector("#compareBtn");
        const yourNumber = document.querySelector("#yourNumber");
        compareBtn.addEventListener("click", compare);
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
                                res = await fetch(urlContinue + resdata.id);
                                resdata = await res.json();
                                finish = true;
                                let second = Math.abs((new Date(resdata.endDate) - new Date(resdata.startDate)) / 1000);
                                let duration = secondsToDhms(second);
                                render(cowsAndBullsTemplate(cowsAndBulls), cowsAndBullsPage);
                                render(gameTemplate(resdata, finish, duration), main);
                                
                            }else{
                            render(cowsAndBullsTemplate(cowsAndBulls), cowsAndBullsPage);
                        }

                        } catch (error) {
                            alert(error.message);
                        }
                         
        }

    } catch (error) {
        alert(error.message);
    }
};

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