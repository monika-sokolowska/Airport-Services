import "./Error.css";
import error from "../../assets/images/error.svg";
import { useDispatch } from "react-redux";
import { clearStore } from "../../reducers/userSlice";

const LayoutError = () => {
  const dispatch = useDispatch();

  return (
    <div className="error-info">
      <img src={error} alt="" className="error-image" />
      <div
        className="log-out"
        onClick={() => dispatch(clearStore("Returning to login page..."))}>
        Return
      </div>
    </div>
  );
};

export default LayoutError;
