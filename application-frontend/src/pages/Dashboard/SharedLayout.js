import "./Dashboard.css";
import Header from "../Header/Header";
import { useSelector } from "react-redux";
import GeneralManagerForm from "./GeneralManagerForm/GeneralManagerForm";
import StandManagerForm from "./StandManagerForm/StandManagerForm";
import LayoutError from "../Error/LayoutError";

const SharedLayout = () => {
  const { user } = useSelector((store) => store.user);

  const displayFormDependingOnService = () => {
    switch (user.service) {
      case "GENERAL":
        return <GeneralManagerForm />;
      case "STAND":
        return <StandManagerForm />;
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
