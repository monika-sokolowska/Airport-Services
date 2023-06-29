import "../Dashboard.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import { clearStore } from "../../../reducers/userSlice";
import { changeServiceAction } from "../../../reducers/serviceSlice";
import "./StandManagerForm.css";
import LuggageArrivalForm from "./LuggageArrivalForm/LuggageArrivalForm";
import BoardingArrivalForm from "./BoardingArrivalForm/BoardingArrivalForm";
import CleaningForm from "./CleaningForm/CleaningForm";
import TankingForm from "./TankingForm/TankingForm";
import CateringForm from "./CateringForm/CateringForm";
import { getAssignedFlights } from "../../../reducers/standManagerSlice";
import LuggageDepartureForm from "./LuggageDepartureForm/LuggageDepartureForm";
import BoardingDepartureForm from "./BoardingDepartureForm/BoardingDepartureForm";
import NavigatorForm from "./NavigatorForm/NavigatorForm";
import PushbackForm from "./PushbackForm/PushbackForm";
import { postFinished } from "../../../reducers/standManagerSlice";

const initialFlight = {
  flightId: "",
  airplaneNumber: "",
};

const StandManagerForm = () => {
  const { user } = useSelector((store) => store.user);
  const { assignedFlights } = useSelector((store) => store.standManager);
  const { service } = useSelector((store) => store.service);
  const dispatch = useDispatch();
  const [disabledButton, setDisabledButton] = useState(true);
  const [flight, setFlight] = useState(initialFlight);
  const [inputsDisabled, setInputsDisabled] = useState(true);
  const [message, setMessage] = useState("");
  const [time, setTime] = useState("");

  const displayFormDependingOnService = () => {
    switch (service) {
      case "LUGGAGE_ARRIVAL":
        return <LuggageArrivalForm flight={flight} />;
      case "BOARDING_ARRIVAL":
        return <BoardingArrivalForm flight={flight} />;
      case "CLEANING":
        return <CleaningForm flight={flight} />;
      case "TANKING":
        return <TankingForm flight={flight} />;
      case "CATERING":
        return <CateringForm flight={flight} />;
      case "LUGGAGE_DEPARTURE":
        return <LuggageDepartureForm flight={flight} />;
      case "BOARDING_DEPARTURE":
        return <BoardingDepartureForm flight={flight} />;
      case "NAVIGATOR":
        return <NavigatorForm flight={flight} />;
      case "PUSHBACK":
        return <PushbackForm flight={flight} />;
    }
  };

  useEffect(() => {
    dispatch(getAssignedFlights(user.id));

    const interval = setInterval(() => {
      dispatch(getAssignedFlights(user.id));
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, [dispatch]);

  const setFlightDetails = (id) => {
    if (assignedFlights) {
      const selectedFlight = assignedFlights.find(
        (flight) => flight.flightId === id
      );
      setFlight(selectedFlight || initialFlight);

      const newTime = new Date(selectedFlight.time);
      newTime.setMinutes(newTime.getMinutes() + selectedFlight.timeToService);

      setTime(newTime.toLocaleTimeString());
      setMessage(selectedFlight.message);
      setDisabledButton(false);
    }
  };

  const changeFlightNumber = (flightId) => {
    setFlightDetails(flightId);
    dispatch(changeServiceAction("LUGGAGE_ARRIVAL"));
  };

  const finishFlightService = (id) => {
    setDisabledButton(true);
    setFlight(initialFlight);
    const data = {
      userId: user.id,
      flightId: id,
    };
    dispatch(postFinished(data));
  };

  return (
    <div className="form-container">
      <div className="form-label">
        <div className="header">Stand Manager</div>
        <div
          className="log-out"
          onClick={() => dispatch(clearStore("Logging out..."))}>
          Log out
        </div>
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

          <div className="header">Assigned flights</div>
          <div className="received-stand-info-flights">
            {assignedFlights &&
              assignedFlights.map((item) => {
                const { flightId, airplaneNumber } = item;
                return (
                  <div
                    key={flightId}
                    className="departed-flights"
                    onClick={() => changeFlightNumber(flightId)}>
                    <h1>{airplaneNumber}</h1>
                  </div>
                );
              })}
          </div>

          <div className="header">Messages</div>
          <div className="received-stand-info">
            <div className="received">
              <h1>Message</h1>
              <textarea
                className="flight-message"
                style={{ minHeight: "35px", width: "100%", height: "150px" }}
                value={message}
                cols="10"
                readOnly></textarea>
              <h1>Departure Time</h1>
              <div className="departed-flights">
                <h1>{time}</h1>
              </div>
            </div>
          </div>
        </div>
        <div className="right-panel">
          <div className="header">Employee Form</div>
          <div className="employee-panel">
            <div className="general-form">
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
              <div className="service-forms-container">
                {displayFormDependingOnService()}
              </div>
              <div className="button-container">
                <input
                  disabled={disabledButton}
                  value="Start"
                  className={
                    disabledButton
                      ? "assign-btn-disabled"
                      : "assign-btn-enabled"
                  }
                  onClick={() => finishFlightService(flight.flightId)}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default StandManagerForm;
