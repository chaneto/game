import { html } from "../../node_modules/lit-html/lit-html.js";

export const registerTemplate = (status, resData) => html`
   <div id="login-image">
            <img src="../img/img1.png" class="img-fluid" alt="Responsive image" style="width: 330px; height: 250px">
        </div>
  <form id="register-form" class="border border-light p-5" method="POST">
            <h1>REGISTER</h1>
             ${status != "ok" ? html`<small id="quantityError" class="form-text bg-danger rounded">${resData}</small>` : null}
            <div class="form-group">
                <label for="username-register">Username</label>
                <input id="username-register" type="text" class="form-control" placeholder="Username" name="username"/>
            </div>
            <div class="form-group">
                <label for="password-register">Password</label>
                <input id="password-register" type="password" class="form-control" placeholder="Password" name="password">
            </div>
            <div class="form-group">
            <label for="passwordConfirm">Password Confirm</label>
            <input id="passwordConfirm" type="password" class="form-control" placeholder="Password Confirm" name="passwordConfirm"/>
            </div>
            <button id="registerBtn" type="submit" class="btn btn-primary">Register</button>
        </form>
`;