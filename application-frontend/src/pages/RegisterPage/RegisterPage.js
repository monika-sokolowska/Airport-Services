import Header from "../Header/Header";
import "./RegisterPage.css";
import { Link } from "react-router-dom";

const RegisterPage = () => {
  return (
    <>
      <Header />

      <div className="register-page">
        <div className="info">
          <form className="register-form">
            <div className="input">
              <label htmlFor="email">Name</label>
              <input type="text" id="name" />
            </div>
            <div className="input">
              <label htmlFor="email">Last name</label>
              <input type="text" id="last-name" />
            </div>
            <div className="input">
              <label htmlFor="email">Email</label>
              <input type="text" id="email" />
            </div>
            <div className="input">
              <label htmlFor="email">Password</label>
              <input type="text" id="password" />
            </div>
            <button className="sign-in-btn">Sign in</button>
            <Link to="/login" className="login-link">
              Log in
            </Link>
          </form>
        </div>
      </div>
    </>
  );
};

export default RegisterPage;
