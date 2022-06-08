import { html } from "../../node_modules/lit-html/lit-html.js";

export const dashboardTemplate = (users) => html`
<h1 class="text-center">Users ranking</h1>
<div  class=" mt-3 ">
    <div class="row d-flex d-wrap">
${users.map(p => userCard(p))}
    </div>
</div>
`;

const userCard = (user) => html`
                <div class="card-deck d-flex justify-content-center">
                    <div class="card mb-4 user-card" >
                        <div class="card-body">
                            <h4 style="color:#C20909;" class="card-title">${user.username}</h4>
                        </div>
                        <div class="card">
                            <p style="color:#1E10AD; font-weight: bold;">Completed Games: ${user.numberOfCompletedGames}</p>
                        </div>
                        <div class="card-body">
                        <h5 style="color:Tomato;" class="card-title">Best Scores</h5>
                        </div>
                        <div class="card">
                            <p style="color:#4BAAF5; font-weight: bold">Number Attempts: ${user.bestNumberOfAttempts}</p>
                        </div>
                        <div class="card">
                            <p style="color:#4BAAF5; font-weight: bold">Best Time: ${user.bestTime}</p>
                        </div>
                    </div>
                </div>
`;