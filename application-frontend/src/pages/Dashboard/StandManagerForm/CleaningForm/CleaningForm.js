import "../../Dashboard.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import "../StandManagerForm.css";
import { changeServiceAction } from "../../../../reducers/serviceSlice";
import {
  getAvailable,
  assignEmployee,
} from "../../../../reducers/standManagerSlice";
import { toast } from "react-toastify";

const initialState = {
  employee: "",
  message:
    "Please finish service work in time. In case of delay contact flight stand manager.",
  time: "20",
};

const CleaningForm = ({ flight }) => {
  const { user } = useSelector((store) => store.user);
  const { availableEmployees } = useSelector((store) => store.standManager);
  const dispatch = useDispatch();
  const [disabledButton, setDisabledButton] = useState(true);
  const [values, setValues] = useState(initialState);
  const [inputsDisabled, setInputsDisabled] = useState(true);
  const [employees, setEmployees] = useState([]);

  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    console.log("name value", name, value);

    setValues({ ...values, [name]: value });
  };

  useEffect(() => {
    dispatch(getAvailable(user.id));
    if (availableEmployees) {
      const serviceEmployees = availableEmployees.filter(
        (availableEmployee) => availableEmployee.service === "Cleaning Service"
      );

      if (serviceEmployees) {
        setEmployees(serviceEmployees);
        if (serviceEmployees[0]) {
          console.log("serviceEmployees[0]", serviceEmployees[0]);
          setValues({ ...values, employee: serviceEmployees[0].id });
        }
      }
    }
    console.log("flight.flightId", flight.flightId);
    if (flight && flight.flightId) {
      setInputsDisabled(false);
      setDisabledButton(false);
    }
  }, [flight]);

  const onSubmit = (e) => {
    e.preventDefault();
    const { employee, message, time } = values;
    console.log("submit", employee, message, time);
    const { flightId } = flight;

    const data = {
      id: user.id,
      url: `/standManager/${employee}/assignEmployee?flightId=${flightId}&message=${message}&timeToService=${time}`,
    };
    if (!employee || !message || !time) {
      toast.error("Please fill out all fields");
      return;
    } else {
      dispatch(assignEmployee(data));
      setDisabledButton(true);
      setInputsDisabled(true);
      setValues(initialState);
      return;
    }
  };

  return (
    <form className="service-form" onSubmit={onSubmit}>
      <label className="service-title">Cleaning service</label>
      <div>
        <label>Employee</label>
        <select
          className="input-enabled"
          name="employee"
          disabled={inputsDisabled}
          onChange={handleChange}>
          {!employees[0] && (
            <option style={{ color: "red" }}>All employees busy</option>
          )}
          {employees[0] &&
            employees.map((item) => {
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
          value={values.time}
          className="input-enabled"
          disabled={inputsDisabled}
          onChange={handleChange}
          name="time"
        />

        <div className="button-container">
          <input
            value="Previous"
            className="prev-btn"
            onClick={() => dispatch(changeServiceAction("BOARDING_ARRIVAL"))}
          />
          <input
            value="Next"
            className="next-btn"
            onClick={() => dispatch(changeServiceAction("TANKING"))}
          />
        </div>
        <div className="button-container">
          <input
            disabled={disabledButton}
            type="submit"
            value="Assign"
            className={
              disabledButton ? "assign-btn-disabled" : "assign-btn-enabled"
            }
          />
        </div>
      </div>
    </form>
  );
};
export default CleaningForm;
