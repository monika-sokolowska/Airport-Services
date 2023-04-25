import "../Dashboard.css";
import "./GeneralManagerForm.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState } from "react";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";

const GeneralManagerForm = () => {
  const { user } = useSelector((store) => store.user);
  const [disabledButton, setDisabledButton] = useState(true);
  const dispatch = useDispatch();

  return (
    <div className="form-container">
      <div className="form-label">
        <div className="header">General Manager</div>
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
            <h1>Name</h1>
            <h3>{user.name}</h3>
            <h1>Login</h1>
            <h3>{user.email}</h3>
          </div>
          <div className="header">Messages</div>
          <div className="received-info">
            <h1>Departed flights</h1>
            <div className="departed-flights">
              <h1>FLIGHT 9558347</h1>
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

              <label>Stand manager</label>
              <select className="input-enabled">
                <option value="Alicja Nowak">Alicja Nowak</option>
                <option value="Andrzej Kowalczyk">Andrzej Kowalczyk</option>
                <option selected value="Barbara Nowak">
                  Barbara Nowak
                </option>
                <option value="Elżbieta Nowak">Elżbieta Nowak</option>
              </select>

              <label>Message</label>
              <input value="" className="input-enabled" />

              <label>Time</label>
              <input value="" className="input-enabled" />
              <div className="button-container">
                <input
                  disabled={disabledButton}
                  type="submit"
                  value="Assign"
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
export default GeneralManagerForm;
