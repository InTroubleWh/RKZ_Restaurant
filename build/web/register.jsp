<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RZK Restaurant - Register</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
            }
            body {
                display: flex;
                min-height: 100vh;
                background: lightgray;
            }
            .container {
                width: 100%;
                display: flex;
                background: white;
                box-shadow: 0 10px 15px black;
            }
            .left {
                width: 60%;
                background: url('static/img/rkz-chicken-plate.jpg') no-repeat center center/cover;
            }
            .registration {
                width: 40%;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                position: relative;
            }
            .close-btn {
                position: absolute;
                top: 10px;
                right: 20px;
                font-size: 24px;
                font-weight: bold;
                cursor: pointer;
            }
            form {
                width: 300px;
                margin: 60px auto;
            }
            h1 {
                margin: 20px;
                text-align: center;
                font-weight: bolder;
                text-transform: uppercase;
            }
            hr {
                border-top: 2px solid black;
            }
            p {
                text-align: center;
                margin: 10px;
            }
            form label {
                display: block;
                font-size: 16px;
                font-weight: 600;
                padding: 5px;
            }
            input {
                width: 100%;
                margin: 2px;
                border: none;
                outline: none;
                padding: 8px;
                border-radius: 5px;
                border: 1px solid black;
            }
            button {
                border: none;
                outline: none;
                padding: 8px;
                width: 100%;
                color: white;
                font-size: 16px;
                cursor: pointer;
                margin-top: 20px;
                border-radius: 5px;
                background: grey;
            }
            button:hover {
                background: darkgray;
            }
            @media (max-width: 880px) {
                .container {
                    flex-direction: column;
                }
                .left {
                    width: 100%;
                    height: 50vh;
                }
                .registration {
                    width: 100%;
                }
                form {
                    width: 90%;
                    margin: 20px auto;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="left"></div>
            <div class="registration">
                <span class="close-btn" onclick="window.location.href = 'home.jsp'">×</span>
                <form action="<%=request.getContextPath()%>/Register" method="post">
                    <h1>Register</h1>
                    <hr>
                    <p>RKZ Restaurant</p>
                    <label for="username">Username: </label>
                    <input type="text" id="username" name="username" placeholder="Insert Your Username" required>
                    <label for="email">E-Mail </label>
                    <input type="text" id="email" name="email" placeholder="Insert Your Username" required>
                    <label for="hp">Phone Number: </label>
                    <input type="tel" id="hp" name="phone_number" placeholder="Insert Your Phone Number" required>
                    <label for="password">Password: </label>
                    <input type="password" id="password" name="password" placeholder="Insert Your Password" required>
                    <label for="confirm_password">Confirm Password: </label>
                    <input type="password" id="confirm_password" name="confirm_password" placeholder="Confirm Password" required>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    </body>
</html>
