import "../Dashboard.css";
import "./NavigateForm.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState } from "react";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";
import customFetch from "../../../utils/axios";
import { toast } from "react-toastify";

const initialState = {
  flightNumber: "",
};

const NavigateForm = () => {
  const { user } = useSelector((store) => store.user);
  const [disabledButton, setDisabledButton] = useState(true);
  const [disabledButtonTakeOff, setDisabledButtonTakeOff] = useState(true);
  const [disabledButtonLanded, setDisabledButtonLanded] = useState(false);
  const dispatch = useDispatch();

  const [values, setValues] = useState(initialState);
  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  const landed = async (url) => {
    try {
      const resp = await customFetch.post(url);
      toast.success(`Plane ${values.flightNumber} landed - information sent`);
      return resp.data;
    } catch (error) {
      toast.error("Request was not sent due to error");
    }
  };

  const postLanded = () => {
    landed(`/flight/navigator/landed?number=${values.flightNumber}`);
  };

  const handleLandedClick = () => {
    console.log(values.flightNumber);
    if (values.flightNumber != "") {
      postLanded();
    } else {
      toast.error("Flight number expected");
    }
  };

  return (
    <div className="form-container">
      <div className="form-label">
        <div className="header">Navigator</div>
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
                name="flight-status"
                disabled={true}
                value="WAITING"
                className="input-disabled"
              />

              <label>Start service status</label>
              <input
                name="start-service"
                disabled={true}
                value="WAITING"
                className="input-disabled"
              />

              <div className="button-container">
                <button
                  disabled={disabledButtonTakeOff}
                  className={
                    disabledButtonTakeOff
                      ? "assign-btn-disabled"
                      : "assign-btn-enabled"
                  }
                  onClick={handleLandedClick}>
                  Take off
                </button>
                <button
                  disabled={disabledButton}
                  type="button"
                  className={
                    disabledButton
                      ? "assign-btn-disabled"
                      : "assign-btn-enabled"
                  }
                  onClick={handleLandedClick}>
                  Finish
                </button>
              </div>

              <label>Flight number</label>
              <input
                name="flightNumber"
                value={values.flightNumber}
                className="input-enabled"
                onChange={handleChange}
              />

              <div className="button-container">
                <button
                  name="flight-button"
                  type="button"
                  disabled={disabledButtonLanded}
                  className={
                    disabledButtonLanded
                      ? "assign-btn-disabled"
                      : "assign-btn-enabled"
                  }
                  onClick={handleLandedClick}>
                  Landed
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
export default NavigateForm;
