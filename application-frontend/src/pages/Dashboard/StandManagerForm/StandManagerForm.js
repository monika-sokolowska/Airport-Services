import "../Dashboard.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState } from "react";
import { clearStore } from "../../../reducers/userSlice";
import "./StandManagerForm.css";
import LuggageArrivalForm from "./LuggageArrivalForm/LuggageArrivalForm";
import BoardingArrivalForm from "./BoardingArrivalForm/BoardingArrivalForm";
import CleaningForm from "./CleaningForm/CleaningForm";
import TankingForm from "./TankingForm/TankingForm";
import CateringForm from "./CateringForm/CateringForm";

const StandManagerForm = () => {
  const { user } = useSelector((store) => store.user);
  const { service } = useSelector((store) => store.service);
  const dispatch = useDispatch();
  const [disabledButton, setDisabledButton] = useState(true);

  const displayFormDependingOnService = () => {
    switch (service) {
      case "LUGGAGE_ARRIVAL":
        return <LuggageArrivalForm />;
      case "BOARDING_ARRIVAL":
        return <BoardingArrivalForm />;
      case "CLEANING":
        return <CleaningForm />;
      case "TANKING":
        return <TankingForm />;
      case "CATERING":
        return <CateringForm />;
    }
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
            <h1>Name</h1>
            <h3>{user.name}</h3>
            <h1>Login</h1>
            <h3>{user.email}</h3>
          </div>

          <div className="header">Messages</div>
          <div className="received-info">
            <h1>Message</h1>
            <div className="departed-flights">
              <h1>FLIGHT 9558347</h1>
            </div>
            <h1>Time</h1>
            <div className="departed-flights">
              <h1>01:00:00</h1>
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
              <div className="service-forms-container">
                {displayFormDependingOnService()}
              </div>
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
export default StandManagerForm;
