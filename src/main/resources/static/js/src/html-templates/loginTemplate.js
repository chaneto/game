import { html } from "../../node_modules/lit-html/lit-html.js";

export const loginTemplate = (status, resData) => html`
<div id="login-image">
<img src="../img/img2.jpg" class="img-fluid" alt="Responsive image" style="width: 330px; height: 200px">
</div>

<form id="login-form" class="border border-light p-5" method="POST">
<h1>LOGIN</h1>
${status != "ok" ? html`<small id="quantityError" class="form-text bg-danger rounded">${resData}</small>` : null}
<div class="form-group">
    <label for="username">Username</label>
    <input id="username" type="text" class="form-control" placeholder="Username" name="username"/>
</div>
<div class="form-group">
    <label for="password">Password</label>
    <input id="password" type="password" class="form-control" placeholder="Password" name="password"/>
</div>
<button id="loginBtn" type="submit" class="btn btn-primary">Login</button>
</form>`;