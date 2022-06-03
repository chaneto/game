import { html, render } from "../../node_modules/lit-html/lit-html.js";
import { gamePage } from "./continueGamePage.js";
import { gamePageHistory } from "./gameHistory.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/games";
let pagination = document.getElementById("pagination");
let pageNumber = document.getElementById("pageNumber");
let next = document.getElementById("next");
let previous = document.getElementById("previous");
let pegaNumberCurrent = 1;

const gameTemplate = (games, onContinue, onHistory) => html`
<h1 class="text-center">Your Games</h1>
<div  class=" mt-3 ">
${games.length != 0 ? html`<div class="row d-flex d-wrap">
     ${games.map(p => gameCard(p, onContinue,onHistory))}
    </div>` : html`<div class="row d-flex d-wrap">
    You have no games played
   </div>`}
</div>
`;

const gameCard = (game, onContinue, onHistory) => html`
                <div class="card-deck d-flex justify-content-center">
                    <div class="card mb-4" >
                        <div class="card">
                            <p>Start Time: ${game.startDate}</p>
                        </div>
                        <div class="card">
                            <p>End Time: ${game.endDate}</p>
                        </div>
                        <div class="card">
                            <p>Number Attempts: ${game.numberOfAttempts}</p>
                        </div>
                        <div class="card">
                        ${game.completed ? html`<p>Is Completed: yes</p>` : html`<p>Is Completed: no</p>`}
                        </div>
                       <div class="card">
                       ${!game.completed ? html`<a @click=${onContinue}  class="btn btn-primary" value=${game.id}>Continue</a> ` : null}
                        </div>
                      <div class="card">
                        <a @click=${onHistory}  class="btn btn-primary" value=${game.id}>History</a>
                         </div>
                    </div>
                </div>
`;

export async function allUsersGamePage() {

    pegaNumberCurrent = pageNumber.textContent;
       await viewProducts(pegaNumberCurrent - 1, onContinue, onHistory);
        pagination.style.display = "block";

}

async function onContinue(e){
    let gameId = e.target.getAttribute("value");
    return gamePage(gameId);
 }

async function onHistory(h) {
    let gameId = h.target.getAttribute("value");
   return gamePageHistory(gameId);
}

previous.addEventListener("click", onPrevious);
function onPrevious(prev){
       prev.preventDefault();
       pegaNumberCurrent = Number(pageNumber.textContent) - 1;
            if(pegaNumberCurrent <= 1){
                  previous.style.display = "none";
              }else{
                  previous.style.display = "block";
                   }
                  pageNumber.textContent =  pegaNumberCurrent;
                  next.style.display = "block";
                  viewProducts(pegaNumberCurrent - 1);
               }


next.addEventListener("click", onNext);
function onNext(nex){
  nex.preventDefault();
  pegaNumberCurrent = Number(pageNumber.textContent) + 1;
   if(pegaNumberCurrent > 9){
       next.style.display = "none";
   }else{
       next.style.display = "block";
        }
       pageNumber.textContent =  pegaNumberCurrent;
       previous.style.display = "block";
       viewProducts(pegaNumberCurrent - 1);
        }


        async function viewProducts(page){

    try {
        const res = await fetch(url + "?page=" + page + "&pageSize=4");
        if(!res.ok){
            throw new Error("Invalid request!!!");
        }
        const games = await res.json();
        pageNumber.textContent =  pegaNumberCurrent;
        render(gameTemplate(games, onContinue, onHistory), main);
         } catch (error) {
            alert(error.message);
        }
  }       



