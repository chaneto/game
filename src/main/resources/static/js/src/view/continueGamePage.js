import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { errorPage } from "./errorPage.js";
import { allUsersGamePage } from "./allUserGames.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games/continue/";
const urlCompare = "http://localhost:8000/games/compare";
const urlGameHistory = "http://localhost:8000/games/history/";


const gameTemplate = (game) => html`
<h1 class="text-center">${game.serverNumber}</h1>
<div id="cowsAndBulls" class=" mt-3 ">
</div>
<div class="form-group">
<label for="username">please enter your 4 digit number</label>
<input id="yourNumber" type="text" class="form-control" placeholder="your number" name="your number" >
<button id="compareBtn" type="submit" class="btn btn-primary">Compare</button>
</div>
`;

const cowsAndBullsTemplate = (cowsAndBulls) => html`
<div  class=" mt-3 ">
${cowsAndBulls.length == 0 ? null : html` <div class="row d-flex d-wrap">
${cowsAndBulls.map(p => cowsAndBullsCard(p))}
</div>`}
</div>
`;


const cowsAndBullsCard = (cowsAndBulls) => html`
                <div class="card-deck d-flex justify-content-center">
                    <div class="card mb-4" >
                        <div class="card-body">
                            <h4 class="card-title">${cowsAndBulls.number}</h4>
                        </div>
                        <div class="card">
                            <p>Cows: ${cowsAndBulls.cows}</p>
                        </div>
                        <div class="card">
                            <p>Bulls: ${cowsAndBulls.bulls}</p>
                        </div>
                    </div>
                </div>
`;

export async function gamePage(gameId) {
           
    try {
        const res = await fetch(url + gameId);
        const resdata = await res.json();
        if(res.status != 201){
            return errorPage(resdata);
        }
        render(gameTemplate(resdata), main);
        const cowsAndBullsPage = document.querySelector("#cowsAndBulls");

        try {
            const resGameHistory = await fetch(urlGameHistory + gameId);
            const resdataGameHistory = await resGameHistory.json();
            if(resGameHistory.status != 200){
                return errorPage(resdataGameHistory);
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
                            if(result.status != 200){
                                return errorPage(cowsAndBulls);
                            }
                            if(cowsAndBulls[arrayLength - 1].bulls == 4){
                                return allUsersGamePage();
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