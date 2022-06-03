import { html, render } from "../../node_modules/lit-html/lit-html.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games/continue/";
const urlCompare = "http://localhost:8000/games/compare";
const urlGameHistory = "http://localhost:8000/games/history/";
let pagination = document.getElementById("pagination");


const gameTemplate = (game, finish, duration) => html`
<h1 class="text-center">${game.startDate}</h1>
${finish ? html`<h3 class="text-center">${game.serverNumber}</h3>` : html`<h3 class="text-center">? ? ? ?</h3>` }
<div id="cows-and-bulls" ></div>
${!finish ? html`
<div>
<input id="yourNumber" type="text" class="form-control" placeholder="enter your four digits" name="your number" >
<button id="compareBtn" type="submit" class="btn btn-primary">Compare</button>
</div>` : html`<h3 class="text-center">Congratulations!!!</h3>
<h3 class="text-center">Attempts: ${game.numberOfAttempts}</h3>`}
`;

const cowsAndBullsTemplate = (cowsAndBulls) => html`
${cowsAndBulls.length == 0 ? null : html`
${cowsAndBulls.map(p => cowsAndBullsCard(p))}`}
`;

const cowsAndBullsCard = (cowsAndBulls) => html`
  <p>${cowsAndBulls.number} ------Bulls: ${cowsAndBulls.bulls} ----- Cows: ${cowsAndBulls.cows}</p>
`;


export async function gamePage(gameId) {
    pagination.style.display = "none";
    let finish = false;
           
    try {
        let res = await fetch(url + gameId);
        if(!res.ok){
            throw new Error("Invalid Request");
        }
        let resdata = await res.json();
        render(gameTemplate(resdata, finish), main);
        const cowsAndBullsPage = document.querySelector("#cows-and-bulls");

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
                                 res = await fetch(url + gameId);
                                 resdata = await res.json();
                                 finish = true;
                                 let duration = resdata.endDate - resdata.startDate;
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