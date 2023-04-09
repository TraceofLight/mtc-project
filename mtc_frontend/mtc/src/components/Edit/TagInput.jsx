import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import TagList from "./TagList";

const TagInput = ({tagChange}) => {
  const [tagSet, setTag] = React.useState(new Set([]));
  // const tagSet = new Set([]);

  const AddTag = (event) => {
    // event.preventDefault();
    if(event.key === 'Enter' || event.key === ' ') {
      tagSet.add(event.target.value);
      console.log(tagSet);
      // const tagList = [...tagSet];
      setTag('');
      setTag(tagSet);
      tagChange([...tagSet])
      // console.log(tagList);
      event.target.value = '';
    }
  }

  const TagDelete = (tag) => {
    tagSet.delete(tag);
    setTag(tagSet);
    tagChange([...tagSet]);
  }

  return (
    <Box
    // component="form"
      sx={{
        "& > :not(style)": { m: 1, width: "100%" },
      }}
      noValidate
      autoComplete="off">
      <TextField id="standard-basic" label="태그"  InputLabelProps={{
            shrink: true,
          }} variant="standard" onKeyUp={AddTag}/>
      <TagList tagtag={tagSet} tagDelete={TagDelete} />
    </Box>
  );
}

export default TagInput;