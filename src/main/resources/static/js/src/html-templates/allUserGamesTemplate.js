import { html } from "../../node_modules/lit-html/lit-html.js";
import { getSeconds } from "../view/continueGamePage.js";
import { secondsToDhms } from "../view/continueGamePage.js";

export const gameTemplate = (games, onContinue, onHistory) =>
html`
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
                    <div class="card mb-4 user-card" >
                        <div class="card">
                            <p style="color:#900C3F; font-weight: bold">Start Time: ${game.startDate}</p>
                        </div>
                        <div class="card">
                            <p style="color:#C20909; font-weight: bold">End Time: ${game.endDate}</p>
                        </div>
                        <div class="card">
                            <p style="color:#C70039; font-weight: bold">Number Attempts: ${game.numberOfAttempts}</p>
                        </div>
                        <div class="card">
                        ${game.completed ? html`<p style="color:#FF5733; font-weight: bold">Is Completed: yes</p>` : html`<p style="color:#FF5733; font-weight: bold">Is Completed: no</p>`}
                        </div>
                       <div class="card">
                       ${!game.completed ? html`<a style="color:#0EEA4A; font-weight: bold" @click=${onContinue}  class="btn btn-primary" value=${game.id}>Continue</a>` :
                       html`<p style="color:#FF5733; font-weight: bold">Duration: ${secondsToDhms(getSeconds(game.endDate, game.startDate))}</p>`}
                        </div>
                      <div class="card">
                        <a style="color:#0EEA4A; font-weight: bold" @click=${onHistory}  class="btn btn-primary" value=${game.id}>History</a>
                         </div>
                    </div>
                </div>
`;