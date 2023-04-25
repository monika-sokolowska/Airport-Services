import Header from "../Header/Header";
import "./LoginPage.css";
import { toast } from "react-toastify";
import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { loginUser } from "../../reducers/userSlice";
import { useNavigate } from "react-router-dom";

const initialState = {
  name: "",
  email: "",
  password: "",
  isMember: true,
};

const LoginPage = () => {
  const [values, setValues] = useState(initialState);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector((store) => store.user);

  useEffect(() => {
    if (user) {
      setTimeout(() => {
        navigate("/home");
      }, 2000);
    }
  }, [user]);

  const onSubmit = (e) => {
    e.preventDefault();
    console.log("submit");
    const { email, password, isMember } = values;
    console.log(email, password);
    if (!email || !password) {
      toast.error("Please fill out all fields");
      return;
    }
    if (isMember) {
      dispatch(loginUser({ email: email, password: password }));
      return;
    }
  };

  const handleChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setValues({ ...values, [name]: value });
  };

  return (
    <div className="login-page-container">
      <Header />
      <div className="info">
        <div className="login-container">
          <form className="login-form" onSubmit={onSubmit}>
            <div className="input">
              <label htmlFor="email">Login</label>
              <input
                type="text"
                id="email"
                onChange={handleChange}
                name="email"
                value={values.email}
              />
            </div>
            <div className="input">
              <label htmlFor="email">Password</label>
              <input
                type="password"
                id="password"
                onChange={handleChange}
                name="password"
                value={values.password}
              />
            </div>
            <input type="submit" value="Log in" className="log-in-btn" />
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
