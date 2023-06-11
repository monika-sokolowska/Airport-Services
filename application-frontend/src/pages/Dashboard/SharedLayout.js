import "./Dashboard.css";
import Header from "../Header/Header";
import { useSelector } from "react-redux";
import GeneralManagerForm from "./GeneralManagerForm/GeneralManagerForm";
import StandManagerForm from "./StandManagerForm/StandManagerForm";
import LuggageArrival from "./LuggageArrival/LuggageArrival";
import LayoutError from "../Error/LayoutError";
import NavigateForm from "./NavigateForm/NavigateForm";

const SharedLayout = () => {
  const { user } = useSelector((store) => store.user);

  const displayFormDependingOnService = () => {
    switch (user.service) {
      case "General Manager":
        return <GeneralManagerForm />;
      case "Stand Manager":
        return <StandManagerForm />;
      case "Luggage Service 1":
        return <LuggageArrival />;
      case "Navigator 1":
        return <NavigateForm />;
      default:
        return <LayoutError />;
    }
  };

  return (
    <main>
      <Header />
      {displayFormDependingOnService()}
    </main>
  );
};
export default SharedLayout;
