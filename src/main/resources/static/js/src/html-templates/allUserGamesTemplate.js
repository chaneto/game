import { html } from "../../node_modules/lit-html/lit-html.js";

export const gameTemplate = (games, onContinue, onHistory) => html`
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