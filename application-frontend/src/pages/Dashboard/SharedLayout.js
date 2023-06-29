import "./Dashboard.css";
import Header from "../Header/Header";
import { useSelector } from "react-redux";
import GeneralManagerForm from "./GeneralManagerForm/GeneralManagerForm";
import StandManagerForm from "./StandManagerForm/StandManagerForm";
import LuggageArrival from "./LuggageArrival/LuggageArrival";
import LayoutError from "../Error/LayoutError";
import NavigateForm from "./NavigateForm/NavigateForm";
import BoardingArrival from "./BoardingArrival/BoardingArrival";
import Cleaning from "./Cleaning/Cleaning";
import Tanking from "./Tanking/Tanking";
import Catering from "./Catering/Catering";
import LuggageDeparture from "./LuggageDeparture/LuggageDeparture";
import BoardingDeparture from "./BoardingDeparture/BoardingDeparture";
import Pushback from "./Pushback/Pushback";

const SharedLayout = () => {
  const { user } = useSelector((store) => store.user);

  const displayFormDependingOnService = () => {
    switch (user.service) {
      case "General Manager":
        return <GeneralManagerForm />;
      case "Stand Manager":
        return <StandManagerForm />;
      case "Luggage Service":
        return <LuggageArrival />;
      case "Boarding Service":
        return <BoardingArrival />;
      case "Cleaning Service":
        return <Cleaning />;
      case "Tanking Service":
        return <Tanking />;
      case "Catering Service":
        return <Catering />;
      case "Luggage Service (departure)":
        return <LuggageDeparture />;
      case "Boarding Service (departure)":
        return <BoardingDeparture />;
      case "Pushback Service":
        return <Pushback />;
      case "Navigator":
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
