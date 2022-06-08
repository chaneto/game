import { html } from "../../node_modules/lit-html/lit-html.js";

export const gameTemplate = (game, finish, duration) => html`
<div id="game-image-right">
<img src="../img/img3.png" class="img-fluid" alt="Responsive image" style="width: 330px; height: 250px">
</div>
${finish ? html`<h1 class="text-center">${duration}</h1>` : html`<h1 class="text-center">${game.startDate}</h1>`}
${finish ? html`<h3 style="color:red;" class="text-center">${game.serverNumber}</h3>` : html`<h3 style="color:red;" class="text-center">? ? ? ?</h3>` }
<div id="cows-and-bulls" ></div>
${!finish ? html`
<div class="text-center">
<input id="yourNumber" type="text"   placeholder="enter your four digits" name="your number"/>
<button id="compareBtn" type="submit" class="btn-primary">Compare</button>
</div>` : html`<h3 style="color:green;" class="text-center">Congratulations!!!</h3>
<h3 style="color:green;" class="text-center">Attempts: ${game.numberOfAttempts}</h3>`}
</div>

<div id="game-image-left">
<img src="../img/logo.png" class="img-fluid" alt="Responsive image" style="width: 330px; height: 250px">
</div>
`;

export const cowsAndBullsTemplate = (cowsAndBulls, currentHistory, status) => html`
${cowsAndBulls.length == 0 ? null : html`
<div class="text-center"> ${currentHistory.map(p => cowsAndBullsCard(p))}
</div>`}
${status != "ok" ? html`<small id="quantityError" class="form-text bg-danger rounded">${cowsAndBulls.description}</small>` : null}
`;

const cowsAndBullsCard = (cowsAndBulls) => html`
 <p id="result" style="color:blue;" class="text-center">${cowsAndBulls.number} --------Bulls: ${cowsAndBulls.bulls} ------- Cows: ${cowsAndBulls.cows}</p>
`;