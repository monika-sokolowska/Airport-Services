import "../Dashboard.css";
import "./NavigateForm.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";
import { useServiceForm } from "../hooks/useServiceForm";
import { toast } from "react-toastify";
import { useState, useEffect } from "react";
import { postLanded } from "../../../reducers/employeeServiceSlice";
import {
  getAssignedFlight,
  getStartService,
  postFinished,
} from "../../../reducers/employeeServiceSlice";

const initialState = {
  flightNumber: "",
};

const NavigateForm = () => {
  const { user } = useSelector((store) => store.user);
  const [disabledButtonTakeOff, setDisabledButtonTakeOff] = useState(true);
  const [disabledButtonLanded, setDisabledButtonLanded] = useState(false);
  const dispatch = useDispatch();
  const [values, setValues] = useState(initialState);

  const { assignedFlight, serviceStart } = useSelector(
    (store) => store.employeeService
  );
  const [disabledButton, setDisabledButton] = useState(true);
  const [message, setMessage] = useState("");
  const [time, setTime] = useState("");
  const [flight, setFlight] = useState({});
  const [start, setStart] = useState("WAITING");

  useEffect(() => {
    if (assignedFlight) {
      setFlight(assignedFlight);
      setMessage(assignedFlight.message);

      setTime(assignedFlight.timeToService);
      const id = user.id;
      const flightNum = assignedFlight.flightId;
      if (serviceStart !== "START") {
        dispatch(getStartService({ userId: id, flightId: flightNum }));
      } else {
        setDisabledButton(false);
        setStart(serviceStart);
      }
    }
  }, [assignedFlight, serviceStart]);

  useEffect(() => {
    dispatch(getAssignedFlight(user.id));
    const interval = setInterval(() => {
      dispatch(getAssignedFlight(user.id));
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  const finishService = () => {
    const data = { userId: user.id, flightId: flight.flightId };
    dispatch(postFinished(data));
    setDisabledButton(true);
  };

  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  const handleLandedClick = () => {
    if (values.flightNumber !== "") {
      dispatch(postLanded(values.flightNumber));
      setValues({ ...values, flightNumber: "" });
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
                value={message}
                cols="10"
                readOnly></textarea>
              <h1>Finish service time</h1>
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
                <button
                  disabled={disabledButtonTakeOff}
                  className={
                    disabledButtonTakeOff
                      ? "assign-btn-disabled"
                      : "assign-btn-enabled"
                  }
                  onClick={() => finishService()}>
                  Take off
                </button>
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
