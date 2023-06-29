import "../Dashboard.css";
import "./GeneralManagerForm.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { clearStore } from "../../../reducers/userSlice";
import { getFlights, getDepartedFlights } from "../../../reducers/flightsSlice";
import { toast } from "react-toastify";
import {
  getAvailableStandManagers,
  assignStandManager,
} from "../../../reducers/generalManagerSlice";

const initialState = {
  standManager: "",
  message: "Assign all service employees and time for flight",
  time: "240",
};

const initialFlight = {
  flightId: "",
  airplaneNumber: "",
};

const GeneralManagerForm = () => {
  const { user } = useSelector((store) => store.user);
  const { flights } = useSelector((store) => store.flights);
  const { departedFlights } = useSelector((store) => store.flights);
  const { standManagers } = useSelector((store) => store.generalManager);
  const [disabledButton, setDisabledButton] = useState(true);
  const dispatch = useDispatch();
  const [flight, setFlight] = useState(initialFlight);
  const [inputsDisabled, setInputsDisabled] = useState(true);

  const [values, setValues] = useState(initialState);
  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  useEffect(() => {
    dispatch(getFlights());
    dispatch(getDepartedFlights());

    const interval = setInterval(() => {
      dispatch(getFlights());
      dispatch(getDepartedFlights());
    }, 1000);

    if (standManagers[0] !== undefined) {
      setValues({ ...values, standManager: standManagers[0].id });
    }

    return () => {
      clearInterval(interval);
    };
  }, [dispatch, standManagers]);

  const setFlightDetails = (id) => {
    dispatch(getAvailableStandManagers());

    if (flights) {
      const selectedFlight = flights.find((flight) => flight.flightId === id);
      setFlight(selectedFlight || initialFlight);

      setDisabledButton(false);
      setInputsDisabled(false);
    }
  };

  const onSubmit = (e) => {
    e.preventDefault();
    const { standManager, message, time } = values;
    const { flightId } = flight;
    if (!standManager || !message || !time) {
      toast.error("Please fill out all fields");
      return;
    } else {
      dispatch(
        assignStandManager(
          `/generalManager/${standManager}/assigStandManager?flightId=${flightId}&message=${message}&timeToService=${time}`
        )
      );
      setFlight(initialFlight);
      setDisabledButton(true);
      setInputsDisabled(true);
      setValues(initialState);
      return;
    }
  };

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
            <div className="received-general">
              {departedFlights &&
                departedFlights.map((item) => {
                  const { flightId, airplaneNumber } = item;
                  return (
                    <div key={flightId} className="departed-flights">
                      <h1>{airplaneNumber}</h1>
                    </div>
                  );
                })}
            </div>

            <h1>Landed flights</h1>
            <div className="received-general">
              {flights &&
                flights.map((item) => {
                  const { flightId, airplaneNumber } = item;
                  return (
                    <div
                      key={flightId}
                      className="departed-flights"
                      onClick={() => setFlightDetails(flightId)}>
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
            <form className="general-form" onSubmit={onSubmit}>
              <label>Flight status</label>
              <input
                disabled={true}
                value={
                  flight.airplaneNumber
                    ? `LANDED ${flight.airplaneNumber}`
                    : "WAITING"
                }
                className="input-disabled"
              />

              <label>Stand manager</label>
              <select
                className="input-enabled"
                name="standManager"
                onChange={handleChange}
                disabled={inputsDisabled}>
                {standManagers &&
                  standManagers.map((item) => {
                    const { id, name } = item;
                    return (
                      <option key={id} value={id}>
                        {name}
                      </option>
                    );
                  })}
              </select>

              <label>Message</label>
              <textarea
                value={values.message}
                className="input-message"
                onChange={handleChange}
                name="message"
                cols="10"
                disabled={inputsDisabled}
              />

              <label>Time</label>
              <input
                type="number"
                name="time"
                value={values.time}
                className="input-enabled"
                onChange={handleChange}
                disabled={inputsDisabled}
              />
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
