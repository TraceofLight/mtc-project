import NavigationBar from '../components/NavigationBar/NavigationBar'
import CreatePost from '../components/CreatePost/CreatePost';
import { useState } from 'react';
// import {useCallbackPrompt} from '../components/CreatePost/useCallbackPrompt';
// import Box from '@mui/material/Box';
// import Modal from '@mui/material/Modal';
// import Button from '@mui/material/Button';
// import Typography from '@mui/material/Typography';
// import { usePrompt } from '../components/CreatePost/Blocker';

const CreatePage = () => {
  // usePrompt('내용이 사라져요', true);

  const [change, setChange] = useState(false);
  // const [showPrompt, confirmNavigation, cancelNavigation] = useCallbackPrompt(change);

  const parentsFunction = () => {
    setChange(true)
    console.log(change)
  };


  // const style = {
  //   position: 'absolute',
  //   top: '50%',
  //   left: '50%',
  //   transform: 'translate(-50%, -50%)',
  //   width: 400,
  //   bgcolor: 'background.paper',
  //   border: '2px solid #000',
  //   boxShadow: 24,
  //   p: 4,
  // };
    return (
      <div>
        
        <div>
          <CreatePost parentsFunction={ parentsFunction }/>
        </div>
        {/* <Modal
        keepMounted
        open={showPrompt}
        onClose={cancelNavigation}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <Box sx={style}>
          <Typography id="keep-mounted-modal-title" variant="h6" component="h2">
            Text in a modal
          </Typography>
          <Typography id="keep-mounted-modal-description" sx={{ mt: 2 }}>
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
          </Typography>
          <Button onClick={confirmNavigation}> 확인 </Button>
        </Box>
      </Modal> */}
      </div>
    );
  };
  
export default CreatePage;
