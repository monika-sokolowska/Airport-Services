import Header from "../Header/Header";
import "./Error.css";
import error from "../../assets/images/error.svg";

const Error = () => {
  return (
    <div className="login-page-container">
      <Header />
      <div className="info">
        <img src={error} alt="" className="error-image" />
      </div>
    </div>
  );
};

export default Error;
