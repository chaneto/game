import { html } from "../../node_modules/lit-html/lit-html.js";

export const gameTemplate = (cowsAndBulls) => html`
<div class="form-group">
${cowsAndBulls.length == 0 ? html` <div class="row d-flex d-wrap">
<h3 class="text-center" style="color:#1E10AD;">The game has no attempts!!!</h3>
</div>` : html` <div class="row d-flex d-wrap">
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