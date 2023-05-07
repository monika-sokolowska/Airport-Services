import "../../Dashboard.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState } from "react";
import "../StandManagerForm.css";
import { changeServiceAction } from "../../../../reducers/serviceSlice";

const BoardingArrivalForm = () => {
  const { user } = useSelector((store) => store.user);
  const dispatch = useDispatch();
  const [disabledButton, setDisabledButton] = useState(true);

  return (
    <form className="service-form">
      <label className="service-title">Catering service</label>
      <label>Employee</label>
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
          type="submit"
          value="Previous"
          className="prev-btn"
          onClick={() => dispatch(changeServiceAction("TANKING"))}
        />
        <input
          type="submit"
          value="Next"
          className="next-btn"
          onClick={() => dispatch(changeServiceAction("LUGGAGE_DEPARTURE"))}
        />
      </div>
    </form>
  );
};
export default BoardingArrivalForm;
