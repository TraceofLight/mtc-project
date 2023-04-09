import HamburgerButton from "./HamburgerButton_tmp";
import EvaluateButton from "./EvaluateButton";
import Stack from '@mui/material/Stack';
import { useSelector } from "react-redux";
const UnderNavigationBar = (props) => {
  const userStatus = useSelector(state => state.userStatus);

  return (
    <>
      <Stack spacing={2}>
        <EvaluateButton post={props.post}/>
        <HamburgerButton post={props.post} />
      </Stack>
    </>
  );
};

export default UnderNavigationBar;
