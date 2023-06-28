import "../Dashboard.css";
import "./LuggageArrival.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";
import {
  getAssignedFlight,
  getStartService,
  postFinished,
} from "../../../reducers/employeeServiceSlice";
import { useServiceForm } from "../hooks/useServiceForm";

const LuggageArrival = () => {
  const { user } = useSelector((store) => store.user);
  const dispatch = useDispatch();

  const { flight, start, message, time, disabledButton, finishService } =
    useServiceForm();

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
                value={message}
                cols="10"
                readOnly></textarea>
              <h1>Time</h1>
              <div className="departed-flights">
                <h1>{time}</h1>
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
                value={
                  flight.flightId
                    ? `ASSIGNED FLIGHT ${flight.flightId}`
                    : "WAITING"
                }
                className="input-disabled"
              />

              <label>Start service status</label>
              <input disabled={true} value={start} className="input-disabled" />

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
                  onClick={() => finishService()}
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
