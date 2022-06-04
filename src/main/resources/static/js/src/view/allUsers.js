import { html, render } from "../../node_modules/lit-html/lit-html.js";
const main = document.getElementById("home-page");
const url = "http://localhost:8000/users/all";
let pagination = document.getElementById("pagination");

const dashboardTemplate = (users) => html`
<h1 class="text-center">Users ranking</h1>
<div  class=" mt-3 ">
    <div class="row d-flex d-wrap">
${users.map(p => userCard(p))}
    </div>
</div>

`;

const userCard = (user) => html`
                <div class="card-deck d-flex justify-content-center">
                    <div class="card mb-4" >
                        <div class="card-body">
                            <h4 class="card-title">${user.username}</h4>
                        </div>
                        <div class="card">
                            <p>Completed Games: ${user.numberOfCompletedGames}</p>
                        </div>
                        <div class="card-body">
                        <h3 class="card-title">Best Game</h3>
                        </div>
                        <div class="card">
                            <p>Number Attempts: ${user.bestNumberOfAttempts}</p>
                        </div>
                        <div class="card">
                            <p>Best Time: ${user.bestTime}</p>
                        </div>
                    </div>
                </div>
`;

export async function dashboardPage() {
    pagination.style.display = "none";
           
    try {
        const res = await fetch(url);
        if(!res.ok){
            throw new Error("Invalid request!!!");
        }
        const users = await res.json();
        render(dashboardTemplate(users), main);
    } catch (error) {
        alert(error.message);
    }
};