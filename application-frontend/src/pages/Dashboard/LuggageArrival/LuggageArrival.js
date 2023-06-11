import "../Dashboard.css";
import "./LuggageArrival.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState } from "react";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";

const initialState = {
  message: "",
};

const LuggageArrival = () => {
  const { user } = useSelector((store) => store.user);
  const [disabledButton, setDisabledButton] = useState(true);
  const dispatch = useDispatch();

  const [values, setValues] = useState(initialState);
  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  return (
    <div className="form-container">
      <div className="form-label">
        <div className="header">Luggage Arrival</div>
        <Link
          to="/login"
          className="log-out"
          onClick={() => dispatch(clearStore("Logging out..."))}>
          Log out
        </Link>
      </div>
      <div className="form">
        <div className="left-column">
          <div className="header">Information</div>
          <div className="personal-info">
            <h1>User</h1>
            <h3>{user.name}</h3>
            <h1>Login</h1>
            <h3>{user.email}</h3>
          </div>
          <div className="header">Messages</div>
          <div className="received-info">
            <div className="received">
              <h1>Message</h1>
              <textarea
                className="flight-message"
                style={{ minHeight: "35px", width: "100%", height: "150px" }}
                value="FLIGHT 9558347"
                cols="10"
                readOnly></textarea>
              <h1>Time</h1>
              <div className="departed-flights">
                <h1>00:30:00</h1>
              </div>
            </div>
          </div>
        </div>
        <div className="right-panel">
          <div className="header">Employee Form</div>
          <div className="employee-panel">
            <form className="general-form">
              <label>Flight status</label>
              <input
                disabled={true}
                value="WAITING"
                className="input-disabled"
              />

              <label>Start service status</label>
              <input
                disabled={true}
                value="WAITING"
                className="input-disabled"
              />

              <div className="button-container">
                <input
                  disabled={disabledButton}
                  type="submit"
                  value="Finish"
                  className={
                    disabledButton
                      ? "assign-btn-disabled"
                      : "assign-btn-enabled"
                  }
                />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
export default LuggageArrival;
