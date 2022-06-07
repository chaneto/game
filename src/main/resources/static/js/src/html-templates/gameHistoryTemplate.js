import { html } from "../../node_modules/lit-html/lit-html.js";

export const gameTemplate = (cowsAndBulls) => html`
<div class="form-group">
${cowsAndBulls.length == 0 ? html` <div class="row d-flex d-wrap">
the game has no attempts
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