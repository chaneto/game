import { html, render } from "../../node_modules/lit-html/lit-html.js";
const main = document.getElementById("home-page");

const errorTemplate = (error) => html`
<div class="jumbotron jumbotron-fluid text-light" style="background-color: #343a40;">
<img src="/img/index.png"
     class="img-fluid" alt="Responsive image" style="width: 150%; height: 200px">
<h1 class="display-4">ERROR</h1>
 <p class="lead">${error.description}</p>
 <p class="lead">${error.message}</p>
 <p class="lead">${error.statusCode}</p>
 <p class="lead">${error.timestamp}</p>
</div>
`;


export async function errorPage(error) {

        render(errorTemplate(error), main);
   
};