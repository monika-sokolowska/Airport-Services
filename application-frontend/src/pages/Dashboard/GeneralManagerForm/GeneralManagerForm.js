import "../Dashboard.css";
import "./GeneralManagerForm.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";
import { getFlights } from "../../../reducers/flightsSlice";

const initialState = {
  message: "",
};

const GeneralManagerForm = () => {
  const { user } = useSelector((store) => store.user);
  const flights = useSelector((store) => store.fligths);
  const [disabledButton, setDisabledButton] = useState(true);
  const dispatch = useDispatch();

  const [values, setValues] = useState(initialState);
  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  useEffect(() => {
    dispatch(getFlights());
  }, []);

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
            <h1>User</h1>
            <h3>{user.name}</h3>
            <h1>Login</h1>
            <h3>{user.email}</h3>
          </div>
          <div className="header">Messages</div>
          <div className="received-info-general">
            <h1>Departed flights</h1>
            <div className="received-general"></div>

            <h1>Landed flights</h1>
            <div className="received-general">
              {flights &&
                flights.map((item) => {
                  const { flightId, airplaneNumber } = item;
                  return (
                    <div key={flightId} className="departed-flights">
                      <h1>{airplaneNumber}</h1>
                    </div>
                  );
                })}
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
              <textarea
                value={values.message}
                className="input-message"
                onChange={handleChange}
                name="message"
                cols="10"
              />

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
