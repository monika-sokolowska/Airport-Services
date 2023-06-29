import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import {
  getAssignedFlight,
  getStartService,
  postFinished,
} from "../../../reducers/employeeServiceSlice";

export const useServiceForm = () => {
  const { user } = useSelector((store) => store.user);
  const { assignedFlight, serviceStart } = useSelector(
    (store) => store.employeeService
  );
  const dispatch = useDispatch();
  const [disabledButton, setDisabledButton] = useState(true);
  const [message, setMessage] = useState("");
  const [time, setTime] = useState("");
  const [flight, setFlight] = useState({});
  const [start, setStart] = useState("WAITING");

  useEffect(() => {
    dispatch(getAssignedFlight(user.id));

    const interval = setInterval(() => {
      dispatch(getAssignedFlight(user.id));
      if (assignedFlight) {
        setFlight(assignedFlight);
        setMessage(assignedFlight.message);

        const newTime = new Date();
        newTime.setMinutes(newTime.getMinutes() + assignedFlight.timeToService);

        setTime(newTime.toLocaleTimeString());
        if (serviceStart != "START") dispatch(getStartService(user.id));
        else {
          setDisabledButton(false);
          setStart(serviceStart);
        }
      }
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, [dispatch]);

  const finishService = () => {
    const data = { id: user.id, flightId: flight.flightId };
    dispatch(postFinished(data));
    setDisabledButton(true);
  };

  return {
    flight,
    start,
    message,
    time,
    disabledButton,
    finishService,
  };
};
