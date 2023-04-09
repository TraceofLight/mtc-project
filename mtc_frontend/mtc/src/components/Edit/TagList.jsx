import * as React from 'react';
import Fab from '@mui/material/Fab';
import ClearIcon from '@mui/icons-material/Clear';
import Box from '@mui/material/Box';

const TagList = ( props ) => {
  // return(
  //     props.TagList.forEach((element) => {
  //         <div>
  //             element
  //         </div>
  //     })
  // 용현이 함

  // )
  
  // const [tags, SetTagList] = React.useState('');
  

  const ButtonClick = (event) => {
    event.preventDefault();
  }

  const DeleteClick = (tag) => {
    props.tagDelete(tag)
  }


  const tagList = [...props.tagtag].map((tag) => (
    <Fab variant="extended" size="small" style={{ backgroundColor:"#6DCEF5" , color:"#FFFFFF"}} onClick={ButtonClick} disableRipple='false'>
      #
      {tag}
      <Box onClick={() => DeleteClick(tag)}>
        <ClearIcon color='warning' fontSize='small' style={{ verticalAlign:"middle" }}/>
      </Box>
    </Fab>
  ));

  return (
    
    <Box sx={{ '& > :not(style)': { m: 0.5 } }}>
        {tagList}
        {/* {tags} */}
       
    </Box>
    );
  // return (
  //     <div>
  //         {props.tagSet}
  //     </div>

  // )
};

export default TagList;
