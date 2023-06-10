import "../../Dashboard.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState } from "react";
import "../StandManagerForm.css";
import { changeServiceAction } from "../../../../reducers/serviceSlice";

const initialState = {
  message: "",
};

const BoardingArrivalForm = () => {
  const { user } = useSelector((store) => store.user);
  const dispatch = useDispatch();
  const [disabledButton, setDisabledButton] = useState(true);
  const [values, setValues] = useState(initialState);

  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  return (
    <form className="service-form">
      <label className="service-title">Tanking service</label>
      <div>
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
            type="submit"
            value="Previous"
            className="prev-btn"
            onClick={() => dispatch(changeServiceAction("CLEANING"))}
          />
          <input
            type="submit"
            value="Next"
            className="next-btn"
            onClick={() => dispatch(changeServiceAction("CATERING"))}
          />
        </div>
      </div>
    </form>
  );
};
export default BoardingArrivalForm;
